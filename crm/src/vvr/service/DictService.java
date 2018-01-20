package vvr.service;

import java.util.List;

import vvr.domain.Dict;

public interface DictService {

	List<Dict> findByCode(String dict_type_code);

}
