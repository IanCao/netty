package com.ke.netty.console;

import com.ke.netty.data.SendToGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入你要发送的群组ID ");
        String groupId = scanner.nextLine();
        System.out.println("请输入你要发送的消息 ");
        String message = scanner.nextLine();

        SendToGroupRequestPacket requestPacket = new SendToGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        requestPacket.setMessage(message);
        channel.writeAndFlush(requestPacket);
    }
}
