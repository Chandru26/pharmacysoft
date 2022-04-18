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
@Table(name="purchases")
public class Purchases { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="purchase_date", nullable=true)
	private String purchase_date;
	public String getpurchase_date() { return purchase_date; }
	public void setpurchase_date( String purchase_date ) { this.purchase_date = purchase_date; }

	@Column(name="title", nullable=true)
	private String title;
	public String gettitle() { return title; }
	public void settitle( String title ) { this.title = title; }

	@Column(name="description", nullable=true)
	private String description;
	public String getdescription() { return description; }
	public void setdescription( String description ) { this.description = description; }

	@Column(name="contacts_id", nullable=true)
	private int contacts_id;
	public int getcontacts_id() { return contacts_id; }
	public void setcontacts_id( int contacts_id ) { this.contacts_id = contacts_id; }

	@Column(name="payments_id", nullable=true)
	private int payments_id;
	public int getpayments_id() { return payments_id; }
	public void setpayments_id( int payments_id ) { this.payments_id = payments_id; }

	@Column(name="shipments_id", nullable=true)
	private int shipments_id;
	public int getshipments_id() { return shipments_id; }
	public void setshipments_id( int shipments_id ) { this.shipments_id = shipments_id; }
	
	@Column(name="cgst_amount", nullable=true)
	private int cgst_amount;
	public int getcgst_amount() { return cgst_amount; }
	public void setcgst_amount( int cgst_amount ) { this.cgst_amount = cgst_amount; }

	@Column(name="sgst_amount", nullable=true)
	private int sgst_amount;
	public int getsgst_amount() { return sgst_amount; }
	public void setsgst_amount( int sgst_amount ) { this.sgst_amount = sgst_amount; }
	
	@Column(name="total", nullable=true)
	private double total;
	public double gettotal() { return total; }
	public void settotal( double total ) { this.total = total; }

	@Column(name="discount", nullable=true)
	private double discount;
	public double getdiscount() { return discount; }
	public void setdiscount( double discount ) { this.discount = discount; }

	@Column(name="tax", nullable=true)
	private double tax;
	public double gettax() { return tax; }
	public void settax( double tax ) { this.tax = tax; }

	@Column(name="nettotal", nullable=true)
	private double nettotal;
	public double getnettotal() { return nettotal; }
	public void setnettotal( double nettotal ) { this.nettotal = nettotal; }

	@Column(name="paid", nullable=true)
	private double paid;
	public double getpaid() { return paid; }
	public void setpaid( double paid ) { this.paid = paid; }

	@Column(name="balance", nullable=true)
	private double balance;
	public double getbalance() { return balance; }
	public void setbalance( double balance ) { this.balance = balance; }
	
	@Column(name="billamount", nullable=true)
	private double billamount;
	public double getbillamount() { return billamount; }
	public void setbillamount( double billamount ) { this.billamount = billamount; }

	@Column(name="billno", nullable=true)
	private String billno;
	public String getbillno() { return billno; }
	public void setbillno( String billno ) { this.billno = billno; }

	
	@Column(name="notes", nullable=true)
	private String notes;
	public String getnotes() { return notes; }
	public void setnotes( String notes ) { this.notes = notes; }

	@Column(name="checkedout", nullable=true)
	private int checkedout;
	public int getcheckedout() { return checkedout; }
	public void setcheckedout( int checkedout ) { this.checkedout = checkedout; }

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
		if (!(obj instanceof Purchases)) return false;
		Purchases other = (Purchases) obj;
		if (id != other.id) return false;
		return true;
	}
}
