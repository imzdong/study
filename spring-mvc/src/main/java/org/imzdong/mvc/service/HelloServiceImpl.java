package org.imzdong.mvc.service;

import org.imzdong.mvc.dao.DaoService;

/**
 * @author zhoud
 * @see
 * @since 2021/2/21 9:15
 */
public class HelloServiceImpl implements HelloService{


    private DaoService daoService;

    @Override
    public void hello() {
        System.out.println("service hello");
        try {
            daoService.update();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }
}
