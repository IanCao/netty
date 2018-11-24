package com.ke.netty;

import com.ke.netty.data.SendToGroupRequestPacket;
import com.ke.netty.data.SendToGroupResponsePacket;
import com.ke.netty.session.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@ChannelHandler.Sharable
public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    public static final SendToGroupRequestHandler INSTANCE = new SendToGroupRequestHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();

        SendToGroupResponsePacket responsePacket = new SendToGroupResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setMessage(msg.getMessage());
        responsePacket.setFromUserName(SessionUtils.getSession(ctx.channel()).getUserName());

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        channelGroup.writeAndFlush(responsePacket);
    }
}
