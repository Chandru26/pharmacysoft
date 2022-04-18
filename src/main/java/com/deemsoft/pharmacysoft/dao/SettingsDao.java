package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Settings;

public interface SettingsDao {

	void save(Settings settings);

	List<Settings> findAllSettings();

	public Settings findSettingsByID(int id);

	public List<Settings> searchSettings(Settings settings);

	public void deleteByID(Settings settings);
	public List findSettingsByPeriod(Period pd);
	public List searchSettingsByName(String str);
	public List getMaxID();
}
