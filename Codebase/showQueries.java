package Codebase;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;


public class showQueries {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;

    public static void queryLandingInterface(Connection conn, LoggedInUser loggedInUser){

    showQueries.conn = conn;
    Scanner sc = new Scanner(System.in);
    int selection = 0;
    boolean main_flag = true;

    try {
        try {
            do{
                System.out.println("\t\tADMIN LANDING PAGE\n\n");
                System.out.println("1. Add Brand");
                System.out.println("2. Add Customer");
                System.out.println("3. Show Brand's Info");
                System.out.println("4. Show Customer's Info");
                System.out.println("5. Add activity type");
                System.out.println("6. Add reward type");
                System.out.println("7. Logout");
                selection = sc.nextInt();
                sc.nextLine();
                switch (selection) {
                case 1:
                    System.out.println("Adding Brand.....");
                    BrandSignUpUI(conn);
                    break;
                case 2:
                    System.out.println("Adding Customer.....");
                    CustomerSignUpUI(conn);
                    break;
                case 3:
                    System.out.println("Showing Brand's Info.....");
                    ShowInfo.ShowBrandInfo(conn);
                    break;
                case 4:
                    System.out.println("Showing Customer's Info.......");
                    ShowInfo.ShowCustomerInfo(conn);
                    break;
                case 5:
                    System.out.println("Adding activity type.........");
                    AddInfo.addActivityType(conn);
                    break;
                case 6:
                    System.out.println("Adding reward type.......");
                    AddInfo.addRewardType(conn);
                    break;
                case 7:
                    System.out.println("Logging out....");
                    main_flag = false;
                    break;    
                default:
                    System.out.println("You have entered an incorrect selection try again");
                }
            } while(main_flag);
        } finally {
            close(conn);
        }
    }

    catch (Exception e) {
        System.out.println("Failed to connect to DB");
        e.printStackTrace();
    }
    }

}
