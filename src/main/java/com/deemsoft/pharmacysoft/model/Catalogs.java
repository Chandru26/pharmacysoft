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
@Table(name="catalogs")
public class Catalogs { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="barcode", nullable=true)
	private String barcode;
	public String getbarcode() { return barcode; }
	public void setbarcode( String barcode ) { this.barcode = barcode; }

	@Column(name="name", nullable=true)
	private String name;
	public String getname() { return name; }
	public void setname( String name ) { this.name = name; }
	
	@Column(name="expiration", nullable=true)
	private String expiration;
	public String getexpiration() { return expiration; }
	public void setexpiration( String expiration ) { this.expiration = expiration; }
	
	@Column(name="company", nullable=true)
	private String company;
	public String getcompany() { return company; }
	public void setcompany( String company ) { this.company = company; }

	@Column(name="catl_type", nullable=true)
	private String catl_type;
	public String getcatl_type() { return catl_type; }
	public void setcatl_type( String catl_type ) { this.catl_type = catl_type; }
	
	@Column(name="supplier_name", nullable=true)
	private String supplier_name;
	public String getsupplier_name() { return supplier_name; }
	public void setsupplier_name( String supplier_name ) { this.supplier_name = supplier_name; }
	
	@Column(name="catl_free", nullable=true)
	private int catl_free;
	public int getcatl_free() { return catl_free; }
	public void setcatl_free( int catl_free ) { this.catl_free = catl_free; }

	@Column(name="hsn_no", nullable=true)
	private int hsn_no;
	public int gethsn_no() { return hsn_no; }
	public void sethsn_no( int hsn_no ) { this.hsn_no = hsn_no; }
	
	@Column(name="gst", nullable=true)
	private int gst;
	public int getgst() { return gst; }
	public void setgst( int gst ) { this.gst = gst; }

	@Column(name="description", nullable=true)
	private String description;
	public String getdescription() { return description; }
	public void setdescription( String description ) { this.description = description; }
	
	@Column(name="manufacturer_contacts_id", nullable=true)
	private int manufacturer_contacts_id;
	public int getmanufacturer_contacts_id() { return manufacturer_contacts_id; }
	public void setmanufacturer_contacts_id( int manufacturer_contacts_id ) { this.manufacturer_contacts_id = manufacturer_contacts_id; }

	@Column(name="supplier_contacts_id", nullable=true)
	private int supplier_contacts_id;
	public int getsupplier_contacts_id() { return supplier_contacts_id; }
	public void setsupplier_contacts_id( int supplier_contacts_id ) { this.supplier_contacts_id = supplier_contacts_id; }

	@Column(name="msrp", nullable=true)
	private double msrp;
	public double getmsrp() { return msrp; }
	public void setmsrp( double msrp ) { this.msrp = msrp; }

	@Column(name="purchase_price", nullable=true)
	private double purchase_price;
	public double getpurchase_price() { return purchase_price; }
	public void setpurchase_price( double purchase_price ) { this.purchase_price = purchase_price; }

	@Column(name="sale_price", nullable=true)
	private double sale_price;
	public double getsale_price() { return sale_price; }
	public void setsale_price( double sale_price ) { this.sale_price = sale_price; }

	@Column(name="market_price", nullable=true)
	private double market_price;
	public double getmarket_price() { return market_price; }
	public void setmarket_price( double market_price ) { this.market_price = market_price; }

	@Column(name="max_discount", nullable=true)
	private double max_discount;
	public double getmax_discount() { return max_discount; }
	public void setmax_discount( double max_discount ) { this.max_discount = max_discount; }

	@Column(name="tax", nullable=true)
	private double tax;
	public double gettax() { return tax; }
	public void settax( double tax ) { this.tax = tax; }

	@Column(name="quantity", nullable=true)
	private int quantity;
	public int getquantity() { return quantity; }
	public void setquantity( int quantity ) { this.quantity = quantity; }

	@Column(name="category", nullable=true)
	private String category;
	public String getcategory() { return category; }
	public void setcategory( String category ) { this.category = category; }

	@Column(name="subcategory", nullable=true)
	private String subcategory;
	public String getsubcategory() { return subcategory; }
	public void setsubcategory( String subcategory ) { this.subcategory = subcategory; }

	@Column(name="instructions", nullable=true)
	private String instructions;
	public String getinstructions() { return instructions; }
	public void setinstructions( String instructions ) { this.instructions = instructions; }

	@Column(name="location", nullable=true)
	private String location;
	public String getlocation() { return location; }
	public void setlocation( String location ) { this.location = location; }

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
		if (!(obj instanceof Catalogs)) return false;
		Catalogs other = (Catalogs) obj;
		if (id != other.id) return false;
		return true;
	}
}
