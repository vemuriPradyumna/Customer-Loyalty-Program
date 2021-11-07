package Codebase;

import java.sql.*;
import java.util.Scanner;

public class CustomerLanding {
    
    public static void CustomerLandingInterface(Connection conn){

        Scanner sc = new Scanner(System.in);
        int selection = 0;

        try {
            try {
                do{
                    System.out.println("\t\tHOME\n\n");
                    System.out.println("1. Enroll in Loyalty program.");
                    System.out.println("2. Reward Activities");
                    System.out.println("3. View Wallet");
                    System.out.println("4. Redeem points");
                    System.out.println("5. Logout");
                    selection = sc.nextInt();
                    switch (selection) {
                    case 1:
                        System.out.println("Enrolling in Loyalty program.....");
                        break;
                    case 2:
                        System.out.println("Reward Activities.....");
                        break;
                    case 3:
                        System.out.println("View Wallet....");
                        break;
                    case 4:
                        System.out.println("Redeem points.......");
                        break;
                    case 5:
                        System.out.println("Exiting.........");
                        Home.HomeUi(conn);
                        break;  
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
                } while(selection<=0 || selection>5);
            } finally {
                sc.close();
                close(conn);
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
