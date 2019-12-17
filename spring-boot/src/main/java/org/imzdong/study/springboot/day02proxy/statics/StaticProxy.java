package org.imzdong.study.springboot.day02proxy.statics;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月27日, 0027 15:02
 */
public class StaticProxy implements ProxyInterFace{

    private ProxyInterFace proxyInterFace;

    public StaticProxy(){
        this.proxyInterFace = new RealPeople();
    }



    @Override
    public void buy() {
        System.out.println("平台代购");
        proxyInterFace.buy();
    }
}
