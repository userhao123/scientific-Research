package com.hao.scientificresearch;

import cn.hutool.json.JSONUtil;
import com.hao.scientificresearch.mapper.OldResearcherMapper;
import com.hao.scientificresearch.model.enums.EducationEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ScientificResearchApplicationTests {

    @Autowired
    private OldResearcherMapper mapper;

    @Test
    void contextLoads() {

//        mapper.selectList(null).forEach(System.out::println);
//        System.out.println(EducationEnum.DOCTER.getCode());
    }

}
