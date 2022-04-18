package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Accounts;

public interface AccountsDao {

	void save(Accounts accounts);

	List<Accounts> findAllAccounts();

	public Accounts findAccountsByID(int id);

	public List<Accounts> searchAccounts(Accounts accounts);

	public void deleteByID(Accounts accounts);
	public List findAccountsByPeriod(Period pd);
	public List searchAccountsByName(String str);
	public List getMaxID();
}
