package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.model.Settings;

@Repository("settingsDao")
public class SettingsDaoImpl extends AbstractDao<Integer, Settings> implements SettingsDao {

	public void save(Settings settings){
		 saveOrUpdate(settings);
	}

	public List<Settings> findAllSettings() {
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public Settings findSettingsByID(int id){
		return getByKey(id);
	}

	public List<Settings> searchSettings(Settings settings){
		Criteria crit = createEntityCriteria();
		return crit.list();
	}
	public void deleteByID(Settings settings){
		 delete(settings);
	}

	public List findSettingsByPeriod(Period pd){
		return sqlQuery("select id from Settings where created BETWEEN CAST('"+pd.beginDate+"' AS DATE) AND CAST('"+pd.endDate+"' AS DATE)");
	}
	public List searchSettingsByName(String str){
		return sqlQuery("select id from settings where  name like '"+str+"%'");
	}
	public List getMaxID(){
		return sqlQuery("select MAX(id) as max_id from settings ");
	}
}
