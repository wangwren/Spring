package vvr.dao;

import java.util.List;

import vvr.domain.Dict;

public interface DictDao {

	List<Dict> findByCode(String dict_type_code);

}
