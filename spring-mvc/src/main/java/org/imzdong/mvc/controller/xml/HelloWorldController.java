package org.imzdong.mvc.controller.xml;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoud
 * @date 2021/2/17:18:21
 * @desc
 */
public class HelloWorldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        System.out.println("request success");
        ModelAndView view = new ModelAndView();
        view.setViewName("hello");
        return view;
    }
}
