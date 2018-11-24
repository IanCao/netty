package com.ke.netty;

import com.ke.netty.data.MessageRequestPacket;
import com.ke.netty.data.MessageResponsePacket;
import com.ke.netty.session.Session;
import com.ke.netty.session.SessionUtils;
import com.ke.netty.utils.LoginUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 获取消息发送方的会话信息
        Session session = SessionUtils.getSession(ctx.channel());

        // 通过消息发送来构造要发送的消息
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setVersion(messageRequestPacket.getVersion());
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUserName());
        responsePacket.setMessage(messageRequestPacket.getMessage());

        // 获取到接收方的Channel
        Channel channel = SessionUtils.getChannel(messageRequestPacket.getToUserId());

        if (channel != null && LoginUtil.hasLogin(channel)) {
            channel.writeAndFlush(responsePacket);
            return;
        }
        System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
    }

}
