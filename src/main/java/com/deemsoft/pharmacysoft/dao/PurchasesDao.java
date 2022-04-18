package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Purchases;

public interface PurchasesDao {

	void save(Purchases purchases);

	List<Purchases> findAllPurchases();

	public Purchases findPurchasesByID(int id);

	public List<Purchases> searchPurchases(Purchases purchases);

	public void deleteByID(Purchases purchases);
	public List findPurchasesByPeriod(Period pd);
	public List searchPurchasesByName(String str);
	public List getMaxID();
}
