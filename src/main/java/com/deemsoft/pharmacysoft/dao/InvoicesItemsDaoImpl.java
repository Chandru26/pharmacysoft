package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.InvoicesItems;

@Repository("invoicesitemsDao")
public class InvoicesItemsDaoImpl extends AbstractDao<Integer, InvoicesItems> implements InvoicesItemsDao {

	public void save(InvoicesItems invoicesitems){
		 saveOrUpdate(invoicesitems);
	}

	public List<InvoicesItems> findAllInvoicesItems() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public InvoicesItems findInvoicesItemsByID(int id){
		return getByKey(id);
	}

	public List<InvoicesItems> searchInvoicesItems(InvoicesItems invoicesitems){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	
	public void deleteByID(InvoicesItems invoicesitems){
		 delete(invoicesitems);
	}

	public List findInvoicesItemsByPeriod(Period pd){
		return sqlQuery("select invoices_id,barcode,name,price,quantity,discount,tax,subtotal from invoices_items where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	
	public List searchInvoicesItemsByName(String str){
		return sqlQuery("select id from invoices_items where  name like '"+str+"%'");
	}
	
	public List getInvoicesItemsByInvoiceID(int id){
		return sqlQuery("select id, invoices_id, catalogs_id, barcode, name,price,expiration, purchase_price, quantity, discount, tax, subtotal from invoices_items where  invoices_id = "+id);
	}
	
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from invoices_items ");
	}
}
