package com.ke.netty;

import com.ke.netty.data.packet.PacketCodeHandler;
import com.ke.netty.data.packet.PacketDecoder;
import com.ke.netty.data.packet.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author caoyixiong
 * @Date: 2018/9/26
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class NettyServer {
    private static int port = 12344;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(); //表示监听端口，接收新连接的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup(); //表示处理每一条连接的数据读写的线程组

        final ServerBootstrap serverBootstrap = new ServerBootstrap(); //引导服务端的启动工作
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class) //指定服务端的IO模型为NIO，如果想指定BIO,可以使用OioServerSocketChannel.class
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //每次有新连接到来的时候，都会调用 ChannelInitializer 的 initChannel() 方法，然后这里 9 个指令相关的 handler 都会被 new 一次。
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception { //处理新连接数据的读写处理逻辑
                        //通过给逻辑处理链 pipeline 添加逻辑处理器，来编写数据的读写逻辑

                        // 添加 基于长度域拆包器 LengthFieldBasedFrameDecoder 来解决拆包和粘包的问题
//                        nioSocketChannel.pipeline().addLast(new LifeCyCleTestHandler());
                        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        nioSocketChannel.pipeline().addLast(PacketCodeHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(new AuthHandler());
                        nioSocketChannel.pipeline().addLast(IMHandler.INSTANCE);

                    }
                });
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception { //用于指定在服务端启动过程中的一些逻辑
                System.out.println("服务端启动");
            }
        });
        serverBootstrap  //childOption()可以给每条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) //ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
                //ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭，false表示开启，
                //通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.TCP_NODELAY, true);
        //ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败

        //除了给每个连接设置这一系列属性之外，我们还可以给服务端channel设置一些属性
        //表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);

        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    serverBootstrap.bind(port + 1);
                }
            }
        });
    }
}