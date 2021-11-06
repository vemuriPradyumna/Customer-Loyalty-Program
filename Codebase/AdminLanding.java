package Codebase;

import java.sql.*;
import java.util.Scanner;

public class AdminLanding {
    
    public static void AdminLandingInterface(Connection conn){
        Scanner sc = new Scanner(System.in);
        int selection = 0;
        try {
            try {
                do{
                    System.out.println("\t\tHOME\n\n");
                    System.out.println("1. Add Brand");
                    System.out.println("2. Add Customer");
                    System.out.println("3. Show Brand's Info");
                    System.out.println("4. Show Customer's Info");
                    System.out.println("4. Show Customer's Info");
                    System.out.println("5. Add activity type");
                    System.out.println("6. Add reward type");
                    System.out.println("7. Logout");
                    selection = sc.nextInt();
                    switch (selection) {
                    case 1:
                        System.out.println("1. Adding Brand");
                        break;
                    case 2:
                        System.out.println("2. Adding Customer");
                        break;
                    case 3:
                        System.out.println("3. Showing Brand's Info");
                        break;
                    case 4:
                        System.out.println("4. Showing Customer's Info");
                        break;
                    case 5:
                        System.out.println("5. Adding activity type");
                        break;
                    case 6:
                        System.out.println("6. Adding reward type");
                        break;
                    case 7:
                        System.out.println("7. Logging out");
                        Home.HomeUi(conn);
                        break;    
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
                } while(selection<=0 || selection>7);
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

