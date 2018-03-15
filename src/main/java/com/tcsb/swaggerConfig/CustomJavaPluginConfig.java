package com.tcsb.swaggerConfig;

/*import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;*/

/*@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan("com.tcsb.api")*/
public class CustomJavaPluginConfig {

/*private SpringSwaggerConfig springSwaggerConfig;

@Autowired
public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
  this.springSwaggerConfig = springSwaggerConfig;
}

@Bean //Don't forget the @Bean annotation
public SwaggerSpringMvcPlugin customImplementation(){
  return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
        .apiInfo(apiInfo())
        .includePatterns(".*pet.*");
}

private ApiInfo apiInfo() {
  ApiInfo apiInfo = new ApiInfo(
          "My Apps API Title",
          "My Apps API Description",
          "My Apps API terms of service",
          "My Apps API Contact Email",
          "My Apps API Licence Type",
          "My Apps API License URL"
    );
  return apiInfo;
}*/
}
