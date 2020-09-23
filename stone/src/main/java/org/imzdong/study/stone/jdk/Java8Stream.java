package org.imzdong.study.stone.jdk;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @description: Stream实例
 * @author: Winter
 * @time: 2020年2月27日, 0027 13:02
 */
public class Java8Stream {

    public static void main(String[] args) {
        //1、创建流
        //buildStream();
        //distinctByKeyTest();
        String a = "Winter"+1+23;
        System.out.println(a);
        //Stream.of(list.stream()).forEach(System.out::println);
    }

    private static void distinctByKeyTest() {
        User winter01 = new User(1, "Winter_01");
        User winter02 = new User(1, "Winter_02");
        User winter03 = new User(3, "Winter_03");
        List<User> list = new ArrayList<>();
        list.add(winter01);
        list.add(winter02);
        list.add(winter03);
        list.stream().filter(distinctByKey(User::getId)).forEach(System.out::println);
    }

    private static void buildStream() {
        Stream.of("1").forEach(System.out::println);
        Stream.of("1","2","3").forEach(System.out::println);
        Arrays.stream(new int []{1,2,3}).forEach(System.out::println);//数组
        //集合
        Collection<String> collection = Arrays.asList("a", "b", "c");
        collection.stream().forEach(System.out::println);
        //builder创建流
        Stream.builder().add(1).add("Winter").add(1.2).build().forEach(System.out::println);
        //创建无限流（不同的实体或者相同的实体）
        Stream.generate(Random::new).forEach(System.out::println);
        User user = new User(666,"Winter");
        Stream.iterate(user,userF->{
            userF.setName("Winter Strong");
            return userF;
        }).forEach(System.out::println);
    }

    /**
     * 根据属性去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    static class User{

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
