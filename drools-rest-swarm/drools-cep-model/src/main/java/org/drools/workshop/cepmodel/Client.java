package org.drools.workshop.cepmodel;

public class Client {

    private String name;

    public Client() {
    }

    public Client(String name) {
        this();
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

