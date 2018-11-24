package com.ke.netty;

import com.ke.netty.data.LoginRequestPacket;
import com.ke.netty.data.LoginResponsePacket;
import com.ke.netty.data.packet.PacketDecoder;
import com.ke.netty.session.Session;
import com.ke.netty.session.SessionUtils;
import com.ke.netty.utils.LoginUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {
        //登录逻辑
        LoginResponsePacket responsePacket = login(ctx, packet);
        if (responsePacket.isSuccess()) { //说明登录成功
            Session session = new Session();
            session.setUserId(packet.getUserId());
            session.setUserName(packet.getUserName());
            SessionUtils.bindSession(session, ctx.channel());

            System.out.println("角色人 " + packet.getUserName() + " 登录成功 " + packet.getUserId());
            responsePacket.setUserId(packet.getUserId());
            responsePacket.setUserName(packet.getUserName());
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(responsePacket.getVersion());

        if (isValid(loginRequestPacket)) { //校验通过
            responsePacket.setSuccess(true);
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码校验失败");
        }
        return responsePacket;
    }

    private boolean isValid(LoginRequestPacket packet) {
//        if ("testUserName".equalsIgnoreCase(packet.getUserName()) && "testPassword".equalsIgnoreCase(packet.getPassword())) {
//            System.out.println("testUserName 此用户登录校验成功");
//            return true;
//        }
//        System.out.println(packet.getUserName() + " 此用户登录校验失败");
//        return false;
        return true;
    }
}
