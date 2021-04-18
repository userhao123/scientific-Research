package com.hao.scientificresearch.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.scientificresearch.entity.LoginLog;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.LoginLogMapper;
import com.hao.scientificresearch.service.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-04-11
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Override
    public Page<LoginLog> pageByParam(int page, int limit, String condition) {
        LambdaQueryWrapper<LoginLog> wrapper = Wrappers.lambdaQuery(LoginLog.class);
        if (StringUtils.isNotBlank(condition)){
            wrapper.eq(LoginLog::getUserName,condition);
        }
        wrapper.orderByDesc(LoginLog::getLoginTime);
        return this.page(new Page<>(page, limit), wrapper);
    }

    @Override
    public boolean delete(Integer id) {
        LoginLog log = this.getById(id);
        if(log==null){
            throw new ParamException("日志不存在");
        }
        return this.removeById(id);
    }
}
