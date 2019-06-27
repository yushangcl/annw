package top.annwz.base.dubbo.service;

import top.annwz.base.entity.BaUser;

/**
 * Created by Wuhuahui on 2016/12/4.
 */
public interface IBaUserService extends IBasicService<BaUser>{
	public BaUser getUser(Long userId);

	public int verifyPassword(String userName, String password);

	public BaUser getByEmail(String email);

	public int updateStatusByEmail(String email);
}
