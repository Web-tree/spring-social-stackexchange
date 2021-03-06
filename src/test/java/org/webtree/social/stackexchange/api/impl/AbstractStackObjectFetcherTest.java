package org.webtree.social.stackexchange.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.client.MockRestServiceServer;
import org.webtree.social.stackexchange.domain.ResponseWrapper;
import org.webtree.social.stackexchange.api.StackExchange;

/**
 * Created by Udjin Skobelev on 06.10.2018.
 */

public abstract class AbstractStackObjectFetcherTest {
    private static String TOKEN = "abcde";
    private static String KEY = "fghij";

    protected StackExchange stackExchange;
    protected MockRestServiceServer server;
    protected ResponseWrapper responseWrapper;
    protected ObjectMapper objectMapper;
    protected String tokenQueryParams;
    protected String baseApiUrl;

    @BeforeEach
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        stackExchange = new StackExchangeTemplate(TOKEN, KEY);
        server = MockRestServiceServer.createServer(((StackExchangeTemplate) stackExchange).getRestTemplate());
        tokenQueryParams =  "key=" + KEY + "&" + "access_token=" + TOKEN;
        baseApiUrl = ((StackExchangeTemplate) stackExchange).getBaseApiUrl();
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.verify();
    }
}
