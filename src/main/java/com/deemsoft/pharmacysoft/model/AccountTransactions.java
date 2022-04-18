package com.deemsoft.pharmacysoft.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="account_transactions")
public class AccountTransactions { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="accounts_id", nullable=true)
	private int accounts_id;
	public int getaccounts_id() { return accounts_id; }
	public void setaccounts_id( int accounts_id ) { this.accounts_id = accounts_id; }

	@Column(name="payments_id", nullable=true)
	private int payments_id;
	public int getpayments_id() { return payments_id; }
	public void setpayments_id( int payments_id ) { this.payments_id = payments_id; }

	@Column(name="expense", nullable=true)
	private double expense;
	public double getexpense() { return expense; }
	public void setexpense( double expense ) { this.expense = expense; }

	@Column(name="payment", nullable=true)
	private double payment;
	public double getpayment() { return payment; }
	public void setpayment( double payment ) { this.payment = payment; }

	@Column(name="balance", nullable=true)
	private double balance;
	public double getbalance() { return balance; }
	public void setbalance( double balance ) { this.balance = balance; }



	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof AccountTransactions)) return false;
		AccountTransactions other = (AccountTransactions) obj;
		if (id != other.id) return false;
		return true;
	}
}
