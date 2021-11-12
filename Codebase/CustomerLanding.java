package Codebase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import Codebase.POJO.Activity;
import Codebase.POJO.LoggedInUser;
import Codebase.POJO.ReRule;
import Codebase.POJO.Reward;
import oracle.jdbc.logging.annotations.Log;

public class CustomerLanding {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;

    public static void CustomerLandingInterface(Connection conn, LoggedInUser loggedInUser){       
        
            try {
                CustomerLanding.conn = conn;
                int selection = 0;
                CustomerLanding.sc = new Scanner(System.in);
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
                        enrollInLoyaltyProgram(conn, loggedInUser);
                        break;
                    case 2:
                        System.out.println("Reward Activities.....");
                        rewardActivities(conn, loggedInUser);
                        break;
                    case 3:
                        System.out.println("View Wallet....");
                        //ViewWallet.ViewWalletInterface(conn, loggedInUser);
                        ViewWalletInterface(conn, loggedInUser);
                        break;
                    case 4:
                        rewardRedeeming(conn, loggedInUser);
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
                close(conn);
            }       
        }
    
        
    static void enrollInLoyaltyProgram(Connection conn, LoggedInUser loggedInUser) throws SQLException{
        
        String lp = null;
        int selection = 0;
        boolean flag = true; 
        try {
            
            statement = conn.createStatement();
            System.out.println("\t\t All the available Loyalty Program: \n");

            String sql_check = "SELECT DISTINCT lp_name FROM Brand WHERE lp_name IS NOT NULL";
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

    static void rewardActivities(Connection conn, LoggedInUser loggedInUser) throws SQLException{
        int selection = 0;
        boolean flag = true;
        ArrayList<String> lpList = new ArrayList<>();
        do {
            String getEnrolledLoyaltyPrograms = "SELECT DISTINCT LP_CODE FROM cust_wallet WHERE WALLET_ID='"+loggedInUser.getWalletId()+"'";

            // System.out.println(sqlCred);
            result = statement.executeQuery(getEnrolledLoyaltyPrograms);
            String lp = null;
            while (result.next()) {
                lp = result.getString("LP_CODE");
                lpList.add(lp);
            }
            System.out.println("\t\t These are your enrolled loyalty programs\n");

            for(int i = 0;i< lpList.size();i++){
                System.out.println(i+1+". "+lpList.get(i));
            }
            System.out.println((lpList.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=lpList.size()){
                performRewardActivities(lpList.get(selection-1),loggedInUser);
            } else if(selection == lpList.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    static void rewardRedeeming(Connection conn,LoggedInUser loggedInUser) throws SQLException{
        int selection = 0;
        boolean flag = true;
        do {
            System.out.println("\t\t REWARD REDEMPTION MENU\n\n");
            System.out.println("1. Rewards Selection");
            System.out.println("2. Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            switch (selection) {
            case 1:
                rewardSelect(conn, loggedInUser);
                break;
            case 2:
                System.out.println("Going back...");
                flag = false;
                break;
            default:
                System.out.println("You have entered an incorrect selection try again");
            }
        } while (flag);
    }
    
    static void rewardSelect(Connection conn,LoggedInUser loggedInUser) throws SQLException{
        int selection = 0;
        boolean flag = true;
        ArrayList<Reward> rewards = new ArrayList<>();
        do {
            String getRewardsList = "select REWARD_CAT_CODE,REWARD_NAME from REWARDS_TYPE";
            // System.out.println(sqlCred);
            result = statement.executeQuery(getRewardsList);
            Reward reward = null;
            while (result.next()) {
                reward = new Reward(result.getString("REWARD_CAT_CODE"), result.getString("REWARD_NAME"));
                rewards.add(reward);
            }
            System.out.println("\t\t REDEEM REWARD TYPE\n\n");
            for(int i = 0;i<rewards.size();i++){
                System.out.println(i+1+". "+rewards.get(i).getRewardName());
            }
            System.out.println((rewards.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=rewards.size()){
                System.out.println("Reward to be selected");
            } else if(selection == rewards.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    static void performRewardActivities(String lpCode, LoggedInUser loggedInUser) throws SQLException{
        int selection = 0;
        boolean flag = true;
        String getBrandId = "SELECT brand_id FROM brand WHERE LP_CODE='"+lpCode+"'";
        result = statement.executeQuery(getBrandId);
        result.next();
        String brandId = result.getString("BRAND_ID");
        do {
            String getAcitivityCodes = "SELECT RE_RULE_CODE,BRAND_ID,ACT_CAT_CODE,RULE_VERSION,POINTS,ACTIVITY_NAME FROM brand WHERE BRAND_ID='"+brandId+"'";
            result = statement.executeQuery(getAcitivityCodes);
            ArrayList<ReRule> reRuleList = new ArrayList<>();
            while (result.next()) {
                reRuleList.add(new ReRule(result.getString("RE_RULE_CODE"), result.getString("BRAND_ID"), result.getString("ACT_CAT_CODE"), result.getString("ACTIVITY_NAME"), result.getString("RULE_VERSION"), Integer.parseInt(result.getString("POINTS"))));
            }
            System.out.println("\t\t List of available activities in the selected loyalty program\n");

            for(int i = 0;i< reRuleList.size();i++){
                System.out.println(i+1+". "+reRuleList.get(i).getActivityName());
            }
            System.out.println((reRuleList.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=reRuleList.size()){
                addPointsToWallet(loggedInUser,reRuleList.get(selection-1).getPoints(),lpCode);
            } else if(selection == reRuleList.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }



    static void addPointsToWallet(LoggedInUser loggedInUser, int points, String lpCode){
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = CustomerLanding.conn
                    .prepareStatement("UPDATE CUST_WALLET SET CURRENT_POINTS = CURRENT_POINTS + ? WHERE WALLET_ID = ? AND LP_CODE = ?");
            ps.setInt(1, points);
            ps.setString(2, loggedInUser.getWalletId());
            ps.setString(3, lpCode);
            int id1 = ps.executeUpdate();
            
            System.out.println(id1);

            if (id1 > 0) {
                System.out.println("Current points updated");
            } else {
                System.out.println("Row not found");
            }


            PreparedStatement ps1 = CustomerLanding.conn
            .prepareStatement("UPDATE CUST_WALLET SET POINTS_ACC = POINTS_ACC + ? WHERE WALLET_ID = ? AND LP_CODE = ?");
            
            ps1.setInt(1, points);
            ps1.setString(2, loggedInUser.getWalletId());
            ps1.setString(3, lpCode);
            int id2 = ps.executeUpdate();

            System.out.println(id2);

          
            if (id2 > 0) {
                System.out.println("Points accumilated updated..");
            } else {
                System.out.println("row not fount");
            }
            conn.commit();  

        } catch (SQLException ex) {
            ex.printStackTrace();
            if(conn!=null)
            {
            	try {
            		System.out.println("Transaction is being rolled back");
            		conn.rollback();                  //if any exception occurs rollback the transaction
            	}catch(SQLException se)
            	{
            	se.printStackTrace();
            	}
            }
        } finally{
            try {
				conn.setAutoCommit(true);       // set auto commit to true 
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
