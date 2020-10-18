package org.imzdong.study.stone.throwable;


import java.io.*;

public class TryCatchDemo {

    public static void main(String[] args) {
        tryCatch();
    }

    private static void tryCatch(){
        // 1、try...catch
        try{

        }catch (Exception e){

        }
        // 2、try...finally
        try {

        }finally {

        }
        // 3、try...catch...catch...finally
        try {

        }catch (RuntimeException r){

        }catch (Exception e){

        }finally {

        }
        // 4、try...catch...catch...finally
        try {

        }catch (ArithmeticException | NullPointerException e){

        }finally {

        }
    }

    private static void tyrWithResource(){
        //语法糖，自动关闭资源
        try(InputStream io = new ByteArrayInputStream("Winter".getBytes())){

        }catch (IOException e){

        }
    }
}
