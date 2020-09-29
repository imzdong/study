package org.imzdong.study.msb.desgin.strategy;

public class Main {

    public static void main(String[] args) {
        Sorter<Cat> catSorter = new Sorter<>();
        Cat[] cats = {new Cat(12,"hello"),new Cat(101,"z")};
        Strategy<Cat> catAgeStrategy = new CatAgeStrategy();
        catSorter.compareTwo(cats,catAgeStrategy);
        System.out.println(cats[0]+":"+cats[1]);
        Strategy<Cat> catNameStrategy = new CatNameStrategy();
        catSorter.compareTwo(cats,catNameStrategy);
        System.out.println(cats[0]+":"+cats[1]);
    }
}
