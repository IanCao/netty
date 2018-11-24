package com.ke.netty.test;

import com.ke.netty.data.DataPacket;
import com.ke.netty.data.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 1000; i++) {
            ByteBuf buffer = getByteBuf(ctx, i);
            ctx.channel().writeAndFlush(buffer);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, int i) {
        DataPacket dataPacket = new DataPacket();
        dataPacket.setMessage("你好，欢迎关注我的微信公众号，《闪电侠的博客》!  " + i);
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), dataPacket);
        return buffer;
    }
}
