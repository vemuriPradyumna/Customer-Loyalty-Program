package src.Codebase;

import java.sql.*;
import java.util.Scanner;

public class AddInfo {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static ResultSet result1 = null;
    public static Scanner sc = new Scanner(System.in);
    public static Connection conn = null;

    static void addActivityType (Connection conn) throws SQLException{
        
        int selection = 0;
        boolean flag = true;
        try {
        do{
        
            System.out.println("\n1. Add Activity Type\n");
            System.out.println("\n2. GO BACK\n");
            selection = sc.nextInt();
            sc.nextLine();
           
            switch (selection) {
                case 1:
                    SignUp.conn = conn;
                    String actCode = null;
                    String actName = null;
                    statement = conn.createStatement();
                    System.out.println("\t\t Add Activity Type \n");
                    System.out.println();
       
                    System.out.println("Enter Activity Type code: \n");
                    actCode = sc.nextLine();
                    System.out.println("Enter Activity Name: \n");
                    actName = sc.nextLine();
                    String sql_check = "INSERT INTO activities(act_cat_code, act_name) VALUES ('"+actCode+"','"+actName+"')";
                    result = statement.executeQuery(sql_check);
                    
                    result1 = statement.executeQuery("SELECT * FROM activities WHERE act_cat_code = '"+actCode+"' AND act_name = '"+actName+"'");
                    System.out.println("\nThe new activity has been added!\n");
                    while (result1.next()){
       
                        System.out.println("\nActivity Type Code : "+result1.getString("act_cat_code")+"\n"+"Activity Name : "+result1.getString("act_name")+"\n");
       
                    }
                    break;
                case 2:
                    //AdminLanding.AdminLandingInterface(conn);
                    flag = false;
                    break;

                default:
                    System.out.println("You have entered an incorrect selection try again");
                }

        
    
    }while(flag);
}catch (Exception e) {
    e.printStackTrace();
    e.getMessage();
} finally {
    close(result);
    close(statement);
}

}

static void addRewardType (Connection conn) throws SQLException{
        
    int selection = 0;
    boolean flag1 = true;
    try {
    do{
    
        System.out.println("\n1. Add Reward Type\n");
        System.out.println("\n2. GO BACK\n");
        selection = sc.nextInt();
        sc.nextLine();

        switch (selection) {
            case 1:
                SignUp.conn = conn;
                String rewCode = null;
                String rewName = null;
                statement = conn.createStatement();
                System.out.println("\t\t Add Reward Type \n");
                System.out.println();
   
                System.out.println("Enter Reward Type code: \n");
                rewCode = sc.nextLine();
                System.out.println("Enter Reward Name: \n");
                rewName = sc.nextLine();
                String sql_check = "INSERT INTO reward_type(reward_cat_code, reward_name) VALUES ('"+rewCode+"','"+rewName+"')";
                result = statement.executeQuery(sql_check);
                
                result1 = statement.executeQuery("SELECT * FROM reward_type WHERE reward_cat_code = '"+rewCode+"' AND reward_name = '"+rewName+"'");
                System.out.println("\nThe new reward has been added!\n");
                while (result1.next()){
   
                    System.out.println("\nReward Type Code : "+result1.getString("reward_cat_code")+"\n"+"Reward Name : "+result1.getString("reward_name")+"\n");
   
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
e.getMessage();
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
