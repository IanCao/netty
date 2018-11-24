package com.ke.netty;

import com.ke.netty.console.ConsoleCommandManager;
import com.ke.netty.console.LoginConsoleCommand;
import com.ke.netty.data.LoginRequestPacket;
import com.ke.netty.data.MessageRequestPacket;
import com.ke.netty.data.packet.*;
import com.ke.netty.session.SessionUtils;
import com.ke.netty.utils.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author caoyixiong
 * @Date: 2018/9/26
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class NettyClient {
    private static int port = 12344;

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();

        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group) //指定线程模型
                .channel(NioSocketChannel.class) //指定IO类型为NIO
                .handler(new ChannelInitializer<Channel>() { //IO 处理逻辑
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(PacketCodeHandler.INSTANCE);
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new SendToGroupResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                    }
                });

        //建立连接
        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port);
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {  //可以监听到连接是否成功，进而打印出连接信息
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接成功~");
                    //启动控制台线程
                    startConsoleThread(channelFuture.channel());
                    return;
                }
                System.out.println("连接失败~");
                //进行失败重连操作

                //bootstrap.config().group() 返回的就是我们在一开始的时候配置的线程模型 workerGroup，
                //调 workerGroup 的 schedule 方法即可实现定时任务逻辑。
                bootstrap.config().group().schedule(new Runnable() {
                    public void run() {
                        bootstrap.connect("127.0.0.1", port);
                    }
                }, 3, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(final Channel channel) {
        final Scanner scanner = new Scanner(System.in);
        final ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        final LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (SessionUtils.hasLogin(channel)) { //已经成功登录
                        consoleCommandManager.exec(scanner, channel);
                    } else { //没有成功登录
                        loginConsoleCommand.exec(scanner, channel);
                    }
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}