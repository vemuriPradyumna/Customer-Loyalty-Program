package Codebase;

import java.sql.*;
import java.util.Scanner;

public class ViewWallet {

    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = new Scanner(System.in);
    public static Connection conn = null;

    static void ViewWalletInterface (Connection conn, LoggedInUser loggedInUser) throws SQLException{

        int selection = 0;
        try {
            
            
                System.out.println("\n1. Show Wallet and go back\n");
                statement = conn.createStatement();
                System.out.println("\t\t WALLET DISPLAY for current user \n");

                String sql_check = "SELECT * FROM CUST_WALLET WHERE wallet_id = (SELECT wallet_id FROM customer WHERE customer_id = '"+loggedInUser.getUser_Id()+"')";
                result = statement.executeQuery(sql_check);
           
                        while (result.next()){
           
                            System.out.println("\nLoyalty Program : "+result.getString("lp_code")+"\n"+"Points accumulated : "+result.getString("points_acc")+"\n"+"Tier Status : "+result.getString("tier_status")+"\n"+"Current Points : "+result.getString("current_points")+"\n"+"Wallet ID : "+result.getString("wallet_id")+"\n");
           
                        }

                selection = sc.nextInt();
                sc.nextLine();                
        do{
                switch (selection) {
                    case 1:
                        CustomerLanding.CustomerLandingInterface(conn, loggedInUser);
                        break;
    
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
        }while(true);
    }catch (Exception e) {
        e.printStackTrace();
    } finally {
        close(result);
        close(statement);
    }

    }

    static void close(Scanner sc) {
        if (statement != null) {
            try {
                sc.close();
            } catch (Throwable whatever) {
            }
        }
    }
    
    static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Throwable whatever) {
            }
        }
    }
    
    static void close(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Throwable whatever) {
            }
        }
    }
    
}
