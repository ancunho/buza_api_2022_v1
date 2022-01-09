package buza.group.api.config;

import buza.group.api.filter.CaptchaFilter;
import buza.group.api.filter.JwtAuthenticationTokenFilter;
import buza.group.api.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    CaptchaFilter captchaFilter;

    @Resource
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter(authenticationManagerBean());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        //config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8089", "http://127.0.0.1:9090"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setMaxAge((long) 3600);
        config.setAllowCredentials(false);
        config.addExposedHeader("token");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("Content-Disposition");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                    //自定义认证成功处理器
                    .successHandler(jwtAuthenticationSuccessHandler)
                    // 自定义失败拦截器
                    .failureHandler(loginFailureHandler)
                    // 自定义登录拦截URI
                    .loginProcessingUrl("/login")
                .and()
                    .logout()
                    .logoutSuccessHandler(new LogoutSeccussHandler())
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                .and()
                    //token的验证方式不需要开启csrf的防护
                    .csrf().disable()
                    // 自定义认证失败类
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
    //                // 自定义权限不足处理类
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                    //设置无状态的连接,即不创建session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/captcha").permitAll()
                    .antMatchers("/logout").permitAll()
                    .antMatchers("/doc.html").permitAll()
                    .antMatchers("/api/mgt/**").permitAll()
//                    .antMatchers("/**").permitAll()
                    //配置允许匿名访问的路径
                    .anyRequest().authenticated();
        // 解决跨域问题（重要）  只有在前端请求接口时才发现需要这个
        httpSecurity.cors().configurationSource(corsConfigurationSource()).and().csrf().disable();


        //配置自己的jwt验证过滤器
        httpSecurity
                .addFilter(authenticationTokenFilterBean())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);


        // disable page caching
        httpSecurity.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/webjars/**"
                ,"/swagger-resources"
                ,"/swagger-resources/**"
                ,"/v2/api-docs/**"
                ,"/favicon.ico"
                ,"/static/**"
//                ,"/favicon.ico"
//                ,"/webjars/**"
        );
    }
}
