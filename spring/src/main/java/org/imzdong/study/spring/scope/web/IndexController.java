package org.imzdong.study.spring.scope.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: Winter
 * @time: 2020/4/6
 */
@Controller
public class IndexController {

    @GetMapping
    public String index(){
        return "index";
    }
}
