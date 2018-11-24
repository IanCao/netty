package com.ke.netty;

import com.ke.netty.data.LoginRequestPacket;
import com.ke.netty.data.LoginResponsePacket;
import com.ke.netty.session.Session;
import com.ke.netty.session.SessionUtils;
import com.ke.netty.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        //客户端连接成功之后，会调用到channelActive 方法，在这个方法里面可以编写向服务端写数据的逻辑
//        System.out.println(new Date() + " : Client 开始进行登录操作");
//
//        LoginRequestPacket packet = new LoginRequestPacket();
//        packet.setUserId(UUID.randomUUID().toString());
//        packet.setUserName("testUserName");
//        packet.setPassword("testPassword");
//
//        ctx.channel().writeAndFlush(packet);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
//            LoginUtil.markAsLogin(channelHandlerContext.channel());
            Session session = new Session();
            session.setUserName(loginResponsePacket.getUserName());
            session.setUserId(loginResponsePacket.getUserId());
            SessionUtils.bindSession(session, channelHandlerContext.channel());
            System.out.println(new Date() + ": 客户端登录成功");
            return;
        }
        System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
