package Codebase;

import java.sql.*;
import java.util.Scanner;

public class Login {

    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner in = null;

    public static void loginInterface(Connection conn) {
            try {
                statement = conn.createStatement();
                // Runtime.getRuntime().exec("clear");
                System.out.println("\t\tLOGIN\n\n");
                boolean firstCheck = false;
                do {
                    if (firstCheck)
                        System.out.println("Wrong Credentials Entered!!! \nPlease Try Again.\n\n");
                    System.out.println("Enter your username: ");
                    in = new Scanner(System.in);
                    String loginUser = in.nextLine();
                    System.out.println("Enter your password: ");
                    String loginPwd = in.nextLine();

                    String sqlCred = "select user_id,pwd,user_type from MARKET_PLACE where user_id=  '" + loginUser
                            + "' and pwd='" + loginPwd + "'";

                    // System.out.println(sqlCred);
                    result = statement.executeQuery(sqlCred);
                    firstCheck = true;
                } while (!result.next());
                String userId = result.getString("USER_ID");
                String userType = result.getString("USER_TYPE");
                System.out.println(userId+" Login successful!  \n");
                System.out.println("Welcome "+userType);
                
                LoggedInUser loggedInUser = new LoggedInUser(userId, userType);

                switch (userType) {
                    case "Customer":
                        System.out.println("Customer landing\n\n");
                        CustomerLanding.CustomerLandingInterface(conn);
                        break;
                    case "Brand":
                        System.out.println("Brand landing");
                        BrandLandingInterface.BrandUi(conn, loggedInUser);
                        break;
                    case "Admin":
                        System.out.println("Admin landing\n\n");
                        AdminLanding.AdminLandingInterface(conn);
                        break;
                    default:
                        System.out.println("Something went wrong...");
                    }

            } catch (Exception e){
                e.printStackTrace();
            } 
            finally {
                close(in);
                close(result);
                close(statement);
            }
        }
    static void close(Scanner sc) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Throwable thr) {
                }
            }
    }
        
    static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Throwable thr) {
            }
        }
    }

    static void close(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (Throwable thr) {
            }
        }
    }

}
