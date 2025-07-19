package com.pmp.infrastructure.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * MyBatis 的多数据源配置类
 */

@Primary
@Configuration
@MapperScan(basePackages = "com.pmp.mapper", sqlSessionFactoryRef = "pmpSqlSessionFactory")
public class MyBatisConfig {
    // 从配置文件中，获取数据库的相关配置
    @Bean(name = "pmpDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.pmp-ai-analysis")
    public DataSourceProperties pmpDataSourceProperties() {
        return new DataSourceProperties();
    }

    // DataSource的实例创建
    @Bean(name = "pmpDataSource")
    public DataSource pmpDataSource(@Qualifier("pmpDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    // ibatis 对应的SqlSession工厂类
    @Bean("pmpSqlSessionFactory")
    public SqlSessionFactory pmpSqlSessionFactory(DataSource pmpDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(pmpDataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean("peaceSugarSqlSessionTemplate")
    public SqlSessionTemplate pmpSqlSessionTemplate(SqlSessionFactory pmpSqlSessionFactory) {
        return new SqlSessionTemplate(pmpSqlSessionFactory);
    }
}
