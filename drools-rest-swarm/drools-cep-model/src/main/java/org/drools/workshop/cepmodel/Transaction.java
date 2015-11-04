package org.drools.workshop.cepmodel;

import java.util.Date;

public class Transaction {

    private Date transactionTime = new Date();

    public Transaction() {
        super();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }
}

