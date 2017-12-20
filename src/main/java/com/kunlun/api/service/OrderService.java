package com.kunlun.api.service;

import com.kunlun.result.PageResult;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/20.
 */
public interface OrderService {
    PageResult list(Integer pageNo, Integer pageSize);
}
