package vvr.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import vvr.domain.Customer;
import vvr.domain.PageBean;

/**
 *通用的dao接口实现类，其他dao实现类直接继承这个类就可以
 * @author wwr
 *
 * @param <T>
 */
@SuppressWarnings("all")	//清除警告
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	
	//定义属性
	private Class clazz;
	
	//可以通过有参的构造方法来指定clazz
	/*
	 * 这种方式需要在子类中调用父类的构造方法，并传一个对象，但是这种方式很好理解
	 * public BaseDaoImpl(Class clazz) {
		this.clazz = clazz;
	}
	*/
	
	//另一种方式，提供无参的构造方法
	public BaseDaoImpl() {
		
		/*
		 * 通过spring的特点，在服务器启动时加载对象，创建子类的同时，父类同样也会被加载，执行父类默认的构造方法，通过这种方式拿到对象
		 * 比如加载CustomerDaoImpl时，继承的BaseDaoImpl也会被加载，执行BaseDaoImpl的默认构造方法
		 */
		
		//最终目的，得到CustomerDaoImpl extends BaseDaoImpl<Customer>中的Customer
		
		//此处的this表示子类，由于是加载子类菜创建的父类，所以this表示子类
		//c表示CustomerDaoImpl的Class对象
		//第一步
		Class c = this.getClass();
		
		//第二步,获取到BaseDaoImpl<Customer>
		Type type = c.getGenericSuperclass();	//该方法得到父类
		
		//目的，把type转换成子接口
		if(type instanceof ParameterizedType) {
			//ParameterizedType 该子接口中的方法可以哪出泛型
			ParameterizedType ptype = (ParameterizedType) type;
			
			//获取Customer,返回的是一个数组，因为泛型可以有多个，比如Map<k,v>
			Type[] types = ptype.getActualTypeArguments();
			this.clazz = (Class) types[0];
		}
		
	}
	
	
	
	/**
	 * 添加
	 */
	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public T findById(Long id) {
		
		//clazz.getSimpleName()得到该类的名称
		T t = (T) this.getHibernateTemplate().get(clazz, id);
		return t;
	}

	@Override
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().find("from " + clazz.getSimpleName());
	}
	
	
	@Override
	public PageBean<T> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
		
		PageBean<T> page = new PageBean<T>();
		
		page.setPageCode(pageCode);
		page.setPageSize(pageSize);
		
		//使用聚合函数，查询所以记录数 sql语句：select count(*) from ...
		criteria.setProjection(Projections.rowCount());
		List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list != null && list.size() != 0 ) {
			int totalCount = list.get(0).intValue();
			//总记录数
			page.setTotalCount(totalCount);
		}
		
		//查询分页数据，需要把聚合函数删除，设置为空就可以
		//此时sql语句：select * from ...
		criteria.setProjection(null);
		List<T> beanList = (List<T>) this.getHibernateTemplate().findByCriteria(criteria, (pageCode - 1) * pageSize, pageSize);
		
		//分页数据
		page.setBeanList(beanList);
		
		return page;
	}

}
