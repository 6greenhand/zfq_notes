package com.example.lastestcar01.pojo;

import com.example.lastestcar01.domain.User;
import com.example.lastestcar01.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class Menu {
    @Resource
    UserService userService;

        //集合

    //菜单
    public void start() {
        Scanner scanner1 = new Scanner(System.in);
        int choice;
        do {
            displayMainMenu();
            choice = scanner1.nextInt();// Consume the newline character

            switch (choice) {
                case 1:
                    login();
                    break;
//                case 2:
//                    register();
//                    break;
//                case 3:
//                    displayLatestCars();
//                    break;
//                case 4:
//                    searchCars();
//                    break;
//                case 5:
//                    if (loggedInUser != null) {
//                        compareCars();
//                    } else {
//                        System.out.println("请先登录才能使用此功能！");
//                    }
//                    break;
//                case 6:
//                    if (loggedInUser != null) {
//                        displayFavorites();
//                    } else {
//                        System.out.println("请先登录才能使用此功能！");
//                    }
//                    break;
//                case 7:
//                    if (loggedInUser != null && loggedInUser.getUsername().equals("admin")) {
//                        adminMenu();
//                    } else {
//                        System.out.println("请以管理员身份登录才能使用此功能！");
//                    }
//                    break;
//                case 8:
//                    System.out.println("感谢使用，再见！");
//                    break;
                default:
                    System.out.println("无效的选项，请重新输入！");
                    break;
            }
            System.out.println();
        } while (choice != 8);
    }

    public void displayMainMenu() {
        System.out.println("主菜单：");
        System.out.println("1. 登陆");
        System.out.println("2. 注册");
        System.out.println("3. 最新二手车信息");
        System.out.println("4. 搜索车辆");
        System.out.println("5. 对比车辆");
        System.out.println("6. 我的收藏");
        System.out.println("7. 后台管理（仅管理员登录后可查看）");
        System.out.println("8. 退出系统");
        System.out.print("请选择：");
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        // Check if the user exists
        User user = userService.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("登录成功！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    }


}
