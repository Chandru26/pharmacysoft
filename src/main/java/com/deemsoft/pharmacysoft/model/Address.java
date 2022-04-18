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
@Table(name="address")
public class Address { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="address_type", nullable=true)
	private String address_type;
	public String getaddress_type() { return address_type; }
	public void setaddress_type( String address_type ) { this.address_type = address_type; }

	@Column(name="address1", nullable=true)
	private String address1;
	public String getaddress1() { return address1; }
	public void setaddress1( String address1 ) { this.address1 = address1; }

	@Column(name="address2", nullable=true)
	private String address2;
	public String getaddress2() { return address2; }
	public void setaddress2( String address2 ) { this.address2 = address2; }

	@Column(name="city", nullable=true)
	private String city;
	public String getcity() { return city; }
	public void setcity( String city ) { this.city = city; }

	@Column(name="province", nullable=true)
	private String province;
	public String getprovince() { return province; }
	public void setprovince( String province ) { this.province = province; }

	@Column(name="zipcode", nullable=true)
	private String zipcode;
	public String getzipcode() { return zipcode; }
	public void setzipcode( String zipcode ) { this.zipcode = zipcode; }

	@Column(name="country", nullable=true)
	private String country;
	public String getcountry() { return country; }
	public void setcountry( String country ) { this.country = country; }

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
		if (!(obj instanceof Address)) return false;
		Address other = (Address) obj;
		if (id != other.id) return false;
		return true;
	}
}
