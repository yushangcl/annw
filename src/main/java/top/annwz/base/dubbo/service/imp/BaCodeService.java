package top.annwz.base.dubbo.service.imp;

import org.springframework.stereotype.Service;
import top.annwz.base.dao.IBasicDao;
import top.annwz.base.dubbo.service.IBaCodeService;
import top.annwz.base.entity.BaCode;
import top.annwz.base.mapper.BaCodeMapper;

import javax.annotation.Resource;

/**
 * Created by Wuhuahui on 2016/12/13.
 */
@Service
public class BaCodeService extends BasicService<BaCode> implements IBaCodeService {
	@Resource
	private BaCodeMapper baCodeMapper;
	@Override
	public IBasicDao<BaCode> getDao() {
		return baCodeMapper;
	}

	@Override
	public BaCode getByCodeValue(String codeValue) {
		return baCodeMapper.getByCodeValue(codeValue);
	}

	@Override
	public int updateByPrimaryKeySelective(BaCode baCode) {
		return baCodeMapper.updateByPrimaryKeySelective(baCode);
	}
}
