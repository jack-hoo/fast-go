//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.config;

import com.flyingcow.fastgo.server.common.filters.JWTAuthenticationFilter;
import com.flyingcow.fastgo.server.common.filters.RestAuthEntryPoint;
import com.flyingcow.fastgo.server.service.SysUserService;
import com.flyingcow.fastgo.server.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class SpringSecutiryConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserServiceImpl userDetailsService;
    @Autowired
    private RestAuthEntryPoint restAuthEntryPoint;

    public SpringSecutiryConfig() {
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
        super.configure(auth);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: 2018/4/12 正式环境需要进行跨域设置
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public JWTAuthenticationFilter jWTAuthenticationFilter() {
        try {
            return new JWTAuthenticationFilter(this.authenticationManager());
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
        public SysUserService sysUserService() {
            return new SysUserServiceImpl();
        }*/
    protected void configure(HttpSecurity http) throws Exception {
//        ((HttpSecurity) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((AuthorizedUrl) ((HttpSecurity) ((HttpSecurity) ((HttpSecurity) http.csrf().disable()).exceptionHandling().authenticationEntryPoint(this.restAuthEntryPoint).and()).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()).addFilter(this.corsFilter()).addFilter(this.jWTAuthenticationFilter()).authorizeRequests().antMatchers(new String[]{"/auth/**"})).permitAll().antMatchers(new String[]{"/pay/**"})).permitAll().antMatchers(new String[]{"/open/consume/**"})).permitAll().antMatchers(new String[]{"/notify/**"})).permitAll().antMatchers(new String[]{"/shopping"})).permitAll().antMatchers(new String[]{"/admin"})).permitAll().antMatchers(new String[]{"/"})).permitAll().antMatchers(new String[]{"/static/**"})).permitAll().anyRequest()).authenticated().and()).headers().cacheControl().and();
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(restAuthEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //必须将跨域过滤器放前面
                .addFilter(corsFilter())
                .addFilter(jWTAuthenticationFilter())
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/pay/**").permitAll()
                .antMatchers("/notify/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().cacheControl()
                .and();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v2/api-docs/**");
    }
}
