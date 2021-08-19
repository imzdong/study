package org.imzdong.study.stone.base;

/**
 * @author admin
 * @date 2021/8/19 下午1:24
 */
public class ClassLoaderPath {

    public static void main(String[] args) {
        System.out.println("---------boot------------");
        String bootClassPath = System.getProperty("sun.boot.class.path");
        System.out.println(bootClassPath.replaceAll(":", System.lineSeparator()));
        System.out.println("---------ext------------");
        String extPath = System.getProperty("java.ext.dirs");
        System.out.println(extPath.replaceAll(":", System.lineSeparator()));
        System.out.println("---------app------------");
        String appPath = System.getProperty("java.class.path");
        System.out.println(appPath.replaceAll(":", System.lineSeparator()));
    }

}
