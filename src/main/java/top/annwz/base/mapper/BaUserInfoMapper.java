package top.annwz.base.mapper;

import top.annwz.base.Dao.IBasicDao;
import top.annwz.base.entity.BaUserInfo;

public interface BaUserInfoMapper extends IBasicDao<BaUserInfo> {
    int deleteByPrimaryKey(Integer userId);

    int insert(BaUserInfo record);

    int insertSelective(BaUserInfo record);

    BaUserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(BaUserInfo record);

    int updateByPrimaryKey(BaUserInfo record);
}