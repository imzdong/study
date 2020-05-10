package org.imzdong.study.design;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.imzdong.study.design.singleton.ThreadSafetyDoubleDetectionLazySingle;
import org.imzdong.study.design.singleton.ThreadSafetyHungrySingle;
import org.imzdong.study.design.singleton.ThreadSafetyLazyEnumsSingle;
import org.imzdong.study.design.singleton.ThreadSafetyLazyInnerClassSingle;
import org.imzdong.study.design.singleton.ThreadSafetyLazySingle;
import org.junit.Test;

/**
 * @description: 单元测试
 * @author: Winter
 * @time: 2019/12/11
 */
public class SingleTest {

    @Test
    public void testHungrySingle() {
        FutureTask<Object> futureTask = new FutureTask<>(ThreadSafetyHungrySingle::getInstance );
        FutureTask<Object> futureTaskTwo = new FutureTask<>(ThreadSafetyHungrySingle::getInstance );    
        new Thread(futureTask).start();
        new Thread(futureTaskTwo).start();
        try {
            assertEquals(futureTask.get(),futureTaskTwo.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSafetyLazySingle() {
        FutureTask<Object> futureTask = new FutureTask<>(ThreadSafetyLazySingle::getInstance );
        FutureTask<Object> futureTaskTwo = new FutureTask<>(ThreadSafetyLazySingle::getInstance );    
        new Thread(futureTask).start();
        new Thread(futureTaskTwo).start();
        try {
            assertEquals( futureTask.get(),futureTaskTwo.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testInnerClassLazySignle() {
        FutureTask<Object> futureTask = new FutureTask<>(ThreadSafetyLazyInnerClassSingle::getInstance );
        FutureTask<Object> futureTaskTwo = new FutureTask<>(ThreadSafetyLazyInnerClassSingle::getInstance );    
        new Thread(futureTask).start();
        new Thread(futureTaskTwo).start();
        try {
            assertEquals( futureTask.get(),futureTaskTwo.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testEunmLazySingle() {
        FutureTask<Object> futureTask = new FutureTask<>(()->ThreadSafetyLazyEnumsSingle.INSTANCE );
        FutureTask<Object> futureTaskTwo = new FutureTask<>(()->ThreadSafetyLazyEnumsSingle.INSTANCE);    
        new Thread(futureTask).start();
        new Thread(futureTaskTwo).start();
        try {
            assertEquals( futureTask.get(),futureTaskTwo.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDoubleSingle() {
        FutureTask<Object> futureTask = new FutureTask<>(ThreadSafetyDoubleDetectionLazySingle::getInstance );
        FutureTask<Object> futureTaskTwo = new FutureTask<>(ThreadSafetyDoubleDetectionLazySingle::getInstance );    
        new Thread(futureTask).start();
        new Thread(futureTaskTwo).start();
        try {
            assertEquals( futureTask.get(),futureTaskTwo.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
