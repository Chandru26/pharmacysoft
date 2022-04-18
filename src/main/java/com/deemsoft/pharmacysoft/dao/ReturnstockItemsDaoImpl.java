package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.ReturnstockItems;

@Repository("returnstockitemsDao")
public class ReturnstockItemsDaoImpl extends AbstractDao<Integer, ReturnstockItems> implements ReturnstockItemsDao {

	public void save(ReturnstockItems returnstockitems){
		 saveOrUpdate(returnstockitems);
	}

	public List<ReturnstockItems> findAllReturnstockItems() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public ReturnstockItems findReturnstockItemsByID(int id){
		return getByKey(id);
	}

	public List<ReturnstockItems> searchReturnstockItems(ReturnstockItems returnstockitems){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	
	public void deleteByID(ReturnstockItems returnstockitems){
		 delete(returnstockitems);
	}

	public List findReturnstockItemsByPeriod(Period pd){
		return sqlQuery("select return_id,barcode,name,price,quantity,discount,tax,subtotal from returnstock_items where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	
	public List searchReturnstockItemsByName(String str){
		return sqlQuery("select id from returnstock_items where  name like '"+str+"%'");
	}
	
	public List getReturnstockItemsByReturnID(int id){
		return sqlQuery("select id, return_id, catalogs_id, barcode, name,price,expiration, purchase_price, quantity, discount, tax, subtotal from returnstock_items where  return_id = "+id);
	}
	
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from returnstock_items ");
	}
}
