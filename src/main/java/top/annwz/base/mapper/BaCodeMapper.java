package top.annwz.base.mapper;

import top.annwz.base.dao.IBasicDao;
import top.annwz.base.entity.BaCode;

public interface BaCodeMapper extends IBasicDao<BaCode> {
    int deleteByPrimaryKey(Integer codeId);

    int insert(BaCode record);

    int insertSelective(BaCode record);

    BaCode selectByPrimaryKey(Integer codeId);

    int updateByPrimaryKeySelective(BaCode record);

    int updateByPrimaryKey(BaCode record);

    BaCode getByCodeValue(String codeValue);
}