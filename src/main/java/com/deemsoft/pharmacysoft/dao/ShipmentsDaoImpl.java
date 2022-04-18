package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Shipments;

@Repository("shipmentsDao")
public class ShipmentsDaoImpl extends AbstractDao<Integer, Shipments> implements ShipmentsDao {

	public void save(Shipments shipments){
		 saveOrUpdate(shipments);
	}

	public List<Shipments> findAllShipments() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Shipments findShipmentsByID(int id){
		return getByKey(id);
	}

	public List<Shipments> searchShipments(Shipments shipments){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Shipments shipments){
		 delete(shipments);
	}

	public List findShipmentsByPeriod(Period pd){
		return sqlQuery("select id from Shipments where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchShipmentsByName(String str){
		return sqlQuery("select id from Shipments where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from Shipments ");
	}
}
