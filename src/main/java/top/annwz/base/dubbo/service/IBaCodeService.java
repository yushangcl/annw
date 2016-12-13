package top.annwz.base.dubbo.service;

import top.annwz.base.entity.BaCode;

/**
 * Created by Wuhuahui on 2016/12/13.
 */
public interface IBaCodeService extends IBasicService<BaCode>{
	public BaCode getByCodeValue(String codeValue);

	public int updateCodeStatus(String codeValue);


}
