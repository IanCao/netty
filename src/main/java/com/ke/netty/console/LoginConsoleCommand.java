package com.ke.netty.console;

import com.ke.netty.data.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名登录");
        String userName = scanner.nextLine();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassword("testpwd");

        channel.writeAndFlush(loginRequestPacket);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
