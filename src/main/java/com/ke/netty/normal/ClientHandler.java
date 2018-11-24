package com.ke.netty.normal;

import com.ke.netty.data.LoginRequestPacket;
import com.ke.netty.data.LoginResponsePacket;
import com.ke.netty.data.MessageResponsePacket;
import com.ke.netty.data.packet.Packet;
import com.ke.netty.data.packet.PacketCodeC;
import com.ke.netty.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * @author caoyixiong
 * @Date: 2018/11/22
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    //这个逻辑处理器继承自 ChannelInboundHandlerAdapter，然后覆盖了 channelActive()方法，这个方法会在客户端连接建立成功之后被调用

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端连接成功之后，会调用到channelActive 方法，在这个方法里面可以编写向服务端写数据的逻辑
        System.out.println(new Date() + " : Client 开始进行登录操作");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("testUserName");
        packet.setPassword("testPassword");

        //编码
        // ctx.alloc() 获取的就是与当前连接相关的 ByteBuf 分配器，
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), packet);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("Client 读取到的数据是   " + byteBuf.toString(Charset.forName("utf-8")));

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;

            if (responsePacket.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
                return;
            }
            System.out.println(new Date() + ": 客户端登录失败，原因：" + responsePacket.getReason());
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }

    // 使用ChannelHandlerContext 获取对应的 当前连接的 ByteBuf 分配器，从而进行数据的传输
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        // 获取二进制抽象 ByteBuf
        // ctx.alloc() 是获取到一个ByteBuf的内存管理器，这个内存管理器的作用就是分配一个ByteBuf，
        // 然后我们把字符串的二进制数据填充到 ByteBuf，这样我们就获取到了 Netty 需要的一个数据格式，
        // 最后我们调用 ctx.channel().writeAndFlush() 把数据写到服务端...

        /**
         * Netty 里面数据是以 ByteBuf 为单位的
         */

        ByteBuf byteBuf = ctx.alloc().buffer();

        //准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "Hello, Netty, 我来啦".getBytes(Charset.forName("utf-8"));

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
