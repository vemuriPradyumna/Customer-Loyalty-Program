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
                System.out.println("1. List all customers that are not part of Brand02’s program.");
                System.out.println("2. List customers that have joined a loyalty program but have not participated in any activity in that program (list the customerid and the loyalty program id).");
                System.out.println("3. List the rewards that are part of Brand01 loyalty program.");
                System.out.println("4. List all the loyalty programs that include “refer a friend” as an activity in at least one of their reward rules.");
                System.out.println("5. For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.");
                System.out.println("6. List customers of Brand01 that have redeemed at least twice.");
                System.out.println("7. All brands where total number of points redeemed overall is less than 500 points");
                System.out.println("8. For Customer C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021.");
                System.out.println("9. Go Back.");
                selection = sc.nextInt();
                sc.nextLine();
                switch (selection) {
                case 1:
                    System.out.println("List all customers that are not part of Brand02’s program.");
                    break;
                case 2:
                    System.out.println("List customers that have joined a loyalty program but have not participated in any activity in that program.");
                    break;
                case 3:
                    System.out.println("List the rewards that are part of Brand01 loyalty program.");
                    break;
                case 4:
                    System.out.println("List all the loyalty programs that include “refer a friend” as an activity in at least one of their reward rules.");
                    break;
                case 5:
                    System.out.println("For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.");
                    break;
                case 6:
                    System.out.println("List customers of Brand01 that have redeemed at least twice.");
                    break;
                case 7:
                    System.out.println("All brands where total number of points redeemed overall is less than 500 points");
                    break;   
                case 8:
                    System.out.println("For Customer C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021.");     
                    break;
                case 9:
                    System.out.println("Going back....");
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

    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }
    }

}
