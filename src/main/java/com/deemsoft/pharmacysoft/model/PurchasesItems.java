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
@Table(name="purchases_items")
public class PurchasesItems { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="catalogs_id", nullable=true)
	private int catalogs_id;
	public int getcatalogs_id() { return catalogs_id; }
	public void setcatalogs_id( int catalogs_id ) { this.catalogs_id = catalogs_id; }

	@Column(name="purchases_id", nullable=true)
	private int purchases_id;
	public int getpurchases_id() { return purchases_id; }
	public void setpurchases_id( int purchases_id ) { this.purchases_id = purchases_id; }

	@Column(name="name", nullable=true)
	private String name;
	public String getname() { return name; }
	public void setname( String name ) { this.name = name; }
	
	@Column(name="barcode", nullable=true)
	private String barcode;
	public String getbarcode() { return barcode; }
	public void setbarcode( String barcode ) { this.barcode = barcode; }
	
	@Column(name="expiration", nullable=true)
	private String expiration;
	public String getexpiration() { return expiration; }
	public void setexpiration( String expiration ) { this.expiration = expiration; }

	@Column(name="quantity", nullable=true)
	private int quantity;
	public int getquantity() { return quantity; }
	public void setquantity( int quantity ) { this.quantity = quantity; }

	
	@Column(name="freequantity", nullable=true)
	private int freequantity;
	public int getfreequantity() { return freequantity; }
	public void setfreequantity( int freequantity ) { this.freequantity = freequantity; }

	@Column(name="price", nullable=true)
	private double price;
	public double getprice() { return price; }
	public void setprice( double price ) { this.price = price; }

	@Column(name="purchase_price", nullable=true)
	private double purchase_price;
	public double getpurchase_price() { return purchase_price; }
	public void setpurchase_price( double purchase_price ) { this.purchase_price = purchase_price; }

	@Column(name="tax", nullable=true)
	private double tax;
	public double gettax() { return tax; }
	public void settax( double tax ) { this.tax = tax; }

	@Column(name="discount", nullable=true)
	private double discount;
	public double getdiscount() { return discount; }
	public void setdiscount( double discount ) { this.discount = discount; }

	@Column(name="subtotal", nullable=true)
	private double subtotal;
	public double getsubtotal() { return subtotal; }
	public void setsubtotal( double subtotal ) { this.subtotal = subtotal; }
	
	@Column(name="created", nullable=true)
	private String created;
	public String getcreated() { return created; }
	public void setcreated( String created ) { this.created = created; }


	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof PurchasesItems)) return false;
		PurchasesItems other = (PurchasesItems) obj;
		if (id != other.id) return false;
		return true;
	}
}
