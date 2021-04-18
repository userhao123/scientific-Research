package com.hao.scientificresearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.scientificresearch.entity.LoginLog;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-04-11
 */
public interface ILoginLogService extends IService<LoginLog> {

    Page<LoginLog> pageByParam(int page, int limit, String condition);

    boolean delete(Integer id);
}
