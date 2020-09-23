package org.imzdong.study.performance.thread;

/**
 * @description:
 * @author: Winter
 * @time: 2020年6月15日, 0015 16:22
 */
public class TryDemo {

    public static void main(String[] args) {
        try{
            System.out.println("all try!");
            try {
                System.out.println("first try!");
                int num =  1/1;
            }finally {
                System.out.println("first finally!");
            }

            try {
                System.out.println("second try!");
            }finally {
                System.out.println("second finally!");
            }
        }finally {
            System.out.println("all finally!");
        }
    }
}
