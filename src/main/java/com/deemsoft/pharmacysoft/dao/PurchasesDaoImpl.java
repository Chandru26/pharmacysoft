package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Purchases;

@Repository("purchasesDao")
public class PurchasesDaoImpl extends AbstractDao<Integer, Purchases> implements PurchasesDao {

	public void save(Purchases purchases){
		 saveOrUpdate(purchases);
	}

	public List<Purchases> findAllPurchases() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Purchases findPurchasesByID(int id){
		return getByKey(id);
	}

	public List<Purchases> searchPurchases(Purchases purchases){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Purchases purchases){
		 delete(purchases);
	}
	
	public List findPurchasesByPeriod(Period pd){
		return sqlQuery("select * from purchases where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchPurchasesByName(String str){
		return sqlQuery("select id from purchases where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from purchases ");
	}
}
