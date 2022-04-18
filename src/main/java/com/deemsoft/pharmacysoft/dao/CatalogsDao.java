package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Catalogs;

public interface CatalogsDao {

	void save(Catalogs catalogs);

	List<Catalogs> findAllCatalogs();

	public Catalogs findCatalogsByID(int id);

	public List<Catalogs> searchCatalogs(Catalogs catalogs);

	public void deleteByID(Catalogs catalogs);
	public List findCatalogsByPeriod(Period pd);
	public List findCatalogsBarByPeriod(Period pd);
	public List searchCatalogsByName(String str);
	public List searchCatalogsByBarcode(String str);
	public List getMaxID();
}
