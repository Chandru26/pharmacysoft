package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.AccountTransactions;

public interface AccountTransactionsDao {

	void save(AccountTransactions accounttransactions);

	List<AccountTransactions> findAllAccountTransactions();

	public AccountTransactions findAccountTransactionsByID(int id);

	public List<AccountTransactions> searchAccountTransactions(AccountTransactions accounttransactions);

	public void deleteByID(AccountTransactions accounttransactions);
	public List findAccountTransactionsByPeriod(Period pd);
	public List searchAccountTransactionsByName(String str);
	public List getMaxID();
}
