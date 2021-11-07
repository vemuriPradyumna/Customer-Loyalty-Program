package Codebase;

import java.sql.*;
import java.util.Scanner;

public class ShowInfo {

    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = new Scanner(System.in);
    public static Connection conn = null;

    static void ShowBrandInfo (Connection conn) throws SQLException{
        
        int selection = 0;
        sc = new Scanner(System.in);
        boolean flag = true;
        try {
        do{
        
            System.out.println("\n1. ShowbrandInfo\n");
            System.out.println("\n2. GO BACK\n");
            selection = sc.nextInt();
            sc.nextLine();
            switch (selection) {
                case 1:
                    SignUp.conn = conn;
                    String BrandId = null;
                    statement = conn.createStatement();
                    System.out.println("\t\t BRAND DISPLAY \n");
                    System.out.println();
       
                    System.out.println("Enter Brand Id: \n");
                    BrandId = sc.nextLine();
                    String sql_check = "Select * from brand where brand_id= '"+BrandId+"'";
                    result = statement.executeQuery(sql_check);
       
                    while (result.next()){
       
                        System.out.println("\nBrand ID : "+result.getString("brand_id")+"\n"+"Brand Name : "+result.getString("brand_name")+"\n"+"Join date : "+result.getString("join_date")+"\n"+"Address : "+result.getString("address")+"\n"+"Loyalty Program Code : "+result.getString("lp_code")+"\n"+"Loyalty Program Name : "+result.getString("lp_name")+"\n"+"Tier 1 : "+result.getString("tier1")+"\n"+"Tier 2 : "+result.getString("tier2")+"\n"+"Tier 3 : "+result.getString("tier3")+"\n"+"Multiplier for Tier 1 : "+result.getString("mult1")+"\n"+"Multiplier for Tier 2 : "+result.getString("mult2")+"\n"+"Multiplier for Tier 3 : "+result.getString("mult3")+"\n"+"Points required for Tier 1 : "+result.getString("points_req_tier1")+"\n"+"Points required for Tier 2 : "+result.getString("points_req_tier2")+"\n"+"Points required for Tier 3 : "+result.getString("points_req_tier3"));
       
                    }
                    break;
                case 2:
                    //AdminLanding.AdminLandingInterface(conn);
                    flag =false;
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

static void ShowCustomerInfo (Connection conn) throws SQLException{
        
    int selection = 0;
    sc = new Scanner(System.in);
    boolean flag1 = true;
    try {
    do{
    
        System.out.println("\n1. ShowCustomerInfo\n");
        System.out.println("\n2. GO BACK\n");
        selection = sc.nextInt();
        sc.nextLine();

        switch (selection) {
            case 1:
                SignUp.conn = conn;
                String CustomerId = null;
                statement = conn.createStatement();
                System.out.println("\t\t CUSTOMER DISPLAY \n");
                System.out.println();
   
                System.out.println("Enter Customer Id: \n");
                CustomerId = sc.nextLine();
                String sql_check = "Select * from customer where customer_id= '"+CustomerId+"'";
                result = statement.executeQuery(sql_check);
   
                while (result.next()){
   
                    System.out.println("\nCustomer ID : "+result.getString("customer_id")+"\n"+"Customer Name : "+result.getString("customer_name")+"\n"+"Address : "+result.getString("address")+"\n"+"Phone number : "+result.getString("phno")+"\n"+"Wallet ID : "+result.getString("wallet_id")+"\n");
   
                }
                break;
            case 2:
                //AdminLanding.AdminLandingInterface(conn);
                flag1 = false;
                break;

            default:
                System.out.println("You have entered an incorrect selection try again");
            }

    

}while(flag1);
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
