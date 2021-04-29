package com.rrain.springdomesticservicesapp.config;

import com.rrain.springdomesticservicesapp.services.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired MongoUserDetailsService mongoUserDetailsService;
    @Autowired PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new MongoUserDetailsService();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    // TODO: 31.03.2021 CSRF, JWT & OAuth2 (Spring built-in), автоаутентификация через POST

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()/*.and()*/
                //.cors().disable()
                .csrf().disable()

                /*.logout()
                .logoutUrl("/logout")
                //.logoutSuccessUrl("")
                .permitAll()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                })
                .and()*/
                .logout().disable()


                //.formLogin().and()


                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST,"/login", "/authenticate").permitAll()

                .antMatchers("/profile/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")

                //.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                /*.and()
                .httpBasic()*/
        ;

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mongoUserDetailsService).passwordEncoder(passwordEncoder);
    }




    /*
    CORS - Cross-origin resource sharing — технология современных браузеров,
    которая позволяет предоставить веб-странице доступ к ресурсам другого домена.
    (когда сервер и клиент на разных доменах - по факту как минимум на разных портах, т.е. на разных приложениях)
    CORS - это именно функция браузера, которую он требует, а не Spring или React
     */
    /*
    React работает на домене, где сервер Node, а Spring REST - другой домен и другой сервер
     */
    /*@Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        // setAllowOrigins -> * & setAllowCredentials -> true IS NOT ALLOWED
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // указывать адрес обязательно БЕЗ слэша в конце
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://192.168.0.50:3000",
                        "http://localhost:5000")
                /*.allowedOrigins(,

                        "http://192.168.43.1:3000",
                        "http://192.168.0.12:3000"
                        "http://192.168.43.1:3000",
                        "http://localhost:5065",
                        "http://*:3000")*/
                .allowedHeaders("*")
                //хз пока что это
                //.exposedHeaders("header1", "header2")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "HEAD")
                .maxAge(3600);

    }


}
