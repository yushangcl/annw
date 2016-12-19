package top.annwz.base.mapper;

import top.annwz.base.dao.IBasicDao;
import top.annwz.base.entity.BaLog;

public interface BaLogMapper extends IBasicDao<BaLog> {
    int deleteByPrimaryKey(Integer logId);

    int insert(BaLog record);

    int insertSelective(BaLog record);

    BaLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(BaLog record);

    int updateByPrimaryKey(BaLog record);
}