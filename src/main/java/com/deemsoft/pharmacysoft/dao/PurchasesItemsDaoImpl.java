package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.PurchasesItems;

@Repository("purchasesitemsDao")
public class PurchasesItemsDaoImpl extends AbstractDao<Integer, PurchasesItems> implements PurchasesItemsDao {

	public void save(PurchasesItems purchasesitems){
		 saveOrUpdate(purchasesitems);
	}

	public List<PurchasesItems> findAllPurchasesItems() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public PurchasesItems findPurchasesItemsByID(int id){
		return getByKey(id);
	}

	public List<PurchasesItems> searchPurchasesItems(PurchasesItems purchasesitems){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(PurchasesItems purchasesitems){
		 delete(purchasesitems);
	}
	public List getPurchasesItemsbyPurchaseID(int id){
		return sqlQuery("select id,purchases_id, catalogs_id, barcode, name, expiration, price, purchase_price,freequantity, quantity, discount, tax, subtotal from purchases_items where  purchases_id = "+id);
	}

	public List findPurchasesItemsByPeriod(Period pd){
		return sqlQuery("select purchases_id,barcode,name,price,purchase_price,quantity,discount,tax,subtotal from purchases_items where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	/*public List findStocksVerificationReport(Period pd){
		String 	sql = "select c.barcode as batchno,c.name, c.quantity as available_quantity, COUNT(inv.quantity) as sold_quantity, COUNT(pur.quantity) as purchased_quantity ";
				sql += "from catalogs as c left join invoices_items as inv on c.barcode=inv.barcode "; 
				sql += "left join purchases_items as pur on c.barcode=pur.barcode ";				
				sql += "where c.quantity>='1'";
				sql += "group by c.barcode";
		return sqlQuery(sql);
	}*/
	
	public List findStocksVerificationReport(Period pd){
		String 	sql = "select c.barcode as batchno,c.name, c.quantity as available_quantity, SUM(inv.quantity) as sold_quantity ";
				sql += "from catalogs as c left join invoices_items as inv on c.barcode=inv.barcode "; 
				sql += "where c.quantity>='1'";				
				sql += "group by c.barcode";
				//sql += " order by sold_quantity desc";
		return sqlQuery(sql);
	}
	
	public List findFastmovingStocksReport(Period pd){
		String 	sql = "select c.barcode as batchno,c.name, c.quantity as available_quantity, SUM(inv.quantity) as sold_quantity ";
				sql += "from catalogs as c left join invoices_items as inv on c.barcode=inv.barcode "; 
				sql += "where c.quantity>='1' and ";				
				sql += "inv.created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)";
				sql += "group by c.barcode";
				sql += " order by sold_quantity desc";
		return sqlQuery(sql);
	}
	
	public List findLowStocksReport(Period pd){
		String 	sql = "select c.barcode as batchno,c.name, c.quantity as available_quantity, SUM(inv.quantity) as sold_quantity ";
				sql += "from catalogs as c left join invoices_items as inv on c.barcode=inv.barcode "; 
				sql += "where c.quantity>='1' and c.quantity<='10'";
				sql += "group by c.barcode";
		return sqlQuery(sql);
	}
	
	
	public List searchPurchasesItemsByName(String str){
		return sqlQuery("select id from purchases_items where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from purchases_items ");
	}
}
