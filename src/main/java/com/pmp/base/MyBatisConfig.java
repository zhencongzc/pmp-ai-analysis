package com.pmp.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.pmp.domain.mapper",
        sqlSessionFactoryRef = "mysqlSqlSessionFactory"
)
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory mysqlSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 其他配置...
        return factoryBean.getObject();
    }
}