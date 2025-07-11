package hjkim27.dev.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hjkim27.dev.handler.IntegerListTypeHandler;
import hjkim27.dev.handler.StringListTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@SpringBootConfiguration
@MapperScan(value = "hjkim27.dev.mapper")
public class DatabaseConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource(hikariConfig());
        log.info("getDriverClassName: {}", dataSource.getDriverClassName());
        log.info("getJdbcUrl: {}", dataSource.getJdbcUrl());
        log.info("getUsername: {}", dataSource.getUsername());
        log.info("getPassword: {}", dataSource.getPassword());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        // mybatis config 객체 설정
        Configuration config = new org.apache.ibatis.session.Configuration();

        // [1] <setting>
        config.setCacheEnabled(false);
        config.setMapUnderscoreToCamelCase(true);

        // [2] <typeAlias>
        config.getTypeAliasRegistry().registerAlias("int", Integer.class);
        config.getTypeAliasRegistry().registerAlias("Integer", Integer.class);
        config.getTypeAliasRegistry().registerAlias("String", String.class);
        config.getTypeAliasRegistry().registerAlias("Boolean", Boolean.class);

        // [3] <typeHandler>
        TypeHandlerRegistry thRegistry = config.getTypeHandlerRegistry();
        thRegistry.register(List.class, JdbcType.ARRAY, IntegerListTypeHandler.class);
        thRegistry.register(List.class, JdbcType.ARRAY, StringListTypeHandler.class);

        sqlSessionFactoryBean.setConfiguration(config);

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("hjkim27.dev.bean.dto");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}
