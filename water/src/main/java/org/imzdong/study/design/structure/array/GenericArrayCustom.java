package org.imzdong.study.design.structure.array;

import java.util.Arrays;

/**
 * @description: 数组 查询、新增
 * @author: Winter
 * @time: 2020年4月28日, 0028 12:07
 */
public class GenericArrayCustom<T> {

    private T[] arrays;
    private int length;//默认实际长度
    private int capacity = 10;//默认容量

    public GenericArrayCustom(){
        arrays = (T[]) new Object [capacity];
    }

    public GenericArrayCustom(int capacity){
        if(capacity < 0){
            throw new ArithmeticException("数组容量不能小于0");
        }
        this.capacity = capacity;
        arrays = (T[]) new Object[capacity];
    }

    public int length(){
        return length;
    }

    /**
     * 尾部添加
     * @param value
     * @return
     */
    public void add(T value){
        if(length>=capacity){
            capacity = capacity+(capacity>>1);
            arrays = Arrays.copyOf(arrays,capacity);
            //扩容
        }
        arrays[length] = value;
        ++length;
    }

    /**
     * 添加 插入不是替换
     * @param value
     * @return
     */
    public void insert(int index, T value){
        if(index >= length || index<0){
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        if(capacity == length){
            throw new RuntimeException("数组已满");
        }
        for(int n=length;n>index;n--){
            arrays[n] = arrays[n-1];
        }
        arrays[index] = value;
        length++;
    }

    /**
     * 索引获取
     * @param index
     * @return
     */
    public T get(int index){
        if(index>=length){
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        return arrays[index];
    }

    public boolean delete(int index){
        if(index>=length || index<0){
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        for(int n=index;n<length;n++){
            arrays[n] = arrays[n+1];
        }
        length--;
        return true;
    }

    @Override
    public String toString() {
        return "ArrayCustom{" +
                "arrays=" + Arrays.toString(Arrays.copyOf(arrays,length)) +
                '}';
    }

}
