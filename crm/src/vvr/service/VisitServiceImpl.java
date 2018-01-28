package vvr.service;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import vvr.dao.VisitDao;
import vvr.domain.PageBean;
import vvr.domain.Visit;

@Transactional
public class VisitServiceImpl implements VisitService {

	private VisitDao visitDao;

	public void setVisitDao(VisitDao visitDao) {
		this.visitDao = visitDao;
	}

	@Override
	public void save(Visit visit) {

		visitDao.save(visit);
	}

	@Override
	public PageBean<Visit> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
		return visitDao.findByPage(pageCode, pageSize, criteria);
	}
	
}
