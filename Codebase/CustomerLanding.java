package Codebase;

import java.sql.*;
import java.util.Scanner;

public class CustomerLanding {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;

    public static void CustomerLandingInterface(Connection conn, LoggedInUser loggedInUser){       
        
            try {
                CustomerLanding.conn = conn;
                int selection = 0;
                Scanner sc = new Scanner(System.in);
                boolean main_flag = true;
                do{
                    System.out.println("\t\nCUSTOMER PAGE\n\n");
                    System.out.println("1. Enroll in Loyalty program.");
                    System.out.println("2. Reward Activities");
                    System.out.println("3. View Wallet");
                    System.out.println("4. Redeem points");
                    System.out.println("5. Logout");
                    selection = sc.nextInt();
                    sc.nextLine();
                    switch (selection) {
                    case 1:
                        System.out.println("Enrolling in Loyalty program.....");
                        EnrollLoyaltyProgram.enrollInLoyaltyProgram(conn, loggedInUser);
                        break;
                    case 2:
                        System.out.println("Reward Activities.....");
                        break;
                    case 3:
                        System.out.println("View Wallet....");
                        //ViewWallet.ViewWalletInterface(conn, loggedInUser);
                        ViewWalletInterface(conn, loggedInUser);
                        break;
                    case 4:
                        System.out.println("Redeem points.......");
                        break;
                    case 5:
                        System.out.println("Exiting.........");
                        main_flag = false;
                        break;  
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
                } while(main_flag);
            }catch (Exception e) {
                System.out.println("Failed to connect to DB");
                e.printStackTrace();
            } finally {
                //sc.close();
                close(conn);
            }       
        }

    static void ViewWalletInterface (Connection conn, LoggedInUser loggedInUser) throws SQLException{

        int selection = 0;
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        try {
                statement = conn.createStatement();
                System.out.println("\t\t WALLET DISPLAY for current user \n");
    
                String sql_check = "SELECT * FROM CUST_WALLET WHERE wallet_id = (SELECT wallet_id FROM customer WHERE customer_id = '"+loggedInUser.getUser_Id()+"')";
                result = statement.executeQuery(sql_check);
               
                while (result.next()){
               
                    System.out.println("\nLoyalty Program : "+result.getString("lp_code")+"\n"+"Points accumulated : "+result.getString("points_acc")+"\n"+"Tier Status : "+result.getString("tier_status")+"\n"+"Current Points : "+result.getString("current_points")+"\n"+"Wallet ID : "+result.getString("wallet_id")+"\n");
               
                }
    
                System.out.println("\n1. Go back\n");
                selection = sc.nextInt();
                sc.nextLine();                
            
                do{
                    switch (selection) {
                        case 1:
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


    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
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
