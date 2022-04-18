package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.AccountTransactions;

@Repository("accounttransactionsDao")
public class AccountTransactionsDaoImpl extends AbstractDao<Integer, AccountTransactions> implements AccountTransactionsDao {

	public void save(AccountTransactions accounttransactions){
		 saveOrUpdate(accounttransactions);
	}

	public List<AccountTransactions> findAllAccountTransactions() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public AccountTransactions findAccountTransactionsByID(int id){
		return getByKey(id);
	}

	public List<AccountTransactions> searchAccountTransactions(AccountTransactions accounttransactions){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(AccountTransactions accounttransactions){
		 delete(accounttransactions);
	}

	public List findAccountTransactionsByPeriod(Period pd){
		return sqlQuery("select id from AccountTransactions where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchAccountTransactionsByName(String str){
		return sqlQuery("select id from AccountTransactions where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from AccountTransactions ");
	}
}
