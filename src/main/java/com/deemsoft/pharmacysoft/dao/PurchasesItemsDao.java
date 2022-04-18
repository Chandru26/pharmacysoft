package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.PurchasesItems;

public interface PurchasesItemsDao {

	void save(PurchasesItems purchasesitems);

	List<PurchasesItems> findAllPurchasesItems();

	public PurchasesItems findPurchasesItemsByID(int id);

	public List<PurchasesItems> searchPurchasesItems(PurchasesItems purchasesitems);

	public void deleteByID(PurchasesItems purchasesitems);
	
	public List findPurchasesItemsByPeriod(Period pd);
	
	public List findStocksVerificationReport(Period pd);
	
	public List findFastmovingStocksReport(Period pd);
	
	public List findLowStocksReport(Period pd);
	
	public List searchPurchasesItemsByName(String str);
	
	public List getMaxID();
	
	public List getPurchasesItemsbyPurchaseID(int id);
}
