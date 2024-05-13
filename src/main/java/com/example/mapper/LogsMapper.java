package com.example.mapper;

import com.example.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface LogsMapper {

    @Insert("INSERT INTO login_logs (user_id, ip, login_time) " +
            "VALUES (#{user_id}, #{ip}, #{login_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void login_log(Login_logs login_logs);

    @Insert("INSERT INTO logout_logs (user_id, ip, logout_time) " +
            "VALUES (#{user_id}, #{ip}, #{logout_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void logout_log(Logout_logs logout_logs);

    @Insert("INSERT INTO admin_login (name, ip, login_time)" +
            "VALUES (#{name}, #{ip}, #{login_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void admin_login(Admin_login adminLogin);

    @Insert("INSERT INTO admin_logout (name, ip, logout_time)" +
            "VALUES (#{name}, #{ip}, #{logout_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void admin_logout(Admin_logout adminLogout);

    @Insert("INSERT INTO seller_login (seller, ip, login_time)" +
            "VALUES (#{seller}, #{ip}, #{login_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void seller_login(Seller_login sellerLogin);

    @Insert("INSERT INTO seller_logout (seller, ip, logout_time)" +
            "VALUES (#{seller}, #{ip}, #{logout_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void seller_logout(Seller_logout sellerLogout);
}
