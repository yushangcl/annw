package top.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import top.annwz.base.dao.RedisHelper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjp on 16/4/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
//@Category(cn.com.iscs.common.test.IscsIgnoreTest.class)
public class RedisHelperTest {
	@Resource
	RedisHelper redisHelper;

	@Test
	public void insertsTest() throws Exception {

		StopWatch watch = new StopWatch();
		for (int i = 0; i < 1000; i++) {
			watch.start();
			redisHelper.set(String.valueOf(i), "kkk");
			watch.stop();
			System.out.println(i + " : " + watch.getLastTaskTimeMillis());
		}
	}

	@Test
	public void delTest() throws Exception {
		StopWatch watch = new StopWatch();
		for (int i = 0; i < 10000; i++) {
			watch.start();
			redisHelper.del(String.valueOf(i));
			watch.stop();
			System.out.println(i + " : " + watch.getLastTaskTimeMillis());
		}
		for (int i = 0; i < 10000; i++) {
			watch.start();
			redisHelper.jedisCluster.del(String.valueOf(i));
			watch.stop();
			System.out.println(i + " : " + watch.getLastTaskTimeMillis());
		}
	}

	@Test
	public void getTest() throws Exception {
		redisHelper.hgetMap("kkk");
	}
	@Test
	public void set() throws Exception {
		Map<String, String> map = new HashMap<>();
		redisHelper.set("kkk",map);
	}
	@Test
	public void getCreatorTest() throws Exception {
		Long participantUkid=10001628L;
		String redisKey = "Creator";
		Long creatorId = redisHelper.hget(redisKey, participantUkid.toString(), Long.class);
		System.out.println("creatorId..........."+creatorId);
	}


}