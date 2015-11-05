package org.drools.workshop.cepmodel;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {

    private Date transactionTime = new Date();
    private Client client;

    public Transaction() {
        super();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Client getClient() {
		return client;
	}
    
    public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result
				+ ((transactionTime == null) ? 0 : transactionTime.hashCode());
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
		Transaction other = (Transaction) obj;
		if (client == null) {
			if (other.client != null) {
				return false;
			}
		} else if (!client.equals(other.client)) {
			return false;
		}
		if (transactionTime == null) {
			if (other.transactionTime != null) {
				return false;
			}
		} else if (!transactionTime.equals(other.transactionTime)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionTime=" + transactionTime + ", client=" + client + "]";
	}
}

