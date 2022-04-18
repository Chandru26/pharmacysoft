package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Address;

@Repository("addressDao")
public class AddressDaoImpl extends AbstractDao<Integer, Address> implements AddressDao {

	public void save(Address address){
		 saveOrUpdate(address);
	}

	public List<Address> findAllAddress() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Address findAddressByID(int id){
		return getByKey(id);
	}

	public List<Address> searchAddress(Address address){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Address address){
		 delete(address);
	}

	public List findAddressByPeriod(Period pd){
		return sqlQuery("select id from Address where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchAddressByName(String str){
		return sqlQuery("select id from address where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from address ");
	}
}
