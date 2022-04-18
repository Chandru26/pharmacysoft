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
@Table(name="accounts")
public class Accounts { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="title", nullable=true)
	private String title;
	public String gettitle() { return title; }
	public void settitle( String title ) { this.title = title; }

	@Column(name="firstname", nullable=true)
	private String firstname;
	public String getfirstname() { return firstname; }
	public void setfirstname( String firstname ) { this.firstname = firstname; }

	@Column(name="lastname", nullable=true)
	private String lastname;
	public String getlastname() { return lastname; }
	public void setlastname( String lastname ) { this.lastname = lastname; }
	
	@Column(name="company", nullable=true)
	private String company;
	public String getcompany() { return company; }
	public void setcompany( String company ) { this.company = company; }
	
	@Column(name="description", nullable=true)
	private String description;
	public String getdescription() { return description; }
	public void setdescription( String description ) { this.description = description; }

	@Column(name="phone", nullable=true)
	private String phone;
	public String getphone() { return phone; }
	public void setphone( String phone ) { this.phone = phone; }

	@Column(name="email", nullable=true)
	private String email;
	public String getemail() { return email; }
	public void setemail( String email ) { this.email = email; }

	@Column(name="address_id", nullable=true)
	private int address_id;
	public int getaddress_id() { return address_id; }
	public void setaddress_id( int address_id ) { this.address_id = address_id; }

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
		if (!(obj instanceof Accounts)) return false;
		Accounts other = (Accounts) obj;
		if (id != other.id) return false;
		return true;
	}
}
