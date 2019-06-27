package top.annwz.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicExceptionHanler implements HandlerExceptionResolver {
    private transient Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ServiceException.class})
    public String exception(ServiceException e) {
        return "error";
    }

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        return new ModelAndView("error");
    }
}