package com.nc.bookservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;

    @Autowired
    public WebSecurityConfig(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(getPasswordEncoder())
                .usersByUsernameQuery(
                        "select username, password, 'true' as enabled from usr where username = ?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
    }
}