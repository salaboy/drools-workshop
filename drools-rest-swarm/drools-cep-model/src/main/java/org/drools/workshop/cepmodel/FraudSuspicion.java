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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result
				+ ((transactions == null) ? 0 : transactions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FraudSuspicion other = (FraudSuspicion) obj;
		if (client == null) {
			if (other.client != null) {
				return false;
			}
		} else if (!client.equals(other.client)) {
			return false;
		}
		if (transactions == null) {
			if (other.transactions != null) {
				return false;
			}
		} else if (!transactions.equals(other.transactions)) {
			return false;
		}
		return true;
	}
}

