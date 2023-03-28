package org.imzdong.tool.geektime;

import org.junit.jupiter.api.Test;

/**
 * @author DongZhou
 * @since 2023/3/27 13:48
 */
public class GeekTimeLoginTest {


    @Test
    public void testLogin(){
        GeekTimeLogin geekTimeLogin = new GeekTimeLogin("18500299025","zH&646555448");
        geekTimeLogin.login();
    }
}
