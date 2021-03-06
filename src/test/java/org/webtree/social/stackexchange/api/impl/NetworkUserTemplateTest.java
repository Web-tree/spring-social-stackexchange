package org.webtree.social.stackexchange.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.webtree.social.stackexchange.domain.NetworkUser;
import org.webtree.social.stackexchange.domain.ResponseWrapper;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by Udjin Skobelev on 20.10.2018.
 */
public class NetworkUserTemplateTest extends AbstractStackObjectFetcherTest {

    @Test
    public void shouldReturnNetworkUsers() throws JsonProcessingException {
        NetworkUser fromStack = new NetworkUser(2, "stack");
        NetworkUser fromMath = new NetworkUser(3, "math");
        List<NetworkUser> users = Arrays.asList(fromStack, fromMath);
        responseWrapper = new ResponseWrapper<>(users, true, 1, 2);

        server
                .expect(requestTo(baseApiUrl + "me/associated?" + tokenQueryParams))
                .andRespond(withSuccess(objectMapper.writeValueAsString(responseWrapper), MediaType.APPLICATION_JSON));

        List<NetworkUser> usersFromApi = stackExchange.networkUserOperations().getUserAssociatedAccounts();
        assertThat(usersFromApi).containsExactlyInAnyOrder(fromStack, fromMath);
    }
}
