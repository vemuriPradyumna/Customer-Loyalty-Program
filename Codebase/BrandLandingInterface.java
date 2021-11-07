package Codebase;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

public class BrandLandingInterface {
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;

    public static void BrandUi(Connection conn){
        try {
            BrandLandingInterface.conn = conn;
            statement = conn.createStatement();
            int selection = 0;
            sc = new Scanner(System.in);
            do {
                System.out.println("\t\t BRAND LANDING PAGE\n\n");
                System.out.println("1. Add Loyalty program");
                System.out.println("2. Add Reward Earning Rules");
                System.out.println("3. Update Reward Earning Rules");
                System.out.println("4. Add Reward Redemption rules");
                System.out.println("5. Update Reward Redemption rules");
                System.out.println("6. Validate Loyalty Program");
                System.out.println("7. Log out");
                selection = sc.nextInt();
                sc.nextLine();
                switch (selection) {
                case 1:
                    addLoyaltyProgram();
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 3:
                    System.out.println("");
                    break;
                case 4:
                    System.out.println("");
                    break; 
                case 5:
                    System.out.println("");
                    break;  
                case 6:
                    System.out.println("");
                    break;
                case 7:
                    System.out.println("");
                    break;                      
                default:
                    System.out.println("You have entered an incorrect selection try again");
                }
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(sc);
            close(result);
            close(statement);
        }

   
    }
    static void addLoyaltyProgram(){
            int selection = 0;
            do {
                System.out.println("\t\t ADD LOYALTY PROGRAM\n\n");
                System.out.println("1. Regular");
                System.out.println("2. Tier");
                System.out.println("3. Go Back");
                selection = sc.nextInt();
                sc.nextLine();
                switch (selection) {
                case 1:
                    // System.out.println("");
                    addRegularLoyalty();
                    break;
                case 2:
                    addTieredLoyalty();
                    break;
                case 3:
                    System.out.println("Going back...");
                    break;
                default:
                    System.out.println("You have entered an incorrect selection try again");
                }
            } while (true);
    }

    static void addRegularLoyalty(){
        
    }

    static void addTieredLoyalty(){
        
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
