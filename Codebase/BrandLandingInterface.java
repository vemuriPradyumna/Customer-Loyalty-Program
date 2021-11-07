package Codebase;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

import Codebase.POJO.Activity;
import Codebase.POJO.Reward;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BrandLandingInterface {
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;
    public static LoggedInUser loggedInUser = null;

    public static void BrandUi(Connection conn,LoggedInUser loggedInUser){
        try {
            boolean flag = true;
            BrandLandingInterface.loggedInUser = loggedInUser;
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
                    AddReRule();
                    break;
                case 3:
                    updateReRule();
                    break;
                case 4:
                    AddRRRule();
                    break; 
                case 5:
                    updateReRule();
                    break;  
                case 6:
                    validateLoyaltyProgram();
                    break;
                case 7:
                    System.out.println("Logging out...");
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
    static void addLoyaltyProgram() throws SQLException{
            int selection = 0;
            boolean flag = true;
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
                    flag = false;
                    break;
                default:
                    System.out.println("You have entered an incorrect selection try again");
                }
            } while (flag);
    }

    static void addRegularLoyalty() throws SQLException{
        int selection = 0;
        boolean flag = true;
        do {
            System.out.println("\t\t ADD REGULAR LOYALTY PROGRAM\n\n");
            System.out.println("1. Activity Types");
            System.out.println("2. Reward Types");
            System.out.println("3. Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            switch (selection) {
            case 1:
                // System.out.println("");
                addActivityTypes();
                break;
            case 2:
                addRewardTypes();
                break;
            case 3:
                System.out.println("Going back...");
                flag = false;
                break;
            default:
                System.out.println("You have entered an incorrect selection try again");
            }
        } while (flag);
    }

    static void addTieredLoyalty() throws SQLException{
        int selection = 0;
        boolean flag = true;
        do {
            System.out.println("\t\t ADD REGULAR LOYALTY PROGRAM\n\n");
            System.out.println("1. Tier Set up");
            System.out.println("2. Activity Types");
            System.out.println("3. Reward Types");
            System.out.println("4. Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            switch (selection) {
            case 1:
                tierSetup();
                break;
            case 2:
                addActivityTypes();
                break;
            case 3:
                addRewardTypes();
                break;
            case 4:
                System.out.println("Going back...");
                flag = false;
                break;
            default:
                System.out.println("You have entered an incorrect selection try again");
            }
        } while (flag);
    }

    static void addActivityTypes() throws SQLException{
        int selection = 0;
        boolean flag = true;
        ArrayList<Activity> activities = new ArrayList<>();
        do {
            String getActivityList = "select ACT_CAT_CODE,ACT_NAME from ACTIVITIES";
            // System.out.println(sqlCred);
            result = statement.executeQuery(getActivityList);
            Activity activity = null;
            while (result.next()) {
                activity = new Activity(result.getString("ACT_CAT_CODE"), result.getString("ACT_NAME"));
                activities.add(activity);
            }
            System.out.println("\t\t ADD ACTIVITY TYPE\n\n");
            for(int i = 0;i<activities.size();i++){
                System.out.println(i+1+". "+activities.get(i).getActivityName());
            }
            System.out.println((activities.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=activities.size()){
                System.out.println("Add activity to loyalty program");
            } else if(selection == activities.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    static void addRewardTypes() throws SQLException{
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
            System.out.println("\t\t ADD ACTIVITY TYPE\n\n");
            for(int i = 0;i<rewards.size();i++){
                System.out.println(i+1+". "+rewards.get(i).getRewardName());
            }
            System.out.println((rewards.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=rewards.size()){
                System.out.println("Add Reward to loyalty program");
            } else if(selection == rewards.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    static void tierSetup(){
        int selection = 0;
        boolean flag = true;
        do {
            System.out.println("\t\t TIER SETUP MENU\n\n");
            System.out.println("1. Setup");
            System.out.println("2. Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            switch (selection) {
            case 1:
                setupProcess();
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

    static void setupProcess(){
        System.out.println("How many tiers whould you like to have in your loyalty program?");
        int tierNum = sc.nextInt();
        sc.nextLine();
        String[] tierNames = new String[3];
        String[] multiplier  = new String[3];
        String[] pointsRequired = new String[3];
        for(int i=0;i<tierNum;i++){
            System.out.println("Enter the name of tier "+i+1);
            tierNames[i] = sc.nextLine();
        }
        for(int i=0;i<tierNum;i++){
            System.out.println("Enter the point multiplier for tier "+i+1);
            multiplier[i] = sc.nextLine();
        }
        for(int i=0;i<tierNum;i++){
            System.out.println("Enter the points required to enter tier "+i+1);
            pointsRequired[i] = sc.nextLine();
        }

    }

    static void AddReRule(String activityCode) throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Adding Activity Code to Brand \n ");
        do {
            if (firstCheck){
                System.out.println("RE code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Earning Rule Code \n");
            String RECode = sc.nextLine();
            System.out.println("Enter version number");
            int RECodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RE_RULE_CODE,RULE_VERSION from RE_RULES where RE_RULE_CODE= '"+RECode+"' AND RULE_VERSION = '"+RECodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Activity Category Code \n");
        String ACCode = sc.nextLine();
        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRewarded = sc.nextInt();
        sc.nextLine();

    }

    static void AddReRule() throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Adding Reward Earning rule \n ");
        do {
            if (firstCheck){
                System.out.println("RE code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Earning Rule Code \n");
            String RECode = sc.nextLine();
            System.out.println("Enter version number");
            int RECodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RE_RULE_CODE,RULE_VERSION from RE_RULES where RE_RULE_CODE= '"+RECode+"' AND RULE_VERSION = '"+RECodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Activity Category Code \n");
        String ACCode = sc.nextLine();
        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRewarded = sc.nextInt();
        sc.nextLine();


    }

    static void updateReRule() throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Updating Reward Earning rule \n ");
        do {
            if (firstCheck){
                System.out.println("RE code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Earning Rule Code you want to update \n");
            String RECode = sc.nextLine();
            System.out.println("Enter the new version number");
            int RECodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RE_RULE_CODE,RULE_VERSION from RE_RULES where RE_RULE_CODE= '"+RECode+"' AND RULE_VERSION = '"+RECodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Activity Category Code \n");
        String ACCode = sc.nextLine();
        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRewarded = sc.nextInt();
        sc.nextLine();


    }

    static void AddRRRule() throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Adding Reward Redeeming rule \n ");
        do {
            if (firstCheck){
                System.out.println("RR code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Redeeming Rule Code \n");
            String RRCode = sc.nextLine();
            System.out.println("Enter version number");
            int RRCodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RR_RULE_CODE,RULE_VERSION from RR_RULES where RE_RULE_CODE= '"+RRCode+"' AND RULE_VERSION = '"+RRCodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Reward Category Code \n");
        String rCCode = sc.nextLine();
        System.out.println(" Enter Points required to redeem the reward \n");
        int pointsRequired = sc.nextInt();
        sc.nextLine();


    }

    static void updateRRRule() throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Updating Reward Reddeming rule \n ");
        do {
            if (firstCheck){
                System.out.println("RR code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Redeeming Rule Code you want to update \n");
            String RRCode = sc.nextLine();
            System.out.println("Enter the new version number");
            int RRCodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RR_RULE_CODE,RULE_VERSION from RR_RULES where RE_RULE_CODE= '"+RRCode+"' AND RULE_VERSION = '"+RRCodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Activity Category Code \n");
        String rCCode = sc.nextLine();
        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRequired = sc.nextInt();
        sc.nextLine();


    }

    static void validateLoyaltyProgram(){
        System.out.println("The Loyalty program is valid");
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
