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
@Table(name="settings")
public class Settings { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="currency", nullable=true)
	private String currency;
	public String getcurrency() { return currency; }
	public void setcurrency( String currency ) { this.currency = currency; }

	@Column(name="app_language", nullable=true)
	private String app_language;
	public String getapp_language() { return app_language; }
	public void setapp_language( String app_language ) { this.app_language = app_language; }

	@Column(name="time_zone", nullable=true)
	private String time_zone;
	public String gettime_zone() { return time_zone; }
	public void settime_zone( String time_zone ) { this.time_zone = time_zone; }

	@Column(name="location", nullable=true)
	private String location;
	public String getlocation() { return location; }
	public void setlocation( String location ) { this.location = location; }

	@Column(name="logo", nullable=true)
	private String logo;
	public String getlogo() { return logo; }
	public void setlogo( String logo ) { this.logo = logo; }

	@Column(name="address", nullable=true)
	private String address;
	public String getaddress() { return address; }
	public void setaddress( String address ) { this.address = address; }

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
		if (!(obj instanceof Settings)) return false;
		Settings other = (Settings) obj;
		if (id != other.id) return false;
		return true;
	}
}
