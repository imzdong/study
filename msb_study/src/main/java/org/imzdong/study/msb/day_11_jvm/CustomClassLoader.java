package org.imzdong.study.msb.day_11_jvm;

public class CustomClassLoader extends ClassLoader{

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return CustomClassLoader.class;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return null;
    }

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> aClass = customClassLoader.loadClass("java.lang.String");
            //System.out.println(T.num);

            System.out.println(new T());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class T{
        public static int num = 1;



        public int a = 10;

        static {
            System.out.println("static");
        }



        public T(){
            System.out.println("T");
            System.out.println(a);
            a=17;
        }

        {
            System.out.println("slslslsl");
            System.out.println(a);
        }
    }


}
