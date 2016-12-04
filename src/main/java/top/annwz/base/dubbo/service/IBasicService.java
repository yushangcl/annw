package top.annwz.base.dubbo.service;

import java.util.HashMap;
import java.util.List;

/**
 * 持久层基本的接口定义 所有的DAO接口都继承该接口
 * 默认是定义为public,需要根据情况自定义接口类.
 */
public interface IBasicService<T> {
	int delete(Long recordUkid);

	int insert(T record);

	int update(T record);

	T get(Long recordUkid);

	List<T> getsByMap(HashMap paramMap);

	int getCountByMap(HashMap paramMap);
}