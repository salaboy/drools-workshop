package org.drools.workshop.cepmodel;

public class BlackListedClient {

    private Client client;

    public BlackListedClient() {
        super();
    }

    public BlackListedClient(Client client) {
        this();
        setClient(client);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

