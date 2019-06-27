package top.annwz.base.dubbo.service.imp;

import org.springframework.stereotype.Service;
import top.annwz.base.dao.IBasicDao;
import top.annwz.base.dubbo.service.IBaUserInfoService;
import top.annwz.base.entity.BaUserInfo;
import top.annwz.base.mapper.BaUserInfoMapper;

import javax.annotation.Resource;

/**
 * Created by Wuhuahui on 2016/12/5.
 */
@Service
public class BaUserInfoService extends BasicService<BaUserInfo>implements IBaUserInfoService {

	@Resource
	private BaUserInfoMapper baUserInfoMapper;

	@Override
	public IBasicDao<BaUserInfo> getDao() {
		return baUserInfoMapper;
	}
}
