package com.deemsoft.pharmacysoft.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.deemsoft.pharmacysoft.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	public void save(User user) {
        saveOrUpdate(user);
    }
	
	public User findById(int id) {
		return getByKey(id);
	}

	public User findBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		return (User) crit.uniqueResult();
	}
	
	public List<User> findAllUsers() {
		Criteria crit = createEntityCriteria();
		
		return crit.list();
	}

	
}
