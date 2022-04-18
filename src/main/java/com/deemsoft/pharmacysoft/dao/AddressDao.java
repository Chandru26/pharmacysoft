package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Address;

public interface AddressDao {

	void save(Address address);

	List<Address> findAllAddress();

	public Address findAddressByID(int id);

	public List<Address> searchAddress(Address address);

	public void deleteByID(Address address);
	public List findAddressByPeriod(Period pd);
	public List searchAddressByName(String str);
	public List getMaxID();
}
