package com.ke.netty.console;

import com.ke.netty.data.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入你要发送人的id");
        String toUserId = scanner.next();
        System.out.println("请输入你要发送的消息");
        String message = scanner.next();

        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
