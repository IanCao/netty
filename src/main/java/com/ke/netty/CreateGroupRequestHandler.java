package com.ke.netty;

import com.alibaba.fastjson.JSON;
import com.ke.netty.data.CreateGroupRequestPacket;
import com.ke.netty.data.CreateGroupResponsePacket;
import com.ke.netty.session.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();

        // 创建一个channel分组
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());

        List<String> userNameList = new ArrayList<String>();
        // 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtils.getChannel(userId);
            if (channel != null) {
                channels.add(channel);
                userNameList.add(SessionUtils.getSession(channel).getUserName());
            }
        }

        // 创建群聊 创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(UUID.randomUUID().toString());
        createGroupResponsePacket.setUserNameList(JSON.toJSONString(userNameList));

        SessionUtils.setChannelGroup(createGroupResponsePacket.getGroupId(), channels);

        channels.writeAndFlush(createGroupResponsePacket);
        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());
    }
}
