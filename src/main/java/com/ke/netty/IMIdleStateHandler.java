package com.ke.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        /**
         *  IdleStateHandler 类的构造函数有四个参数，其功能分别为：
         *  readerIdleTime ：读空闲时间，指的是在这段时间内如果没有数据读到，就表示连接假死
         *  writerIdleTime ：写空闲时间，表示是在这段时间内如果没有写数据，就表示连接假死
         *  allIdleTime    ：读写空闲时间，表示在这段时间内如果没有产生数据读或者写，就表示连接假死
         *
         *  连接假死之后会回调 channelIdle() 方法，我们这个方法里面打印消息，并手动关闭连接。
         */

        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
