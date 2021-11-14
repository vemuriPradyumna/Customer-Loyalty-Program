package Codebase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import Codebase.POJO.Activity;
import Codebase.POJO.LoggedInUser;
import Codebase.POJO.LoyaltyProgram;
import Codebase.POJO.ReRule;
import Codebase.POJO.Reward;

import java.util.Calendar;
import java.util.Date;

public class CustomerLanding {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;
    public static LoggedInUser loggedInUser= null;
    public static Calendar calendar = Calendar.getInstance();
    public static Date currentTime;

    public static void CustomerLandingInterface(Connection conn, LoggedInUser loggedInUser){       
            CustomerLanding.loggedInUser = loggedInUser;
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
                //close(conn);
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
                            statement.executeQuery("INSERT INTO cust_wallet(lp_code,wallet_id,cust_id) VALUES ('"+lp1+"','"+wallet+"','"+loggedInUser.getUser_Id()+"')");
                            
                            System.out.println("Your Wallet for this Loyalty Program is: \n");

                            result4 = statement.executeQuery("SELECT * FROM cust_wallet WHERE lp_code = '"+lp1+"' AND wallet_id = '"+wallet+"'");
                            while(result4.next()){
                                System.out.println("\n"+"Loyalty Program code: "+result4.getString("lp_code")+"\n"+"Points accumulated: "+result4.getString("points_acc")+"\n"+"Current Points: "+result4.getString("current_points")+"\n"+"Wallet ID: "+result4.getString("wallet_id")+"\nCustomer ID : "+result.getString("cust_id")+"\n");
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
               
                    System.out.println("\nLoyalty Program : "+result.getString("lp_code")+"\n"+"Points accumulated : "+result.getString("points_acc")+"\n"+"Current Points : "+result.getString("current_points")+"\n"+"Wallet ID : "+result.getString("wallet_id")+"\n"+"Customer ID : "+result.getString("cust_id")+"\n");
               
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
        ArrayList<LoyaltyProgram> lpList = new ArrayList<>();
        do {
            String getEnrolledLoyaltyPrograms = "select BRAND.LP_NAME,BRAND.LP_CODE from Brand,cust_wallet where BRAND.LP_CODE = CUST_WALLET.LP_CODE AND CUST_WALLET.WALLET_ID = '"+CustomerLanding.loggedInUser.getWalletId()+"'";

            // System.out.println(sqlCred);
            result = statement.executeQuery(getEnrolledLoyaltyPrograms);
            String lpCode = null;
            String lpName = null;
            while (result.next()) {
                lpCode = result.getString("LP_CODE");
                lpName = result.getString("LP_NAME");
                lpList.add(new LoyaltyProgram(lpCode, lpName));
            }
            System.out.println("\t\t These are your enrolled loyalty programs. \n Choose LP in which you would like to perform activity\n");

            for(int i = 0;i< lpList.size();i++){
                System.out.println(i+1+". "+lpList.get(i).getLpName());
            }
            System.out.println((lpList.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=lpList.size()){
                performRewardActivities(lpList.get(selection-1));
            } else if(selection == lpList.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    
    static void performRewardActivities(LoyaltyProgram loyaltyProgram) throws SQLException{
        int selection = 0;
        boolean flag = true;
        System.out.println("\nFetching Activity list from the loyalty program...");
        System.out.println("Select the activities you would like to perform");
        ArrayList<Activity> activities = new ArrayList<>();
    
        do {
            String getActivityNames = "select activities.act_name,activities.act_cat_code from RE_RULES,ACTIVITIES WHERE re_rules.act_cat_code = activities.act_cat_code and re_rules.lp_code='"+loyaltyProgram.getLpCode()+"'";
            result = statement.executeQuery(getActivityNames);
            
            System.out.println("\t\t List of available activities in the selected loyalty program\n");
            Activity activity;
            while (result.next()) {
                activity = new Activity(result.getString("ACT_CAT_CODE"), result.getString("ACT_NAME"));
                activities.add(activity);
            }
            for(int i = 0;i< activities.size();i++){
                System.out.println(i+1+". "+activities.get(i).getActivityName());
            }
            System.out.println((activities.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=activities.size()){
                
            } else if(selection == activities.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    static void DoActivity(Activity activity,LoyaltyProgram loyaltyProgram) throws SQLException{
        String getMultiplierTier = "select tier_mult from tier_status_update where lp_code = '"+loyaltyProgram.getLpCode()+"' and cust_id='"+CustomerLanding.loggedInUser.getUser_Id()+"'";
        result = statement.executeQuery(getMultiplierTier);
        int tierMult = result.getInt("tier_mult");
        if(activity.getActivityName().equals("Purchase") || activity.getActivityName().equals("Purchase")){
            System.out.println("Enter the purchase amount");
            int amount = sc.nextInt();
            sc.nextLine();
            System.out.println("Checking if you have any gift cards...");
            String getGiftCard = "select reward_cat_code, datavalue from customer_reward_log where customer_reward_log.expirydate > current_timestamp and REWARD_CAT_CODE = 'R2' and cust_id='"+CustomerLanding.loggedInUser.getUser_Id()+"'";
            String getGiftCardCount = "select count(*) as rowcount from customer_reward_log where customer_reward_log.expirydate > current_timestamp and REWARD_CAT_CODE = 'R2' and cust_id='"+CustomerLanding.loggedInUser.getUser_Id()+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(getGiftCard);
            if (!result.next()){
                System.out.println("You have no gift cards available..");
                System.out.println("Proceeding to transaction without gift cards...");
        
                System.out.println("Total value of purchase made is "+amount);
                String getMultiplier = "select points,re_rule_code from re_rules where lp_code = '"+loyaltyProgram.getLpCode()+"' and ACT_CAT_CODE='A01'";
                result = statement.executeQuery(getMultiplier);
                int pointMult = result.getInt("Points");
                String re_code = result.getString("re_rule_code");
                int pointsToBeAdded = (amount)*pointMult;



                PreparedStatement ps = CustomerLanding.conn
                .prepareStatement("INSERT INTO CUSTOMER_ACTIVITY_LOG(act_cat_code, cust_id,data_value,date_of_activity,re_rule_code,re_points) VALUES (?,?,?,?,?,?)");
                ps.setString(1, activity.getActivityCode());
                ps.setString(2, CustomerLanding.loggedInUser.getUser_Id());
                ps.setString(3, Integer.toString(amount));

                Date currentTime = calendar.getTime();
                long time = currentTime.getTime();
                ps.setTimestamp(4, new Timestamp(time));
                ps.setString(5, re_code);
                ps.setInt(6, pointsToBeAdded*tierMult);

                

                int id1 = ps.executeUpdate();
        
                System.out.println(id1);
        
                if (id1 > 0) {
                    System.out.println("Activity recorded");
                } else {
                    System.out.println("Row not found");
                }
            } else{
                int giftCardValue = 0;
                int countGift = 0;
                giftCardValue = Integer.parseInt(result.getString("datavalue"));
                result = statement.executeQuery(getGiftCardCount);
                countGift = result.getInt("rowcount");
                System.out.println("Found "+countGift+" gift cards with value of "+giftCardValue);
                System.out.println("\nWould you like to use your gift cards for the purchase?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int response = sc.nextInt();
                sc.nextLine();
                boolean flagError = false;
                if(response == 1){
                    do{
                        if(flagError){
                            System.out.println("You have chosen the wrong number of gift cards.\n Please choose a valid number");
                        }
                    System.out.println("Choose the number of gift cards you want to use for the purchase");
                    response = sc.nextInt();
                    sc.nextLine();
                    flagError = true;
                    } while(!(response <= countGift && response>=0));
                } else{
                    System.out.println("Proceeding to transaction without gift cards...");
                    response = 0;
                }
                    System.out.println("Total value of gift cards chosen is "+giftCardValue*response);
                    if(amount <= giftCardValue*response){
                        System.out.println("Your purchase has been made with the use of gift cards completely. \n Total bill 0$");
                    } else{
                        System.out.println("Total value of purchase made is "+(amount - giftCardValue*response));
                        String getMultiplier = "select points,re_rule_code from re_rules where lp_code = '"+loyaltyProgram.getLpCode()+"' and ACT_CAT_CODE='A01'";
                        result = statement.executeQuery(getMultiplier);
                        int pointMult = result.getInt("Points");
                        String re_code = result.getString("re_rule_code");
                        int pointsToBeAdded = (amount - giftCardValue*response)*pointMult;
                        PreparedStatement ps = CustomerLanding.conn
                        .prepareStatement("INSERT INTO CUSTOMER_ACTIVITY_LOG(act_cat_code, cust_id,data_value,date_of_activity,re_rule_code,re_points) VALUES (?,?,?,?,?,?)");
                        ps.setString(1, activity.getActivityCode());
                        ps.setString(2, CustomerLanding.loggedInUser.getUser_Id());
                        ps.setString(3, Integer.toString(amount));

                        Date currentTime = calendar.getTime();
                        long time = currentTime.getTime();
                        ps.setTimestamp(4, new Timestamp(time));
                        ps.setString(5, re_code);
                        ps.setInt(6, pointsToBeAdded*tierMult);

                        
    
                        int id1 = ps.executeUpdate();
                
                        System.out.println(id1);
                
                        if (id1 > 0) {
                            System.out.println("Activity recorded");
                        } else {
                            System.out.println("Row not found");
                        }
                    }
                }
               

        } else if(activity.getActivityName().equals("Leave a Review") || activity.getActivityName().equals("Leave a review")){
            System.out.println("Enter a review for the Brand's Program (50 characters)");
            String review = sc.nextLine();
            String getPointsForActivity = "select points,re_rule_code from re_rules where lp_code = '"+loyaltyProgram.getLpCode()+"' and ACT_CAT_CODE='A02'";
            result = statement.executeQuery(getPointsForActivity);
            int points = result.getInt("Points");
            String recode = result.getString("re_rule_code");
            PreparedStatement ps = CustomerLanding.conn
            .prepareStatement("INSERT INTO CUSTOMER_ACTIVITY_LOG(act_cat_code, cust_id,data_value,date_of_activity,re_rule_code,re_points) VALUES (?,?,?,?,?,?)");
            ps.setString(1, activity.getActivityCode());
            ps.setString(2, CustomerLanding.loggedInUser.getUser_Id());
            ps.setString(3, review);

            Date currentTime = calendar.getTime();
            long time = currentTime.getTime();
            ps.setTimestamp(4, new Timestamp(time));
            ps.setString(5, recode);
            ps.setInt(6, points*tierMult);

        } else if(activity.getActivityName().equals("Refer a Friend") || activity.getActivityName().equals("Refer a Friend")){
            System.out.println("Enter email of the person you are refering");
            String friendId = sc.nextLine();
            String getPointsForActivity = "select points,re_rule_code from re_rules where lp_code = '"+loyaltyProgram.getLpCode()+"' and ACT_CAT_CODE='A03'";
            result = statement.executeQuery(getPointsForActivity);
            int points = result.getInt("Points");
            String recode = result.getString("re_rule_code");
            PreparedStatement ps = CustomerLanding.conn
            .prepareStatement("INSERT INTO CUSTOMER_ACTIVITY_LOG(act_cat_code, cust_id,data_value,date_of_activity,re_rule_code,re_points) VALUES (?,?,?,?,?,?)");
            ps.setString(1, activity.getActivityCode());
            ps.setString(2, CustomerLanding.loggedInUser.getUser_Id());
            ps.setString(3, friendId);

            Date currentTime = calendar.getTime();
            long time = currentTime.getTime();
            ps.setTimestamp(4, new Timestamp(time));
            ps.setString(5, recode);
            ps.setInt(6, points*tierMult);
        }
           
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
