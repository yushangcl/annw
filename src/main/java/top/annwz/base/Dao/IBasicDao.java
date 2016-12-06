package top.annwz.base.Dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 持久层基本的接口定义 所有的DAO接口都必须继承该接口
 */
public interface IBasicDao<T> {
    int deleteByPrimaryKey(Long orderUkid);

    int insert(T record);

    T selectByPrimaryKey(Long ukid);

    int updateByPrimaryKey(T record);

    List<T> getsByMap(@Param("map") HashMap params);

    int getCountByMap(@Param("map") HashMap params);
}