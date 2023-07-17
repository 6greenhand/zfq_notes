package com.example.lastestcar01;

import com.example.lastestcar01.pojo.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class LastestCar01ApplicationTests {

    public static void main(String[] args) {
        Menu menu = new Menu();

        //start的方法是打印菜单
        menu.start();


    }


}
