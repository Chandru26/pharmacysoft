package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Payments;

@Repository("paymentsDao")
public class PaymentsDaoImpl extends AbstractDao<Integer, Payments> implements PaymentsDao {

	public void save(Payments payments){
		 saveOrUpdate(payments);
	}

	public List<Payments> findAllPayments() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Payments findPaymentsByID(int id){
		return getByKey(id);
	}

	public List<Payments> searchPayments(Payments payments){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Payments payments){
		 delete(payments);
	}

	public List findPaymentsByPeriod(Period pd){
		return sqlQuery("select id from Payments where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchPaymentsByName(String str){
		return sqlQuery("select id from Payments where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from Payments ");
	}
}
