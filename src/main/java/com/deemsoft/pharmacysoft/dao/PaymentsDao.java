package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Payments;

public interface PaymentsDao {

	void save(Payments payments);

	List<Payments> findAllPayments();

	public Payments findPaymentsByID(int id);

	public List<Payments> searchPayments(Payments payments);

	public void deleteByID(Payments payments);
	public List findPaymentsByPeriod(Period pd);
	public List searchPaymentsByName(String str);
	public List getMaxID();
}
