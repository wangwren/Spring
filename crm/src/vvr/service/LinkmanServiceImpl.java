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
	
}
