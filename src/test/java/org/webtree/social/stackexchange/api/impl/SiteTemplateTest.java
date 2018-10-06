package org.webtree.social.stackexchange.api.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.webtree.social.stackexchange.api.ResponseWrapper;
import org.webtree.social.stackexchange.api.Site;

import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * Created by Udjin Skobelev on 06.10.2018.
 */
public class SiteTemplateTest extends AbstractApiTest {

    @Test
    public void shouldReturnSites() throws JsonProcessingException {
        Site stack = new Site("stack", "stack overflow");
        Site apps = new Site("apps", "stack apps");
        List<Site> sites = Arrays.asList(stack, apps);
        responseWrapper = new ResponseWrapper<>(sites, true, 0, 1);

        server
                .expect(requestTo(stackExchange.getBaseApiUrl() + "sites?" + tokenQueryParams))
                .andRespond(withSuccess(objectMapper.writeValueAsString(responseWrapper), MediaType.APPLICATION_JSON));

        List<Site> sitesFromApi = stackExchange.siteOperations().getActualSites();
        assertThat(sitesFromApi).containsExactlyInAnyOrder(stack, apps);
        server.verify();
    }
}