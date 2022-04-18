package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.InvoicesItems;

public interface InvoicesItemsDao {

	void save(InvoicesItems invoicesitems);

	List<InvoicesItems> findAllInvoicesItems();

	public InvoicesItems findInvoicesItemsByID(int id);

	public List<InvoicesItems> searchInvoicesItems(InvoicesItems invoicesitems);

	public void deleteByID(InvoicesItems invoicesitems);
	
	public List findInvoicesItemsByPeriod(Period pd);
	
	public List searchInvoicesItemsByName(String str);
	
	public List getInvoicesItemsByInvoiceID(int id);
	
	public List getMaxID();
}
