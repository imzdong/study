package org.imzdong.study.data.structure.array;

import java.util.Arrays;

/**
 * @description: 数组 查询、新增
 * @author: Winter
 * @time: 2020年4月28日, 0028 12:07
 */
public class ArrayCustom {

    private int[] arrays;
    private int length;//默认实际长度
    private int capacity = 10;//默认容量

    public ArrayCustom(){
        arrays = new int[capacity];
    }

    public ArrayCustom(int capacity){
        if(capacity < 0){
            throw new ArithmeticException("数组容量不能小于0");
        }
        this.capacity = capacity;
        arrays = new int[capacity];
    }

    public int length(){
        return length;
    }

    /**
     * 尾部添加
     * @param value
     * @return
     */
    public void add(int value){
        if(length>=capacity){
            //扩容
            return;
        }
        arrays[length] = value;
        ++length;
    }

    /**
     * 添加
     * @param value
     * @return
     */
    public void insert(int index, int value){
        if(length>=capacity){
            //扩容
            return;
        }
        arrays[length] = value;
        ++length;
    }

    /**
     * 索引获取
     * @param index
     * @return
     */
    public int get(int index){
        if(index>=length){
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        return arrays[index];
    }


    @Override
    public String toString() {
        return "ArrayCustom{" +
                "arrays=" + Arrays.toString(arrays) +
                '}';
    }
}
