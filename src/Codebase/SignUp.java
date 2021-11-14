package src.Codebase;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;



public class SignUp {
    public static Connection conn = null;
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = new Scanner(System.in);
    public static boolean firstCheck = false;
    public static Calendar calendar = Calendar.getInstance();
    public static Date currentTime;

    public static void SignUpInterface(Connection conn) {
        try {
            SignUp.conn = conn;
            statement = conn.createStatement();
            int selection = 0;
            boolean flag = true;
            do {
                System.out.println("\t\t SIGN UP \n\n");
                System.out.println("1. BRAND SIGN UP");
                System.out.println("2. CUSTOMER SIGN UP ");
                System.out.println("3. GO BACK");
                selection = sc.nextInt();
                sc.nextLine();
                switch (selection) {
                case 1:
                    BrandSignUpUI(conn);
                    break;
                case 2:
                    CustomerSignUpUI(conn);
                    break;
                case 3:
                    System.out.println("Going out...");
                    flag = false;
                    break;
                default:
                    System.out.println("You have entered an incorrect selection try again");
                }
            } while (flag);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(result);
            close(statement);
        }
    }

    static void BrandSignUpUI (Connection conn) throws SQLException{
        SignUp.conn = conn;
        SignUp.statement = conn.createStatement();
        String userId = null;
        System.out.println("\t\t BRAND SIGN UP \n\n");
        System.out.println();
        
        do {
            if (firstCheck){
                System.out.println("Brand ID already exists \nPlease select a new one\n\n");
            }
            System.out.println("Choose your username: \n");
            userId = sc.nextLine();
            String sql_check = "Select user_id from market_place where user_id= '"+userId+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
            firstCheck = true;
        } while (result.next() == true);
        
        firstCheck = false;
        System.out.println("Enter your Name");
        String brandname = sc.nextLine();
        System.out.println("Enter Address");
        String brandAddress = sc.nextLine();
        System.out.println("Choose your password");
        String brandPassword = sc.nextLine();
        if(addUser(userId, brandPassword, "Brand")){
            System.out.println("Login details added!");
        }
        else{
            System.out.println("Failed to add login details");;
        }
        
        System.out.println(userId+" "+brandname+" "+brandAddress);
        if(addBrand(userId, brandname, brandAddress)){
            System.out.println("You have been successfully added to our System.");
            System.out.println("You can now login with your credentials");
        } else{
            System.out.println("Error while adding new Brand");
        }
    }

    static void CustomerSignUpUI(Connection conn) throws SQLException, ParseException{
        SignUp.conn = conn;
        String userId = null;
        System.out.println("\t\t CUSTOMER SIGN UP \n\n");
                    System.out.println();
                    
                    do {
                        if (firstCheck){
                            System.out.println("Customer ID already exists \nPlease select a new one\n\n");
                        }
                        System.out.println("Choose your username: \n");
                        userId = sc.nextLine();
                        String sql_check = "Select user_id from market_place where user_id= '"+userId+"'";
                        // System.out.println(sqlCred);
                        result = statement.executeQuery(sql_check);
                        firstCheck = true;
                    } while (result.next() == true);
                    
                    firstCheck = false;
                    System.out.println("Enter your Name");
                    String customerName = sc.nextLine();
                    System.out.println("Enter Address");
                    String address = sc.nextLine();
                    System.out.println("Enter Phone Number");
                    String phoneNumber = sc.nextLine();
                    System.out.println("Choose your password");
                    String customerPassword = sc.nextLine();
                    if(addUser(userId, customerPassword, "Customer")){
                        System.out.println("Login details added!");
                    }
                    else{
                        System.out.println("Failed to add login details");;
                    }
                    
                    System.out.println(userId+" "+customerName+" "+address+" "+phoneNumber);
                    if(addCustomer(userId, customerName, address, phoneNumber)){
                        System.out.println("You have been successfully added to our System.");
                        System.out.println("You can now login with your credentials");
                    } else{
                        System.out.println("Error while adding new customer");
                    }
    }

    static boolean addUser(String userId,String password,String userType){
        int id = 0;

        try {
            String sql = "INSERT INTO market_place (user_id,pwd,user_type) VALUES (?,?,?)";
        
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, password);
            ps.setString(3, userType);
            id = ps.executeUpdate();
            System.out.println(id);
            ps.close();
            return true;

        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            return false;
        }
    }

    static boolean addCustomer(String userId, String name, String address, String phone) throws ParseException {

        int id = 0;

        try {
            String sql1 = "INSERT INTO customer (CUSTOMER_ID,CUSTOMER_NAME,ADDRESS,PHNO) VALUES (?,?,?,?)";
        
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, userId);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, phone);
            id = ps.executeUpdate();
            System.out.println(id);
            ps.close();
            return true;

        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            return false;
        }
    }

    static boolean addBrand(String userId, String name, String address){
        int id = 0;

        try {
            String sql1 = "INSERT INTO brand (BRAND_ID,BRAND_NAME,JOIN_DATE,ADDRESS) VALUES (?,?,?,?)";
            
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, userId);
            ps.setString(2, name);

            Date currentTime = calendar.getTime();
            long time = currentTime.getTime();
            ps.setTimestamp(3, new Timestamp(time));

            ps.setString(4, address);
            
            id = ps.executeUpdate();
            System.out.println(id);
            ps.close();
            return true;

        } catch (SQLException oops) {
            System.out.println("Connection Failed! Check output console");
            oops.printStackTrace();
            return false;
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
