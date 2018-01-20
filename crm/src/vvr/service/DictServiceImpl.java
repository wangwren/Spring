package vvr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import vvr.dao.DictDao;
import vvr.domain.Dict;

/**
 * ¿Í»§×Öµä
 * @author wwr
 *
 */
@Transactional
public class DictServiceImpl implements DictService {

	private DictDao dictDao;

	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}

	@Override
	public List<Dict> findByCode(String dict_type_code) {
		
		return dictDao.findByCode(dict_type_code);
	}
	
}
