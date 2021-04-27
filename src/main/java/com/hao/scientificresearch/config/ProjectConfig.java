package com.hao.scientificresearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    //解决跨域问题
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowedOriginPatterns("http://127.0.0.1:8848")
//                .allowedOrigins("http://127.0.0.1:8848")
//                .allowedHeaders("Origin, X-Requested-With, Content-Type, Accept")
//                .allowedMethods("GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH")
//                .allowCredentials(true)
//                .maxAge(3600);
//
//    }

    //配置登录拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //"/asserts/**","/webjars/**"排除css,js等静态资源
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/page/login","/userLogin/login","index.html",
                        "/css/**","/images/**","/js/**","/layui-v2.5.7.layui/**","/lib/**");
    }

    //配置虚拟路径映射,即当访问/uploadFile路径下的文件实际上是跑到
    //file:/D:/save_data/IntelliJ-IDEA-Ultimate/scientific-Research/uploadFile/这个本地磁盘读取文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFile/**")
                .addResourceLocations("file:/D:/save_data/IntelliJ-IDEA-Ultimate/scientific-Research/uploadFile/");
    }
}
