package top.annwz.base.mapper;

import top.annwz.base.dao.IBasicDao;
import top.annwz.base.entity.BaUser;

public interface BaUserMapper extends IBasicDao<BaUser> {
    int deleteByPrimaryKey(Integer userId);

    int insert(BaUser record);

    int insertSelective(BaUser record);

    BaUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(BaUser record);

    int updateByPrimaryKey(BaUser record);

    BaUser getByEmail(String email);

    int updateStatusByEmail(String email);
}