package vvr.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import vvr.domain.PageBean;

/**
 * 编写通用的dao接口，定义常用的方法
 * @author wwr
 *自定义泛型，用于保存对象，对于方法中也可以传入Object，但是泛型能好一点
 *
 *其他dao接口直接继承这个接口
 */
public interface BaseDao<T> {

	/**
	 * 添加
	 */
	public void save(T t);
	
	/**
	 * 删除
	 * @param t  t代表传入的对象
	 */
	public void delete(T t);
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * 查询指定编号的数据
	 * @param id
	 * @return
	 */
	public T findById(Long id);
	
	public List<T> findAll();
	
	/**
	 * 分页，返回的是通用的分页对象
	 * @return
	 */
	public PageBean<T> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);
	
}
