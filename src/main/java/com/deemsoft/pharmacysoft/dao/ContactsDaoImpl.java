package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Contacts;

@Repository("contactsDao")
public class ContactsDaoImpl extends AbstractDao<Integer, Contacts> implements ContactsDao {

	public void save(Contacts contacts){
		 saveOrUpdate(contacts);
	}

	public List<Contacts> findAllContacts() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Contacts findContactsByID(int id){
		return getByKey(id);
	}

	public List<Contacts> searchContacts(Contacts contacts){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Contacts contacts){
		 delete(contacts);
	}

	public List findContactsByPeriod(Period pd){
		return sqlQuery("select title,firstname,company,mobile_phone,email from contacts where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchContactsByName(String str){
		return sqlQuery("select id, address_id, firstname, age, mobile_phone,email from contacts where  firstname like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from contacts ");
	}
}
