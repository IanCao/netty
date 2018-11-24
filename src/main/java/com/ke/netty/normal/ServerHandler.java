package com.ke.netty.normal;

import com.ke.netty.data.LoginRequestPacket;
import com.ke.netty.data.LoginResponsePacket;
import com.ke.netty.data.MessageRequestPacket;
import com.ke.netty.data.MessageResponsePacket;
import com.ke.netty.data.packet.Packet;
import com.ke.netty.data.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author caoyixiong
 * @Date: 2018/11/22
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //这个方法在接收到客户端发来的数据之后被回调。
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Server 读取到的数据是   " + byteBuf.toString(Charset.forName("utf-8")));

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        //判断是否是登录请求包
        if (packet instanceof LoginRequestPacket) {

            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(responsePacket.getVersion());

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if (isValid(loginRequestPacket)) { //校验通过
                responsePacket.setSuccess(true);
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("账号密码校验失败");
            }
            //编码
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            System.out.println("服务端接收到的 客户端的数据为： " + requestPacket.getMessage());

            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("服务端回复【" + requestPacket.getMessage() + "】");
            ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), responsePacket);
            ctx.channel().writeAndFlush(buf);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "服务端返回给客户端的数据".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }

    private boolean isValid(LoginRequestPacket packet) {
        if ("testUserName".equalsIgnoreCase(packet.getUserName()) && "testPassword".equalsIgnoreCase(packet.getPassword())) {
            System.out.println("testUserName 此用户登录校验成功");
            return true;
        }
        return false;
    }
}
