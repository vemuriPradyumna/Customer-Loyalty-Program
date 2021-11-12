package Codebase;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

public class AdminLanding {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;

    public static void AdminLandingInterface(Connection conn){

        AdminLanding.conn = conn;
        Scanner sc = new Scanner(System.in);
        int selection = 0;
        boolean flag = true;
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
                        flag = false;
                        break;    
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
                } while(flag);
            } finally {
                //close(conn);
            }
        }

        catch (Exception e) {
            System.out.println("Failed to connect to DB");
            e.printStackTrace();
        }
    }

    static void BrandSignUpUI (Connection conn) throws SQLException{
        boolean firstCheck = false;
        sc = new Scanner(System.in);
        AdminLanding.conn = conn;
        AdminLanding.statement = conn.createStatement();
        String userId = null;
        System.out.println("\t\t ADDING BRAND \n\n");
        System.out.println();
        
        do {
            if (firstCheck){
                System.out.println("Brand ID already exists \nPlease select a new one\n\n");
            }
            System.out.println("Choose brand username: \n");
            userId = sc.nextLine();
            String sql_check = "Select user_id from market_place where user_id= '"+userId+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
            firstCheck = true;
        } while (result.next() == true);
        
        firstCheck = false;
        System.out.println("Enter brand Name");
        String brandname = sc.nextLine();
        System.out.println("Enter brand Address");
        String brandAddress = sc.nextLine();
        System.out.println("Choose brand password");
        String brandPassword = sc.nextLine();
        if(addUser(userId, brandPassword, "Brand")){
            System.out.println("Login details added!");
        }
        else{
            System.out.println("Failed to add login details");;
        }
        
        System.out.println(userId+" "+brandname+" "+brandAddress);
        if(addBrand(userId, brandname, brandAddress)){
            System.out.println("Brand has been successfully added to the System.");
            System.out.println("Brand can login with these credentials!");
        } else{
            System.out.println("Error while adding new Brand");
        }
    }

    static void CustomerSignUpUI(Connection conn) throws SQLException, ParseException{
        AdminLanding.conn = conn;
        sc = new Scanner(System.in);
        boolean firstCheck = false;
        AdminLanding.statement = conn.createStatement();
        String userId = null;
        System.out.println("\t\t ADDING CUSTOMER \n\n");
                    System.out.println();
                    
                    do {
                        if (firstCheck){
                            System.out.println("Customer ID already exists \nPlease select a new one\n\n");
                        }
                        System.out.println("Choose customer username: \n");
                        userId = sc.nextLine();
                        String sql_check = "Select user_id from market_place where user_id= '"+userId+"'";
                        // System.out.println(sqlCred);
                        result = statement.executeQuery(sql_check);
                        firstCheck = true;
                    } while (result.next() == true);
                    
                    firstCheck = false;
                    System.out.println("Enter customer Name");
                    String customerName = sc.nextLine();
                    System.out.println("Enter Address");
                    String address = sc.nextLine();
                    System.out.println("Enter Phone Number");
                    String phoneNumber = sc.nextLine();
                    System.out.println("Choose customer password");
                    String customerPassword = sc.nextLine();
                    if(addUser(userId, customerPassword, "Customer")){
                        System.out.println("Login details added!");
                    }
                    else{
                        System.out.println("Failed to add login details");;
                    }
                    
                    System.out.println(userId+" "+customerName+" "+address+" "+phoneNumber);
                    if(addCustomer(userId, customerName, address, phoneNumber)){
                        System.out.println("Customer has been successfully added to our System.");
                        System.out.println("Customer can now login with these credentials");
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
        Calendar calendar = Calendar.getInstance();
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


    static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable whatever) {
            }
        }
    }

    }

