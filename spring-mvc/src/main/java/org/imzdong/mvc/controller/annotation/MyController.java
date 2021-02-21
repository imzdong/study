package org.imzdong.mvc.controller.annotation;

import org.imzdong.mvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author zhoud
 * @since  2021/2/18 8:40
 * @see org.springframework.web.servlet.mvc.Controller
 */
@Controller
public class MyController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/first")
    public String first(Map<String, String> params, String userName){
        params.put("pp","吆西");
        return "hello";
    }


    @RequestMapping("/second/haha")
    public String second(Map<String, String> params, String userName){
        params.put("pp","吆西");
        return "hello";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(){
        helloService.hello();
        return "ok";
    }
}
