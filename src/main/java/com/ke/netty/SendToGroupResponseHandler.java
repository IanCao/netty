package com.ke.netty;

import com.ke.netty.data.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket msg) throws Exception {
        String groupId = msg.getFromGroupId();
        String groupName = msg.getFromUserName();
        String message = msg.getMessage();
        System.out.println("群组：[" + groupId + "] 中的[" + groupName + "] 发送的消息时 --> " + message);
    }
}
