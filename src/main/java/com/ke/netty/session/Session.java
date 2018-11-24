package com.ke.netty.session;

import lombok.Data;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;
}