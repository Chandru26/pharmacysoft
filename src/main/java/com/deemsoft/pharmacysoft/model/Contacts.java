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
@Table(name="contacts")
public class Contacts { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="address_id", nullable=true)
	private int address_id;
	public int getaddress_id() { return address_id; }
	public void setaddress_id( int address_id ) { this.address_id = address_id; }

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

	@Column(name="middlename", nullable=true)
	private String middlename;
	public String getmiddlename() { return middlename; }
	public void setmiddlename( String middlename ) { this.middlename = middlename; }

	@Column(name="contact_type", nullable=true)
	private String contact_type;
	public String getcontact_type() { return contact_type; }
	public void setcontact_type( String contact_type ) { this.contact_type = contact_type; }

	@Column(name="occupation", nullable=true)
	private String occupation;
	public String getoccupation() { return occupation; }
	public void setoccupation( String occupation ) { this.occupation = occupation; }

	@Column(name="income", nullable=true)
	private String income;
	public String getincome() { return income; }
	public void setincome( String income ) { this.income = income; }

	@Column(name="company", nullable=true)
	private String company;
	public String getcompany() { return company; }
	public void setcompany( String company ) { this.company = company; }

	@Column(name="age", nullable=true)
	private String age;
	public String getage() { return age; }
	public void setage( String age ) { this.age = age; }

	@Column(name="mobile_phone", nullable=true)
	private String mobile_phone;
	public String getmobile_phone() { return mobile_phone; }
	public void setmobile_phone( String mobile_phone ) { this.mobile_phone = mobile_phone; }

	@Column(name="home_phone", nullable=true)
	private String home_phone;
	public String gethome_phone() { return home_phone; }
	public void sethome_phone( String home_phone ) { this.home_phone = home_phone; }

	@Column(name="office_phone", nullable=true)
	private String office_phone;
	public String getoffice_phone() { return office_phone; }
	public void setoffice_phone( String office_phone ) { this.office_phone = office_phone; }

	@Column(name="email", nullable=true)
	private String email;
	public String getemail() { return email; }
	public void setemail( String email ) { this.email = email; }

	@Column(name="emergency_contactno", nullable=true)
	private String emergency_contactno;
	public String getemergency_contactno() { return emergency_contactno; }
	public void setemergency_contactno( String emergency_contactno ) { this.emergency_contactno = emergency_contactno; }

	@Column(name="referedby", nullable=true)
	private String referedby;
	public String getreferedby() { return referedby; }
	public void setreferedby( String referedby ) { this.referedby = referedby; }

	@Column(name="photo_id", nullable=true)
	private String photo_id;
	public String getphoto_id() { return photo_id; }
	public void setphoto_id( String photo_id ) { this.photo_id = photo_id; }

	@Column(name="photoid_pic", nullable=true)
	private String photoid_pic;
	public String getphotoid_pic() { return photoid_pic; }
	public void setphotoid_pic( String photoid_pic ) { this.photoid_pic = photoid_pic; }

	@Column(name="customer_pic", nullable=true)
	private String customer_pic;
	public String getcustomer_pic() { return customer_pic; }
	public void setcustomer_pic( String customer_pic ) { this.customer_pic = customer_pic; }

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
		if (!(obj instanceof Contacts)) return false;
		Contacts other = (Contacts) obj;
		if (id != other.id) return false;
		return true;
	}
}
