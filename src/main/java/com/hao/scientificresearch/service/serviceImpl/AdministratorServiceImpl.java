package com.hao.scientificresearch.service.serviceImpl;

import com.hao.scientificresearch.entity.Administrator;
import com.hao.scientificresearch.mapper.AdministratorMapper;
import com.hao.scientificresearch.service.IAdministratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理员信息表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements IAdministratorService {

}
