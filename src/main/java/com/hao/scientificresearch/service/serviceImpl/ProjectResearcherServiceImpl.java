package com.hao.scientificresearch.service.serviceImpl;

import com.hao.scientificresearch.entity.ProjectResearcher;
import com.hao.scientificresearch.mapper.ProjectResearcherMapper;
import com.hao.scientificresearch.service.IProjectResearcherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目与参与人关系表（多对多） 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-03-31
 */
@Service
public class ProjectResearcherServiceImpl extends ServiceImpl<ProjectResearcherMapper, ProjectResearcher> implements IProjectResearcherService {

}
