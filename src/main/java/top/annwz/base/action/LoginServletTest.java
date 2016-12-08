package top.annwz.base.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Wuhuahui on 2016/12/8.
 */
@Controller
@ResponseBody
@RequestMapping("/test")
public class LoginServletTest extends HttpServlet {
	@RequestMapping("/test")
	public void service (HttpServletRequest request, HttpServletResponse response) throws IOException{

		request.setAttribute("测试", "1234");
		PrintWriter out = response.getWriter();
		out.print("ok");
		out.close();
	}
}
