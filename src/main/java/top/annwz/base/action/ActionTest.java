package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import top.annwz.base.entity.Mail;
import top.annwz.base.uitl.PageData;

import java.util.Map;

/**
 * Created by Wuhuahui on 2016/12/22.
 */
@Controller
public class ActionTest extends BasicAction{

	//pass the parameters to front-end
	@RequestMapping("/show")
	public String showPerson(Map<String,Object> map){
		Mail mail = new Mail();
		map.put("p", mail);
		mail.setHost("20");
		mail.setName("jayjay");
		return "show";
	}

	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login")
	public ModelAndView toLogin() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", "登录"); //读取系统名称
		mv.setViewName("user/login");
		mv.addObject("name","Wuhuahui");
		return mv;
	}

	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/regist")
	public ModelAndView toregist() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", "注册"); //读取系统名称
		mv.setViewName("user/registration");
		mv.addObject("name","Wuhuahui");
		return mv;
	}


}
