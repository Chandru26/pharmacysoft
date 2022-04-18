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
@Table(name="shipments")
public class Shipments { 
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	public int getid() { return id; }
	public void setid(int id ) { this.id = id; }


	@Column(name="address_id", nullable=true)
	private int address_id;
	public int getaddress_id() { return address_id; }
	public void setaddress_id( int address_id ) { this.address_id = address_id; }

	@Column(name="type", nullable=true)
	private String type;
	public String gettype() { return type; }
	public void settype( String type ) { this.type = type; }

	@Column(name="vendor", nullable=true)
	private String vendor;
	public String getvendor() { return vendor; }
	public void setvendor( String vendor ) { this.vendor = vendor; }

	@Column(name="cost", nullable=true)
	private double cost;
	public double getcost() { return cost; }
	public void setcost( double cost ) { this.cost = cost; }

	@Column(name="weight", nullable=true)
	private double weight;
	public double getweight() { return weight; }
	public void setweight( double weight ) { this.weight = weight; }

	@Column(name="hight", nullable=true)
	private double hight;
	public double gethight() { return hight; }
	public void sethight( double hight ) { this.hight = hight; }

	@Column(name="width", nullable=true)
	private double width;
	public double getwidth() { return width; }
	public void setwidth( double width ) { this.width = width; }

	@Column(name="length", nullable=true)
	private double length;
	public double getlength() { return length; }
	public void setlength( double length ) { this.length = length; }

	@Column(name="customs_duty", nullable=true)
	private double customs_duty;
	public double getcustoms_duty() { return customs_duty; }
	public void setcustoms_duty( double customs_duty ) { this.customs_duty = customs_duty; }

	@Column(name="customs_notes", nullable=true)
	private String customs_notes;
	public String getcustoms_notes() { return customs_notes; }
	public void setcustoms_notes( String customs_notes ) { this.customs_notes = customs_notes; }

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
		if (!(obj instanceof Shipments)) return false;
		Shipments other = (Shipments) obj;
		if (id != other.id) return false;
		return true;
	}
}
