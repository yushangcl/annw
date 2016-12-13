package top.annwz.base.dubbo.service.imp;

import org.springframework.stereotype.Service;
import top.annwz.base.dao.IBasicDao;
import top.annwz.base.dubbo.service.IBaUserService;
import top.annwz.base.entity.BaUser;
import top.annwz.base.mapper.BaUserMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Wuhuahui on 2016/12/4.
 */
@Service
public class BaUserService extends BasicService<BaUser> implements IBaUserService {

	@Resource
	private BaUserMapper baUserMapper;

	@Override
	public IBasicDao<BaUser> getDao() {
		return baUserMapper;
	}

	@Override
	public BaUser getUser(Long userId) {
		return baUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int verifyPassword(String userName, String password) {
		HashMap map = new HashMap();
		map.put("user_name", userName);
		map.put("password", password);
		return baUserMapper.getCountByMap(map);
	}

	@Override
	public BaUser getByEmail(String email) {
		return baUserMapper.getByEmail(email);
	}

	@Override
	public int updateStatusByEmail(String email) {
		return baUserMapper.updateStatusByEmail(email);
	}
}
