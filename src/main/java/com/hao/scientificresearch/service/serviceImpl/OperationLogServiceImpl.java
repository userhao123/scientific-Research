package com.hao.scientificresearch.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.scientificresearch.entity.OperationLog;
import com.hao.scientificresearch.exception.ParamException;
import com.hao.scientificresearch.mapper.OperationLogMapper;
import com.hao.scientificresearch.service.IOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

    @Override
    public Page<OperationLog> pageByParam(int page, int limit, String condition) {
        LambdaQueryWrapper<OperationLog> wrapper = Wrappers.lambdaQuery(OperationLog.class);
        if (StringUtils.isNotBlank(condition)){
            wrapper.eq(OperationLog::getUserName,condition);
        }
        wrapper.orderByDesc(OperationLog::getOperateTime);
        return this.page(new Page<>(page, limit), wrapper);
    }

    @Override
    public boolean delete(Integer id) {
        OperationLog log = this.getById(id);
        if(log==null){
            throw new ParamException("日志不存在");
        }
        return this.removeById(id);
    }
}
