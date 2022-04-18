package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Shipments;

public interface ShipmentsDao {

	void save(Shipments shipments);

	List<Shipments> findAllShipments();

	public Shipments findShipmentsByID(int id);

	public List<Shipments> searchShipments(Shipments shipments);

	public void deleteByID(Shipments shipments);
	public List findShipmentsByPeriod(Period pd);
	public List searchShipmentsByName(String str);
	public List getMaxID();
}
