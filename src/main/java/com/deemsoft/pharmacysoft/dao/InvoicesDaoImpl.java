package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Invoices;

@Repository("invoicesDao")
public class InvoicesDaoImpl extends AbstractDao<Integer, Invoices> implements InvoicesDao {

	public void save(Invoices invoices){
		 saveOrUpdate(invoices);
	}

	public List<Invoices> findAllInvoices() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Invoices findInvoicesByID(int id){
		return getByKey(id);
	}

	public List<Invoices> searchInvoices(Invoices invoices){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Invoices invoices){
		 delete(invoices);
	}
	
	public List ListInvoicesByStatusAndUser(int usr){
		return sqlQuery("select id,title from invoices where  status=0 and created_by="+usr);
	}
	public List findInvoicesByPeriod(Period pd){
		return sqlQuery("select * from invoices where invoice_date BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	
	public List findSalesByPeriod(Period pd){
		return sqlQuery("select  a.id,a.invoice_date,b.invoices_id,b.barcode,b.name, SUM(b.quantity) as quantity from invoices as a,invoices_items as b  where a.id = b.invoices_id and a.invoice_date BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE) group by b.barcode");
	}
	
	public List searchInvoicesByName(String str){
		return sqlQuery("select id from invoices where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from invoices ");
	}
}
