package com.kucw.service;

import com.kucw.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceSpyTest {

    @SpyBean
    private UserService userService;

    @Test
    public void test() throws Exception {
        //雖然我們已經spy了userService，但是因為還沒有自己override任何方法的返回值，所以Spring會去用這個bean原本正常的方法
        //所以這裡返回的user才會是正常的John
        User user = userService.getUserById(1);

        /**
         * spy和mock一樣，也是使用 when(object.methodName()).thenReturn(response) 來設定方法返回值
         */

        //設定當input為3時，返回名字為"I'm no.3"的User
        Mockito.when(userService.getUserById(3)).thenReturn(new User(3, "I'm no.3", new Date()));

        //設定當input為任意數時，返回名字為"I'm any"的User
        Mockito.when(userService.getUserById(5)).thenReturn(new User(200, "I'm any", new Date()));

        //spy跟mock一樣，當一個method有定義多次return值時，也是從最後定義的那個when開始比對，如果參數符合的話，就返回那個when的doReturn，如果都沒有符合的，就call原本的bean的方法
        User user2 = userService.getUserById(3); //所以這裡返回的user會是 I'm any
        User user3 = userService.getUserById(5); //這裡返回的user也會是 I'm any
        User user4 = userService.getUserById(1); //這裡返回的user則是DB裡的 John

        //thenThrow和verifiy用法都跟mock一樣，就不展開了
    }
}