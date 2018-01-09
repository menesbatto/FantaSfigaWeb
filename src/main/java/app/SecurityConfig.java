//package app;
//
//import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties.SessionCreationPolicy;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class SecurityConfig extends WebMvcConfigurerAdapter {
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//      .csrf().disable()
//      .authorizeRequests()
//        .antMatchers(HttpMethod.POST, "/api/**").authenticated()
//        .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
//        .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
//        .anyRequest().permitAll()
//        .and()
//      .httpBasic().and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//  }
//}