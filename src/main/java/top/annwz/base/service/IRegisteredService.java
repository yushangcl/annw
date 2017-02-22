package top.annwz.base.service;

import top.annwz.base.entity.BaUser;

/**
 * Created by Wuhuahui on 2016/12/8.
 */
public interface IRegisteredService {

	/**
	 * 功能说明  保存用户信息
	 * @param
	 * @author sunkuo
	 * */
	BaUser saveRegisterInfo(String username, String password, String mobile, String email) throws Exception;

	/**
	 * 更新，验证验证邮箱
	 * @return
	 * @throws Exception
	 */
	boolean updateStatus(String codeValue, String email) throws Exception;

}
