package com.ke.netty.test;

import com.ke.netty.data.DataPacket;
import com.ke.netty.data.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        DataPacket dataPacket = (DataPacket) PacketCodeC.INSTANCE.decode(byteBuf);
        System.out.println(new Date() + ": 服务端读到数据 -> " + dataPacket.getMessage());
    }
}
