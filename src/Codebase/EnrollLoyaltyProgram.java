package src.Codebase;

import java.sql.*;
import java.util.Scanner;

public class EnrollLoyaltyProgram {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = new Scanner(System.in);

    static void enrollInLoyaltyProgram(Connection conn, LoggedInUser loggedInUser) throws SQLException{
        
        String lp = null;
        int selection = 0;
        boolean flag = true; 
        try {
            
            statement = conn.createStatement();
            System.out.println("\t\t All the available Loyalty Program: \n");

            String sql_check = "SELECT DISTINCT lp_name FROM Brand WHERE lp_name IS NOT NULL and ACTIVE_FLAG=1";
            result = statement.executeQuery(sql_check);
       
                    while (result.next()){
       
                        System.out.println("\n"+result.getString("lp_name")+"\n");
       
                    }
            System.out.println("Please enter a Loyalty Program of your choice:");
            lp = sc.nextLine();
            System.out.println("\n1. Enroll in the Loyalty Program?\n");
            System.out.println("\n2. Go back\n");
            selection = sc.nextInt();
            sc.nextLine();                
            do{
                switch (selection) {
                    case 1:
                        boolean isthere = false;
                        ResultSet result1 = null;
                        result1 = statement.executeQuery("SELECT lp_name FROM brand WHERE lp_code = (SELECT lp_code FROM cust_wallet WHERE wallet_id = (SELECT wallet_id FROM customer WHERE customer_id = '"+loggedInUser.getUser_Id()+"'))");
                        while(result1.next()){
                            if (result1.getString("lp_name").equals(lp)) isthere = true;
                            //System.out.println(result1.getString("lp_name"));
                        }
                        if(isthere){
                            System.out.println("\nYou are already enrolled in this program!\n");
                            flag = false;
                        }
                        else{
                            System.out.println("\nAdding you to this program!\n");
                            String wallet = null;
                            ResultSet result2 = null;
                            result2 = statement.executeQuery("SELECT wallet_id FROM customer WHERE customer_id = '"+loggedInUser.getUser_Id()+"'");
                            result2.next();
                            wallet = result2.getString("wallet_id");
                            String lp1 = null;
                            ResultSet result3 = null;
                            result3 = statement.executeQuery("SELECT lp_code FROM brand WHERE lp_name = '"+lp+"'");
                            result3.next();
                            lp1 = result3.getString("lp_code");

                            ResultSet result4 = null;
                            statement.executeQuery("INSERT INTO cust_wallet(lp_code,wallet_id) VALUES ('"+lp1+"','"+wallet+"')");
                            
                            System.out.println("Your Wallet for this Loyalty Program is: \n");

                            result4 = statement.executeQuery("SELECT * FROM cust_wallet WHERE lp_code = '"+lp1+"' AND wallet_id = '"+wallet+"'");
                            while(result4.next()){
                                System.out.println("\n"+"Loyalty Program code: "+result4.getString("lp_code")+"\n"+"Points accumulated: "+result4.getString("points_acc")+"\n"+"Tier Status: "+result4.getString("tier_status")+"\n"+"Current Points: "+result4.getString("current_points")+"\n"+"Wallet ID: "+result4.getString("wallet_id"));
                            }
                            flag = false;
                        }
                        break;

                    case 2:
                        flag = false;
                        break;
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                }
            }while(flag);
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
