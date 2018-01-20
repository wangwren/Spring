package vvr.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import vvr.domain.Dict;

public class DictDaoImpl extends HibernateDaoSupport implements DictDao {

	@Override
	public List<Dict> findByCode(String dict_type_code) {
		
		List<Dict> list = (List<Dict>) this.getHibernateTemplate().find("from Dict where dict_type_code = ?", dict_type_code);
		return list;
	}

}
