package com.rrain.springdomesticservicesapp.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.util.Base64;

public class Test {

    // что-то про установку куки....
    /*@Test
    public void
    givenEndpointIsCalledTwiceAndResponseIsReturned_whenMongoDBIsQueriedForCount_thenCountMustBeSame() {

        HttpEntity<String> response = restTemplate
                .exchange("http://localhost:" + 8080, HttpMethod.GET, null, String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        Assert.assertEquals(response.getBody(),
                repository.findById(getSessionId(set_cookie)).getAttribute("count").toString());
    }

    private String getSessionId(String cookie) {
        return new String(Base64.getDecoder().decode(cookie.split(";")[0].split("=")[1]));
    }*/

}
