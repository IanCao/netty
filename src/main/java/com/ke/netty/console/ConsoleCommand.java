package com.ke.netty.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
