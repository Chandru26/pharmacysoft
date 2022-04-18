package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.ReturnstockItems;

public interface ReturnstockItemsDao {

	void save(ReturnstockItems returnstockitems);

	List<ReturnstockItems> findAllReturnstockItems();

	public ReturnstockItems findReturnstockItemsByID(int id);

	public List<ReturnstockItems> searchReturnstockItems(ReturnstockItems returnstockitems);

	public void deleteByID(ReturnstockItems returnstockitems);
	
	public List findReturnstockItemsByPeriod(Period pd);
	
	public List searchReturnstockItemsByName(String str);
	
	public List getReturnstockItemsByReturnID(int id);
	
	public List getMaxID();
}
