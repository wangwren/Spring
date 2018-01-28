package vvr.service;

import org.hibernate.criterion.DetachedCriteria;

import vvr.domain.PageBean;
import vvr.domain.Visit;

public interface VisitService {

	void save(Visit visit);

	PageBean<Visit> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);

}
