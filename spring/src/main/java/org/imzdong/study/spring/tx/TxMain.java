package org.imzdong.study.spring.tx;

import org.imzdong.study.spring.tx.service.TxService;
import org.imzdong.study.spring.util.SelfSpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxMain {

    public static void main(String[] args) throws Exception{
        SelfSpringUtil.saveGeneratedCGlibProxyFiles(System.getProperty("user.dir")+"/proxy");
        String path = "tx-content.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(path);
        TxService txService = context.getBean(TxService.class);

        int update = txService.updateAgeService(1, 20);
        System.out.println(update);
    }
}
