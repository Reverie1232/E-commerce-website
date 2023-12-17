package com.example.service;

import com.example.mapper.UserMapper;
import com.example.pojo.*;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private OrderService orderService;
    @Resource
    private AddressService addressService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private UserMapper userMapper;

    /**
     * 激活账号邮件发送
     *
     * @param activationUrl 激活 url 链接
     * @param email 收件人邮箱
     */
    public void sendMailForActivateAccount(String activationUrl, String email){
        // 创建邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            message.setSubject("欢迎来到E-commerce - 个人账号激活");
            // 设置邮件发送者，可以多个
            message.setFrom(mailUsername);
            // 设置邮件接受者，可以多个
            message.setTo(email);
            // 设置邮件抄送人，可以多个
            //message.setCc();
            // 设置隐秘抄送人，可以多个
            // 设置邮件发送日期
            message.setSentDate(new Date());
            // 创建上下文环境
            Context context = new Context();
            context.setVariable("activationUrl", activationUrl);
            String text = templateEngine.process("activation-account.html", context);
            // 设置邮件正文
            message.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 邮件发送
        javaMailSender.send(mimeMessage);
    }

    public void sendMailForCheckout(String email) {
        // 创建邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 根据订单 ID 获取订单详情
        int order_id = orderService.getCurrentOrderId();
        Order order = orderService.getOrdersByOrderId(order_id);
        User user = userMapper.selectUserByUserId(order.getUser_id());
        int address_id = order.getShipping_address_id();
        Address address = addressService.getAddressById(address_id);
        List<OrderItem> orderItems = orderService.getItemsByOrderId(order_id);
        List<Goods> Items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            Goods goods = goodsService.getGoodsById(orderItem.getGoods_id());
            Items.add(goods);
            quantities.add(orderItem.getQuantity());
        }
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            message.setSubject("欢迎来到E-commerce - 结算成功");
            // 设置邮件发送者，可以多个
            message.setFrom(mailUsername);
            // 设置邮件接受者，可以多个
            message.setTo(email);
            // 设置邮件抄送人，可以多个
            //message.setCc();
            // 设置隐秘抄送人，可以多个
            // 设置邮件发送日期
            message.setSentDate(new Date());
            // 创建上下文环境
            Context context = new Context();
            context.setVariable("user", user);
            context.setVariable("order", order);
            context.setVariable("address", address);
            context.setVariable("Items", Items);
            context.setVariable("quantities", quantities);
            String text = templateEngine.process("check-out-email.html", context);
            // 设置邮件正文
            message.setText(text, true);
        } catch (MessagingException ignored) {

        }
        // 邮件发送
        javaMailSender.send(mimeMessage);
    }
}

