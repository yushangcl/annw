package top.annwz.base.mapper;

import top.annwz.base.entity.BaUserSalt;

public interface BaUserSaltMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(BaUserSalt record);

    int insertSelective(BaUserSalt record);

    BaUserSalt selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(BaUserSalt record);

    int updateByPrimaryKey(BaUserSalt record);
}