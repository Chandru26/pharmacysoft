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
@Table(name="payments")
public class Payments { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="address_id", nullable=true)
	private int address_id;
	public int getaddress_id() { return address_id; }
	public void setaddress_id( int address_id ) { this.address_id = address_id; }

	@Column(name="payment_type", nullable=true)
	private String payment_type;
	public String getpayment_type() { return payment_type; }
	public void setpayment_type( String payment_type ) { this.payment_type = payment_type; }

	@Column(name="payment", nullable=true)
	private double payment;
	public double getpayment() { return payment; }
	public void setpayment( double payment ) { this.payment = payment; }

	@Column(name="created_by", nullable=true)
	private int created_by;
	public int getcreated_by() { return created_by; }
	public void setcreated_by( int created_by ) { this.created_by = created_by; }

	@Column(name="updated_by", nullable=true)
	private int updated_by;
	public int getupdated_by() { return updated_by; }
	public void setupdated_by( int updated_by ) { this.updated_by = updated_by; }

	@Column(name="created", nullable=true)
	private String created;
	public String getcreated() { return created; }
	public void setcreated( String created ) { this.created = created; }

	@Column(name="updated", nullable=true)
	private String updated;
	public String getupdated() { return updated; }
	public void setupdated( String updated ) { this.updated = updated; }



	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Payments)) return false;
		Payments other = (Payments) obj;
		if (id != other.id) return false;
		return true;
	}
}
