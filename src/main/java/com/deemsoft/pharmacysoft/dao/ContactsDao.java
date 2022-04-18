package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Contacts;

public interface ContactsDao {

	void save(Contacts contacts);

	List<Contacts> findAllContacts();

	public Contacts findContactsByID(int id);

	public List<Contacts> searchContacts(Contacts contacts);

	public void deleteByID(Contacts contacts);
	public List findContactsByPeriod(Period pd);
	public List searchContactsByName(String str);
	public List getMaxID();
}
