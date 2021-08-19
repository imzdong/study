package org.imzdong.study.stone.base;

import java.io.*;

/**
 * @author admin
 * @since 2021/8/19 下午2:13
 */
public class CustomClassLoader extends ClassLoader{


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // /Users/admin/IdeaWorkSpace/study/stone/target/classes/org/imzdong/study/stone/base/
        /**String path = this.getClass().getResource("").getPath();
        System.out.println(path);*/
        // /Users/admin/IdeaWorkSpace/study/stone/target/classes/
        File f = new File("/Users/admin/soft/test/", name.replace(".", "/").concat(".class"));
        try {
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;

            while ((b=fis.read()) !=0) {
                baos.write(b);
            }

            byte[] bytes = baos.toByteArray();
            baos.close();
            fis.close();//可以写的更加严谨

            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name); //throws ClassNotFoundException

        /**
        String rootPath = this.getClass().getResource("/").getPath();
        rootPath = "/Users/admin/soft/test/";
        String path = rootPath + name.replace(".", "/") + ".class";
        System.out.println("加载类路径：" + path);
        try {
            FileInputStream file = new FileInputStream(new File(path));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int b = 0;
            while ((b=file.read())!=0){
                out.write(b);
            }

            byte[] bytes = out.toByteArray();

            out.close();
            file.close();//可以写的更加严谨

            return defineClass(name, bytes, 0, bytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException(name);
         */
    }


    public static void main(String[] args) throws Exception{

        System.out.println("---------app------------");
        String appPath = System.getProperty("java.class.path");
        System.out.println(appPath.replaceAll(":", System.lineSeparator()));

        CustomClassLoader customClassLoader = new CustomClassLoader();
        //org/imzdong/study/stone/base/People.java
        String name = "Winter";
        Class<?> loadClass = customClassLoader.loadClass(name);

        Object pp =  loadClass.newInstance();

        System.out.println(pp);

        System.out.println(pp.getClass().getClassLoader());


    }

}
