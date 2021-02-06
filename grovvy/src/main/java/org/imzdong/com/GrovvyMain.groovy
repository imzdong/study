package org.imzdong.com

class GrovvyMain {

    //闭包
    def static userBb(Closure closure){
        closure();
    }
    //闭包参数
    def static userBbArgs(Closure closure, String arg){
        closure(arg);
    }
    //执行方法
    static void main(String[] args) {
        //数据类型
        def i = 1;
        println i;
        def str = "Winter";
        println str;
        def list = ["zhou", "Winter"];
        list << "age";
        println list;
        def map = ["name":"Winter","age":"18"];
        println map.get("age");
        map.sex = "man";
        println map;
        //闭包，就是一段代码块
        def bb = {
            println "I'm 闭包！";
        }
        userBb(bb);
        //闭包参数
        def bbArgs = {
            v ->
            println "I'm 闭包！Hello ${v}";
        }
        userBbArgs(bbArgs, "Dong");
    }

}
