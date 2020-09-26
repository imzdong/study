package org.imzdong.study.msb.day_06_proxy;

import java.io.*;

public class SerializeUtil<T> {

    public static byte[] serialize(Object obj){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(obj);
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T>T deserialize(byte[] bytes){
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            ObjectInputStream ooi = new ObjectInputStream(stream);
            return (T)ooi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
