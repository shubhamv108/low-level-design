package ratelimiter.proxies;

import ratelimiter.components.ClientIdentifierBuilderClient;

public class ClientIdentifierBuilderProxy {

    private final ClientIdentifierBuilderClient client;

    public ClientIdentifierBuilderProxy(final ClientIdentifierBuilderClient client) {
        this.client = client;
    }
}
