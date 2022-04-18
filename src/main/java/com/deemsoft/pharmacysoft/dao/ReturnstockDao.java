package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Returnstock;

public interface ReturnstockDao {

	void save(Returnstock returnstock);

	List<Returnstock> findAllReturnstock();

	public Returnstock findReturnstockByID(int id);

	public List<Returnstock> searchReturnstock(Returnstock returnstock);

	public void deleteByID(Returnstock returnstock);
	
	public List ListReturnstockByStatusAndUser(int usr);
	
	public List findReturnstockByPeriod(Period pd);
	
	public List findReturnByPeriod(Period pd);
	
	public List searchReturnstockByName(String str);
	
	public List getMaxID();
}
