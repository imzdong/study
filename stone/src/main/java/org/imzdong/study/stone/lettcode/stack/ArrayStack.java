package org.imzdong.study.stone.lettcode.stack;

/**
 * @author admin
 * @date 2021/5/10 6:38 下午
 */
public class ArrayStack {

    private int[] values = new int[10];
    private int index;

    public int pop(){
        if(index < 0){
            return -1;
        }
        return values[index--];
    }

    public boolean push(int val){
        if(index == 10){
            return false;
        }
        values[index++] = val;
        return true;
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack();
        int num = 0;
        while (true){
            boolean push = arrayStack.push(num++);
            if (!push){
                System.out.println(num);
                break;
            }
        }

        while (true){
            int push = arrayStack.pop();
            if (push == -1){
                break;
            }
            System.out.println(push);
        }
    }
}
