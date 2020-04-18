package com.base.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.IBaseDao;


@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements IBaseDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存一个对象
	 * 
	 * @param bean
	 *            要保存的JavaBean 对象
	 */
	@Override
	public Serializable save(T o) {
		return this.getCurrentSession().save(o); 
	}

	/**
	 * 删除一个对象
	 * 
	 * @param JavaBean
	 *            对象
	 */
	@Override
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	/**
	 * 修改一个对象
	 * 
	 * @param JavaBean
	 *            对象
	 */
	@Override
	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	/**
	 * 修改一个对象
	 * 
	 * @param 要保存的JavaBean
	 *            对象
	 */
	@Override
	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	/**
	 * 查询对象集合
	 * 
	 * @param Hql格式查询语句
	 * @return 对象集合
	 */
	@Override
	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	/**
	 * 查询对象集合
	 * 
	 * @param Hql格式语句
	 * @param map参数
	 * @return 对象集合
	 */
	@Override
	public List<T> find(String hql, Map<String, Object> map) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				if (obj instanceof Collection<?>) {
					q.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(string, (Object[]) obj);
				} else {
					q.setParameter(string, obj);
				}
			}
		}
		return q.list();
	}

	/**
	 * 分页查询对象集合
	 * 
	 * @param Hql格式语句
	 * @param map参数
	 * @param 页码
	 * @param 每页记录数
	 * @return 对象集合
	 ***/
	@Override
	public List<T> find(String hql, Map<String, Object> map, Integer page,
			Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				if (obj instanceof Collection<?>) {
					q.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(string, (Object[]) obj);
				} else {
					q.setParameter(string, obj);
				}
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/**
	 * 查询指定对象
	 * 
	 * @param 对象
	 * @param 主键值
	 * @return 获取对象
	 */
	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	/**
	 * 查询指定对象
	 * 
	 * @param hql查询语句
	 * @param map参数
	 * @return 相应对象
	 */
	@Override
	public T get(String hql, Map<String, Object> map) {
		List<T> l = this.find(hql, map);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询记录数
	 * 
	 * @param hql查询语句
	 * @param map参数
	 * @return 记录数
	 */
	@Override
	public Long count(String hql, Map<String, Object> map) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				if (obj instanceof Collection<?>) {
					q.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(string, (Object[]) obj);
				} else {
					q.setParameter(string, obj);
				}
			}
		}
		return (Long) q.uniqueResult();
	}

	/**
	 * 执行更新语句
	 * 
	 * @param hql查询语句
	 * @param map参数
	 * @return 响应数目
	 */
	@Override
	public Integer executeHql(String hql, Map<String, Object> map) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				if (obj instanceof Collection<?>) {
					q.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(string, (Object[]) obj);
				} else {
					q.setParameter(string, obj);
				}
			}
		}
		return q.executeUpdate();
	}

	/**
	 * 执行查询语句
	 * 
	 * @param hql查询语句
	 * @param map
	 *            参数
	 * @return 响应数目
	 */
	@Override
	public List<Object> queryHql(String hql, Map<String, Object> map) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				if (obj instanceof Collection<?>) {
					q.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(string, (Object[]) obj);
				} else {
					q.setParameter(string, obj);
				}
			}
		} 
		return q.list();
	}

	@Override
	public List<Object> queryHqlMap(String hql, Map<String, Object> map) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (map != null) {
			Set<String> keySet = map.keySet();
			for (String string : keySet) {
				Object obj = map.get(string);
				if (obj instanceof Collection<?>) {
					q.setParameterList(string, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					q.setParameterList(string, (Object[]) obj);
				} else {
					q.setParameter(string, obj);
				}
			}
		}
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
}
