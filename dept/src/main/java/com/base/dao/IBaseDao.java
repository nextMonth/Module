package com.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface IBaseDao<T> {

	/**
	 * 保存一个对象
	 * 
	 * @param javabean对象
	 * @return
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param javabean对象
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param javabean对象
	 */
	public void update(T o);

	/**
	 * 保存或更新对象
	 * 
	 * @param javabean对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 查询对象集合
	 * 
	 * @param hql查询语句
	 * @return 对象集合
	 */
	public List<T> find(String hql);

	/**
	 * 查询对象集合
	 * 
	 * @param hql查询语句
	 * @param 参数数组
	 * @return
	 */
	public List<T> find(String hql, Map<String, Object> map);

	/**
	 * 查询对象集合(带分页)
	 * 
	 * @param hql查询语句
	 * @param param
	 *            参数数组
	 * @param page
	 *            查询第几页
	 * @param rows
	 *            每页显示几条记录
	 * @return
	 */
	public List<T> find(String hql, Map<String, Object> map, Integer page,
			Integer rows);

	/**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @return Object 对象
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * 获得一个对象
	 * 
	 * @param hql
	 *            查询语句
	 * @param param
	 *            参数数组
	 * @return Object 对象
	 */
	public T get(String hql, Map<String, Object> map);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 *            查询语句
	 * @param map
	 *            参数支持空值
	 * @return 合计值
	 */
	public Long count(String hql, Map<String, Object> map);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql语句
	 * @param map
	 *            参数 支持空值
	 * @return 响应数目
	 */
	public Integer executeHql(String hql, Map<String, Object> map);

	/**
	 * 查询字段列表,Map形式
	 * 
	 * @param hql
	 *            hql语句
	 * @param map
	 *            参数支持空值
	 * @return
	 */
	public List<Object> queryHqlMap(String hql, Map<String, Object> map);
	
	/**
	 * 查询字段列表
	 * 
	 * @param hql
	 *            hql语句
	 * @param map
	 *            参数支持空值
	 * @return
	 */
	public List<Object> queryHql(String hql, Map<String, Object> map);
	 
	
 
}
