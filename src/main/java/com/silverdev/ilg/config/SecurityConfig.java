package com.silverdev.ilg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        @Autowired
        public SecurityConfig(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            /*http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin").permitAll();*/

            http
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admFuncionarios","/admFuncionarios/**").hasRole("ADMIN")
                    .antMatchers("/admCursos","/admCursos/**").hasRole("ADMIN")
                    .antMatchers("/admTurmas","/admTurmas/**").hasRole("ADMIN")
                    .antMatchers("/aluno","/aluno/**").hasRole("ALUNO")
                    .antMatchers("/ingresso", "/ingresso/**").permitAll()
                    .antMatchers("/ingressante").hasRole("INGRESSANTE")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/redirectTipoUsuario", true)
                    .permitAll();
            http
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout");
            http
                    .authorizeRequests()
                    .antMatchers("/register", "/js/**", "/css/**", "/img/**",
                            "/jquery/**","/popper/**","/font-awesome/**", "/register/username")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf()
                    .disable();

        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }


}

