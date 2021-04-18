package com.hao.scientificresearch;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(CodeGenerator.globalConfig());
        mpg.setDataSource(CodeGenerator.datasourceConfig());
        mpg.setPackageInfo(CodeGenerator.packageConfig());
        mpg.setStrategy(CodeGenerator.strConfig());
        mpg.execute();
    }

    // 全局配置
    public static GlobalConfig globalConfig(){
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
//        String projectPath1 = "D://generatorCode";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("hao");
        gc.setOpen(false);
        gc.setControllerName("%sController");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setEntityName("%s");
        gc.setIdType(IdType.ASSIGN_ID);
        return gc;
    }

    //数据源配置
    public static DataSourceConfig datasourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/scientific_research?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("654123");
        return dsc;
    }

    //包配置
    public static PackageConfig packageConfig(){
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.hao.scientificresearch");
        pc.setController("controller");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.serviceImpl");
        pc.setXml("mapper/xml");
        pc.setEntity("entity");
        return pc;
    }

    //策略配置
    public static StrategyConfig strConfig(){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//         写于父类中的公共字段
        strategy.setSuperEntityColumns("id","deleted");
        //继承的父类
        strategy.setSuperEntityClass("com.hao.scientificresearch.entity.BaseEntity");
        strategy.setInclude("b_project_file");
//        strategy.setLikeTable(new LikeTable("b_"));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("b_");
        return strategy;
    }


    //模板配置
    public static void templateConfig(){
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);
    }
}
