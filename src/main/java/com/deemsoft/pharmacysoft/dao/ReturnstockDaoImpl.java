package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Returnstock;

@Repository("returnstockDao")
public class ReturnstockDaoImpl extends AbstractDao<Integer, Returnstock> implements ReturnstockDao {

	public void save(Returnstock returnstock){
		 saveOrUpdate(returnstock);
	}

	public List<Returnstock> findAllReturnstock() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Returnstock findReturnstockByID(int id){
		return getByKey(id);
	}

	public List<Returnstock> searchReturnstock(Returnstock returnstock){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Returnstock returnstock){
		 delete(returnstock);
	}
	
	public List ListReturnstockByStatusAndUser(int usr){
		return sqlQuery("select id,title from returnstock where  status=0 and created_by="+usr);
	}
	public List findReturnstockByPeriod(Period pd){
		return sqlQuery("select * from returnstock where return_date BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	
	public List findReturnByPeriod(Period pd){
		return sqlQuery("select  a.id,a.return_date,b.return_id,b.barcode,b.name, SUM(b.quantity) as quantity from returnstock as a,returnstock_items as b  where a.id = b.return_id and a.return_date BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE) group by b.barcode");
	}
	
	public List searchReturnstockByName(String str){
		return sqlQuery("select id from returnstock where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from returnstock ");
	}
}
