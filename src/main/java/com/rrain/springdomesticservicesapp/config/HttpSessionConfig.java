package com.rrain.springdomesticservicesapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.time.Duration;

/*
https://www.baeldung.com/spring-session-mongodb

Let's take a look at what goes on in the Spring session behind the scenes.

The SessionRepositoryFilter is responsible for most of the work:

converts the HttpSession into a MongoSession
checks if there's a Cookie present, and if so, loads the session data from the store
saves the updated session data in the store
checks the validity of the session
Also, the SessionRepositoryFilter creates a cookie with the name SESSION that is HttpOnly and secure. This cookie contains the session id, which is a Base64-encoded value.
 */




// настройка чтобы сессии автоматически сохранялись в монго
/*
дополнительно в application.properties надо добавить

spring.session.store-type=mongodb
 */
@EnableMongoHttpSession
public class HttpSessionConfig {

    @Bean
    public JdkMongoSessionConverter jdkMongoSessionConverter() {
        return new JdkMongoSessionConverter(Duration.ofMinutes(30));
    }


    // необязательно, использовать для настроек
    @Bean
    public DefaultCookieSerializer customCookieSerializer(){
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();

        //cookieSerializer.setUseHttpOnlyCookie(false);

        return cookieSerializer;
    }

}
