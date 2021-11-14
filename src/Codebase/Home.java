package src.Codebase;

import java.sql.*;
import java.util.Scanner;

public class Home {


    public static void HomeUi(Connection connection) {
        Scanner sc = new Scanner(System.in);
        int selection = 0;
        boolean flag = true;
        try {
            try {
                do{
                    System.out.println("\t\tHOME\n\n");
                    System.out.println("1. LOGIN");
                    System.out.println("2. SIGN UP ");
                    System.out.println("3. SHOW QUERIES");
                    System.out.println("4. EXIT");
                    selection = sc.nextInt();
                    sc.nextLine();
                    switch (selection) {
                    case 1:
                        System.out.println("login page");
                        Login.loginInterface(connection);
                        break;
                    case 2:
                        SignUp.SignUpInterface(connection);
                        break;
                    case 3:
                        System.out.println("Showing queries....");
                        showQueries.queryLandingInterface(connection);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        flag = false;
                        break;
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
                } while(flag);
            } finally {
                close(connection);
            }
        }

        catch (Exception e) {
            System.out.println("Failed to connect to DB");
            e.printStackTrace();
        }
    }

    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }
    }


}