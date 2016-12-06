package top.annwz.base.dubbo.service.imp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.annwz.base.Dao.IBasicDao;
import top.annwz.base.dubbo.service.IBasicService;
import top.annwz.base.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class BasicService<T>  {
    protected transient final Logger logger = LogManager.getLogger(getClass());

    public abstract IBasicDao<T> getDao();

    @Transactional(propagation = Propagation.REQUIRED)
    public int delete(Long ukid) {
        try {
            return getDao().deleteByPrimaryKey(ukid);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(T record) {
        try {
            return getDao().insert(record);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int update(T record) {
        try {
            return getDao().updateByPrimaryKey(record);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T get(Long ukid) {
        return getDao().selectByPrimaryKey(ukid);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<T> getsByMap(HashMap map) {
        return getDao().getsByMap(map);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int getCountByMap(HashMap map) {
        return getDao().getCountByMap(map);
    }
}