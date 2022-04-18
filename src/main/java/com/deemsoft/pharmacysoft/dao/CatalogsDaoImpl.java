package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Catalogs;

@Repository("catalogsDao")
public class CatalogsDaoImpl extends AbstractDao<Integer, Catalogs> implements CatalogsDao {

	public void save(Catalogs catalogs){
		 saveOrUpdate(catalogs);
	}

	public List<Catalogs> findAllCatalogs() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Catalogs findCatalogsByID(int id){
		return getByKey(id);
	}

	public List<Catalogs> searchCatalogs(Catalogs catalogs){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Catalogs catalogs){
		 delete(catalogs);
	}

	public List findCatalogsByPeriod(Period pd){
		return sqlQuery("select barcode,name,description,expiration,quantity,msrp,purchase_price from catalogs where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	
	public List findCatalogsBarByPeriod(Period pd){
		return sqlQuery("select * from catalogs where id >= "+pd.beginDate+" AND id <= "+pd.endDate+"");
		}
	public List searchCatalogsByName(String str){
		return sqlQuery("select id,name,barcode,expiration,purchase_price,gst,hsn_no,supplier_name,msrp,max_discount,tax,quantity ,description from catalogs where  name like '%"+str+"%'");
	}
	public List searchCatalogsByBarcode(String str){
		return sqlQuery("select id,name,barcode,expiration,purchase_price,gst,hsn_no,supplier_name,msrp,max_discount,tax,quantity,description from catalogs where  barcode = '"+str+"'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from Catalogs ");
	}
}
