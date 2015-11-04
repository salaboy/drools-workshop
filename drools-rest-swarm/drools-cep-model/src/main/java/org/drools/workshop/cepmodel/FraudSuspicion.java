package org.drools.workshop.cepmodel;

import java.util.List;

public class FraudSuspicion {

    private Client client;
    private List<?> transactions;

    public FraudSuspicion() {
    }

    public FraudSuspicion(Client client, List<?> transactions) {
         this();
         this.client = client;
         this.transactions = transactions;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) { 
        this.client = client;
    }

    public List<?> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<?> transactions) {
        this.transactions = transactions;
    }
}

