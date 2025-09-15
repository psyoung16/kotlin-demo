package org.psy.demo


import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class RoutingConfiguration : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/api/CLIENT/v3.0/static/**").addResourceLocations("classpath:/static/")
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/static/swagger-ui/")
    }
}
