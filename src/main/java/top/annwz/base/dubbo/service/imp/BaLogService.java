package top.annwz.base.dubbo.service.imp;

import top.annwz.base.dao.IBasicDao;
import top.annwz.base.dubbo.service.IBaLogService;
import top.annwz.base.entity.BaLog;

/**
 * Created by Wuhuahui on 2016/12/19.
 */
public class BaLogService extends BasicService<BaLog> implements IBaLogService {
	@Override
	public IBasicDao<BaLog> getDao() {
		return null;
	}
}
