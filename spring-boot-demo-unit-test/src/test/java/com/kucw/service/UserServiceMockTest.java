package com.kucw.service;

import com.kucw.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceMockTest {

    @MockBean
    private UserService userService;

    @Test
    public void test() throws Exception {
        /**
         * Mockito 的語法通常是 when(object.methodName()).thenReturn(response) 表示當methodName這個方法被call時，就return response這個結果
         * 如果沒有先定義好 when().thenReturn()，就直接去調用該方法時，默認return null
         */

        //當使用任何int值call userService的getUserById方法時，就回傳一個名字為"I'm mockito name"的user
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(new User(200, "I'm mockito name", new Date()));

        //限制只有當input的數字是3時，才會return名字為"I'm no.3"的User
        Mockito.when(userService.getUserById(3)).thenReturn(new User(3, "I'm no.3", new Date()));

        //當一個method有定義多次return值時，會從最後定義的那個when開始比對，如果參數符合的話，就返回那個when的return
        User user = userService.getUserById(3); //所以這裡會返回 "I'm no.3" User
        User user2 = userService.getUserById(5); //而這裡會返回 "I'm mockito name" User

        //當call userService的insertUser時，不管傳進來的User的值是什麼，都回傳100
        Mockito.when(userService.insertUser(Mockito.any(User.class))).thenReturn(100);

        Integer i = userService.insertUser(new User()); //會返回100

        /**
         * Mockito不僅能模擬方法調用返回值，也能記錄該mock對象的歷史調用記錄，可以使用verify()來檢查mock對象的某個方法是否曾被調用、或是他的方法調用順序
         */

        //檢查調用getUserById、且參數為3的次數是否為1次
        Mockito.verify(userService, Mockito.times(1)).getUserById(Mockito.eq(3)) ;

        //驗證調用順序，確保mock對象會先調用getUserById兩次with特定parameter，然後才調用insertUser
        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).getUserById(3);
        inOrder.verify(userService).getUserById(5);
        inOrder.verify(userService).insertUser(Mockito.any(User.class));

        /**
         * 除了when().thenReturn()可以設置mock對象的方法返回值之外，也可以使用when().thenThrow()來拋出一個異常
         */

        //當調用getUserById時的參數是9時，拋出一個RuntimeException
        Mockito.when(userService.getUserById(9)).thenThrow(new RuntimeException("mock throw exception"));

        //如果方法沒有return值的話，要改用doThrow()拋出Exception
        //當call userService的print方法時，拋出一個Exception
        Mockito.doThrow(new UnsupportedOperationException("mock throw unsupported exception")).when(userService).print();
    }
}