package com.example.demo.config;


import com.example.demo.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserService appUserService;

    /**
     * for in memory authentication
     * @return
     */

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("shivam").password("$2a$10$aBtB0A4e0SO1GrddXwFP0enjSBDxbTKfFbPUg4WLiPih1G.n4QgbO")
//                .roles("admin", "user")
//                .and().withUser("ravi").password("$2a$10$aBtB0A4e0SO1GrddXwFP0enjSBDxbTKfFbPUg4WLiPih1G.n4QgbO")
//                .roles("user");
//    }

    /**
     * for db authentication
     * @return
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("1234"));
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().antMatchers("/hello/*").hasRole("user")
//                        .antMatchers("/product/*").permitAll();

        http.authorizeHttpRequests().antMatchers("/api/resident/*").hasAuthority("resident")
                .antMatchers("/api/admin/*").hasAuthority("admin");
        http.formLogin();
        http.httpBasic();
       http.csrf().disable();
    }



}
