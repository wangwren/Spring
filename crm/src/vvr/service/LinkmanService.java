package vvr.service;

import org.hibernate.criterion.DetachedCriteria;

import vvr.domain.Linkman;
import vvr.domain.PageBean;

/**
 * 联系人业务逻辑层
 * @author wwr
 *
 */
public interface LinkmanService {

	PageBean<Linkman> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);

	void save(Linkman linkman);

	Linkman findById(Long lkm_id);

	void delete(Linkman lk);

	void update(Linkman linkman);

}
