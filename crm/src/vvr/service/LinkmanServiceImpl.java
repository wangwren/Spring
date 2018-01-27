package vvr.service;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import vvr.dao.LinkmanDao;
import vvr.domain.Linkman;
import vvr.domain.PageBean;

@Transactional
public class LinkmanServiceImpl implements LinkmanService {

	private LinkmanDao linkmanDao;

	public void setLinkmanDao(LinkmanDao linkmanDao) {
		this.linkmanDao = linkmanDao;
	}

	@Override
	public PageBean<Linkman> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
		
		return linkmanDao.findByPage(pageCode, pageSize, criteria);
	}

	@Override
	public void save(Linkman linkman) {
		linkmanDao.save(linkman);
		
	}

	@Override
	public Linkman findById(Long lkm_id) {
		
		return linkmanDao.findById(lkm_id);
	}

	@Override
	public void delete(Linkman lk) {
		linkmanDao.delete(lk);
		
	}

	@Override
	public void update(Linkman linkman) {
		linkmanDao.update(linkman);
	}
	
}
