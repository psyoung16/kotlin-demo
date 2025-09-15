package org.psy.demo.config

import org.psy.demo.config.db.RoutingDataSource
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration(
    exclude = [
        DataSourceAutoConfiguration::class,
    ]
)
@EnableTransactionManagement
class DataSourceConfiguration {

    @Bean(name = ["writeDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.write.hikari")
    fun writeDataSource(): DataSource {
        return HikariDataSource()
    }

    @Bean(name = ["readOnlyDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.read-only.hikari")
    fun readOnlyDataSource(): DataSource {
        return HikariDataSource()
    }

    @Bean(name = ["routingDataSource"])
    fun routingDataSource(
        @Qualifier("writeDataSource") writeDataSource: DataSource,
        @Qualifier("readOnlyDataSource") readOnlyDataSource: DataSource,
    ): DataSource {
        // Custom Routing DataSource
        return RoutingDataSource(writeDataSource, readOnlyDataSource)
    }


    @Bean
    fun readEntityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(readOnlyDataSource())
            .packages("org.psy.demo")
            .persistenceUnit("readPU")
            .build()
    }

    @Primary
    @Bean(name = ["entityManagerFactory"])
    fun writeEntityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(writeDataSource())
            .packages("org.psy.demo")
            .persistenceUnit("writePU")
            .build()
    }


    @Bean
    fun readTransactionManager(
        @Qualifier("readEntityManagerFactory") factory: EntityManagerFactory?
    ): PlatformTransactionManager {
        return JpaTransactionManager(factory!!)
    }

    @Primary
    @Bean(name = ["transactionManager"])
    fun writeTransactionManager(
        @Qualifier("entityManagerFactory") factory: EntityManagerFactory?
    ): PlatformTransactionManager {
        return JpaTransactionManager(factory!!)
    }

    @Primary
    @Bean(name = ["dataSource"])
    @DependsOn("routingDataSource")
    fun dataSource(
        @Qualifier("routingDataSource") routingDataSource: DataSource,
    ): LazyConnectionDataSourceProxy {
        return LazyConnectionDataSourceProxy(routingDataSource)
    }
}
