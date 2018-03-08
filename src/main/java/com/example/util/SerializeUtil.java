package com.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	/**  
     * 序列化  
     */    
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream(); //序列化
        try {  
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);    
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally{
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());  
            }  
        }
        return null;
    }    
    
    /**  
     * 反序列化  
     */    
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally{
            try {
                bais.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }    
}
