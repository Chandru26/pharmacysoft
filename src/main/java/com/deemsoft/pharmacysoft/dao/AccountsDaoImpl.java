package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Accounts;

@Repository("accountsDao")
public class AccountsDaoImpl extends AbstractDao<Integer, Accounts> implements AccountsDao {

	public void save(Accounts accounts){
		 saveOrUpdate(accounts);
	}

	public List<Accounts> findAllAccounts() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Accounts findAccountsByID(int id){
		return getByKey(id);
	}

	public List<Accounts> searchAccounts(Accounts accounts){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Accounts accounts){
		 delete(accounts);
	}

	public List findAccountsByPeriod(Period pd){
		return sqlQuery("select title,firstname,company,phone,email from accounts where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchAccountsByName(String str){
		return sqlQuery("select id from accounts where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from accounts ");
	}
}
