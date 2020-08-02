package org.imzdong.study.performance.throwable;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

public class TryCatchDemo {

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
    }

    private static void tyrWithResource(){
        //语法糖，自动关闭资源
        try(InputStream io = new ByteInputStream()){

        }catch (IOException e){

        }
    }
}
