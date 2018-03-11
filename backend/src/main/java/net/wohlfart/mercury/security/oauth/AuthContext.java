package net.wohlfart.mercury.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.Map;

@EqualsAndHashCode
public class AuthContext {

    final String accessTokenKey = "access_token";

    static final String CODE_RESPONSE = "code_response";

    static final String ACCESS_TOKEN_RESPONSE = "access_token_response";


    private final ObjectMapper mapper = new ObjectMapper();

    private final ObjectNode root = JsonNodeFactory.instance.objectNode();

    @Id
    private String userId;

    private ObjectNode currentProviderNode;

    private String currentProviderKey;

    public AuthContext useProvider(String provider) {
        this.currentProviderNode = mapper.createObjectNode();
        this.currentProviderKey = provider;
        this.root.set(this.currentProviderKey, this.currentProviderNode);
        return this;
    }

    protected String getFromCurrentProvider(String path) {
        return currentProviderNode.at(path).asText();
    }

    public AuthContext collectRedirectParameters(Map<String, String[]> redirectParameters) {
        ObjectNode container = mapper.createObjectNode();
        redirectParameters.forEach((fieldName, parameters) -> {
            if (parameters.length != 1) {
                throw new IllegalArgumentException(
                    "multiple values for field '" + fieldName + "' value are '" + Arrays.toString(parameters) + "'");
            }
            container.put(fieldName, parameters[1]);
        });
        currentProviderNode.set(CODE_RESPONSE, container);
        return this;
    }

    public AuthContext collectAccessTokenParameters(Map<String, String> accessTokenParameters) {
        ObjectNode container = mapper.createObjectNode();
        accessTokenParameters.forEach(container::put);
        currentProviderNode.set(ACCESS_TOKEN_RESPONSE, container);
        return this;
    }


    public String getCode() {
        return getFromCurrentProvider(CODE_RESPONSE + "." + "code");
    }

    public String getAccessToken() {
        return getFromCurrentProvider(ACCESS_TOKEN_RESPONSE + "." + "access_token");
    }

}
