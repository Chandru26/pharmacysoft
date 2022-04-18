package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Invoices;

public interface InvoicesDao {

	void save(Invoices invoices);

	List<Invoices> findAllInvoices();

	public Invoices findInvoicesByID(int id);

	public List<Invoices> searchInvoices(Invoices invoices);

	public void deleteByID(Invoices invoices);
	
	public List ListInvoicesByStatusAndUser(int usr);
	
	public List findInvoicesByPeriod(Period pd);
	
	public List findSalesByPeriod(Period pd);
	
	public List searchInvoicesByName(String str);
	
	public List getMaxID();
}
