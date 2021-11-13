package Codebase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Codebase.POJO.Activity;
import Codebase.POJO.LoggedInBrand;
import Codebase.POJO.LoggedInUser;
import Codebase.POJO.Reward;

public class BrandLandingInterface {
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;
    public static LoggedInBrand loggedInBrand = null;

    public static void BrandUi(Connection conn,LoggedInBrand loggedInBrand){
        try {
            boolean flag = true;
            BrandLandingInterface.loggedInBrand = loggedInBrand;
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
                    if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                        System.out.println("The brand has not registered in a loyalty program yet! \n");
                        System.out.println("Please add a loyalty program and try again \n");
                    } else{
                        addActivityTypes();
                    }
                    break;
                case 3:
                    if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                        System.out.println("The brand has not registered in a loyalty program yet! \n");
                        System.out.println("Please add a loyalty program and try again \n");
                    } else{
                        updateReRule();
                    }
                    
                    break;
                case 4:
                    if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                        System.out.println("The brand has not registered in a loyalty program yet! \n");
                        System.out.println("Please add a loyalty program and try again \n");
                    } else{
                        addRewardTypes();
                    }
                    break; 
                case 5:
                    if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                        System.out.println("The brand has not registered in a loyalty program yet! \n");
                        System.out.println("Please add a loyalty program and try again \n");
                    } else{
                        updateRrRule();
                    }
                    break;  
                case 6:
                    if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                        System.out.println("The brand has not registered in a loyalty program yet! \n");
                        System.out.println("Please add a loyalty program and try again \n");
                    } else{
                        if(validateLoyaltyProgram()){
                            PreparedStatement ps = BrandLandingInterface.conn
                            .prepareStatement("UPDATE BRAND SET ACTIVE_FLAG = ? WHERE BRAND_ID = ?");
                            ps.setInt(1, 1);
                            ps.setString(2, BrandLandingInterface.loggedInBrand.getBrandId());
                        
                            int id1 = ps.executeUpdate();

                            System.out.println(id1);

                            if (id1 > 0) {
                                System.out.println("Loyalty program Activated");
                            } else {
                                System.out.println("Row not found");
                            }

                        } else{
                            System.out.println("Invalid Loyalty program. Please update it again..");
                        }
                    }
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
    //add loyalty program BEGIN

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
        System.out.println("What would you like to name your Loyalty program?");
        String lpName = sc.nextLine();
        String lpCode = null;
        Boolean firstCheck = false;
        Boolean loopBreak = true;
        do {
            if (firstCheck){
                System.out.println("Loyalty program code already exists \nPlease select a new one\n\n");
            }
            System.out.println("Choose your Loyalty program code: \n");
            lpCode = sc.nextLine();
            System.out.println(lpCode);
            String sql_check = "Select lp_code from brand where brand_id= '"+BrandLandingInterface.loggedInBrand.getBrandId()+"'";
            result = statement.executeQuery(sql_check);
            result.next();
            int foo = result.getObject("LP_CODE") != null ? result.getInt("LP_CODE") : -1;
            if(foo == -1){
                loopBreak = false;
            }
            firstCheck = true;
        } while (loopBreak);
        BrandLandingInterface.loggedInBrand.setIsTiered(0);
        BrandLandingInterface.loggedInBrand.setLpCode(lpCode);
        BrandLandingInterface.loggedInBrand.setLpName(lpName);

        PreparedStatement ps = BrandLandingInterface.conn
        .prepareStatement("UPDATE BRAND SET LP_CODE=?, LP_NAME=?, ISTIERED = ? WHERE BRAND_ID = ?");
        ps.setString(1, lpCode);
        ps.setString(2, lpName);
        ps.setInt(3, 0);
        ps.setString(4, BrandLandingInterface.loggedInBrand.getBrandId());
     
        int id1 = ps.executeUpdate();

        System.out.println(id1);

        if (id1 > 0) {
            System.out.println("Loyalty program updated");
        } else {
            System.out.println("Row not found");
        }

        // int selection = 0;
        // boolean flag = true;
        // do {
        //     System.out.println("\t\t ADD REGULAR LOYALTY PROGRAM\n\n");
        //     System.out.println("1. Activity Types");
        //     System.out.println("2. Reward Types");
        //     System.out.println("3. Go Back");
        //     selection = sc.nextInt();
        //     sc.nextLine();
        //     switch (selection) {
        //     case 1:
        //         // System.out.println("");
        //         addActivityTypes();
        //         break;
        //     case 2:
        //         addRewardTypes();
        //         break;
        //     case 3:
        //         System.out.println("Going back...");
        //         flag = false;
        //         break;
        //     default:
        //         System.out.println("You have entered an incorrect selection try again");
        //     }
        
        // } while (flag);
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
                if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                    System.out.println("The brand has not registered in a loyalty program yet! \n");
                    System.out.println("Please add a loyalty program and try again \n");
                } else{
                    addActivityTypes();
                }
                break;
            case 3:
                if(BrandLandingInterface.loggedInBrand.getLpCode()==null ||  BrandLandingInterface.loggedInBrand.getLpCode().isEmpty()  ){
                    System.out.println("The brand has not registered in a loyalty program yet! \n");
                    System.out.println("Please add a loyalty program and try again \n");
                } else{
                    addRewardTypes();
                }
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
                AddReRule(activities.get(selection-1));
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
            result = statement.executeQuery(getRewardsList);
            Reward reward = null;
            while (result.next()) {
                reward = new Reward(result.getString("REWARD_CAT_CODE"), result.getString("REWARD_NAME"));
                rewards.add(reward);
            }
            System.out.println("\t\t ADD REWARD TYPE\n\n");
            for(int i = 0;i<rewards.size();i++){
                System.out.println(i+1+". "+rewards.get(i).getRewardName());
            }
            System.out.println((rewards.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=rewards.size()){
                AddRRRule(rewards.get(selection-1));
            } else if(selection == rewards.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
    }

    static void tierSetup() throws SQLException{
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


    static void setupProcess() throws SQLException{
        System.out.println("What would you like to name your Loyalty program?");
        String lpName = sc.nextLine();
        String lpCode = null;
        Boolean firstCheck = false;
        Boolean loopBreak = true;
        do {
            if (firstCheck){
                System.out.println("Loyalty program code already exists \nPlease select a new one\n\n");
            }
            System.out.println("Choose your Loyalty program code: \n");
            lpCode = sc.nextLine();
            System.out.println(lpCode);
            String sql_check = "Select lp_code from brand where brand_id= '"+BrandLandingInterface.loggedInBrand.getBrandId()+"'";
            result = statement.executeQuery(sql_check);
            result.next();
            int foo = result.getObject("LP_CODE") != null ? result.getInt("LP_CODE") : -1;
            if(foo == -1){
                loopBreak = false;
            }
            firstCheck = true;
        } while (loopBreak);

        System.out.println("How many tiers whould you like to have in your loyalty program?");
        int tierNum = sc.nextInt();
        sc.nextLine();
        String[] tierNames = new String[3];
        int[] multiplier  = new int[3];
        int[] pointsRequired = new int[3];
        for(int i=0;i<tierNum;i++){
            System.out.println("Enter the name of tier "+i+1);
            tierNames[i] = sc.nextLine();
        }
        for(int i=0;i<tierNum;i++){
            System.out.println("Enter the point multiplier for tier "+i+1);
            multiplier[i] = sc.nextInt();
            sc.nextLine();
        }
        for(int i=0;i<tierNum;i++){
            System.out.println("Enter the points required to enter tier "+i+1);
            pointsRequired[i] = sc.nextInt();
            sc.nextLine();
        }

        PreparedStatement ps = BrandLandingInterface.conn
        .prepareStatement("UPDATE BRAND SET LP_CODE=?, LP_NAME=?, TIER1=?, TIER2 = ?,TIER3=?, MULT1=?, MULT2=?, MULT3=?, POINTS_REQ_TIER1=?, POINTS_REQ_TIER2=?, POINTS_REQ_TIER3=?, ISTIERED = ? WHERE BRAND_ID = ?");
        ps.setString(1, lpCode);
        ps.setString(2, lpName);
        ps.setString(3, tierNames[0]);
        ps.setString(4, tierNames[1]);
        ps.setString(5, tierNames[2]);
        ps.setInt(6, multiplier[0] );
        ps.setInt(7, multiplier[1] );
        ps.setInt(8, multiplier[2] );
        ps.setInt(9, pointsRequired[0]);
        ps.setInt(10, pointsRequired[1]);
        ps.setInt(11, pointsRequired[2]);
        ps.setInt(12, 1);
        ps.setString(13, BrandLandingInterface.loggedInBrand.getBrandId());
        int id1 = ps.executeUpdate();

        System.out.println(id1);

        if (id1 > 0) {
            System.out.println("Loyalty program updated");
        } else {
            System.out.println("Row not found");
        }

        BrandLandingInterface.loggedInBrand.setIsTiered(1);
        BrandLandingInterface.loggedInBrand.setLpCode(lpCode);
        BrandLandingInterface.loggedInBrand.setLpName(lpName);

    }

    //add loyalty program END
    
    static void AddReRule(Activity activity) throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Adding Activity "+activity.getActivityName() +" to Brand \n ");
        String RECode = null;
        int RECodeVersion = 0;
        do {
            if (firstCheck){
                System.out.println("RE code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Earning Rule Code \n");
            RECode = sc.nextLine();
            System.out.println("Enter version number");
            RECodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RE_RULE_CODE,RULE_VERSION from RE_RULES where RE_RULE_CODE= '"+RECode+"' AND RULE_VERSION = '"+RECodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRewarded = sc.nextInt();
        sc.nextLine();

        PreparedStatement ps = BrandLandingInterface.conn
        .prepareStatement("INSERT INTO RE_RULES (RE_RULE_CODE, LP_CODE,ACT_CAT_CODE,RULE_VERSION,POINTS,ACTIVITY_NAME) VALUES (?,?,?,?,?,?);");

        ps.setString(1, RECode);
        ps.setString(2, BrandLandingInterface.loggedInBrand.getBrandId());
        ps.setString(3, activity.getActivityCode());
        ps.setInt(4, RECodeVersion);
        ps.setInt(5, pointsRewarded);
        ps.setString(6, activity.getActivityName());
        int id1 = ps.executeUpdate();

        System.out.println(id1);

        if (id1 > 0) {
            System.out.println("RE Rule code updated");
        } else {
            System.out.println("Info not added");
        }


    }
    
    static void AddRRRule(Reward reward) throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Adding Reward" + reward.getRewardName()+" rule to brand "+BrandLandingInterface.loggedInBrand.getBrandId()+" \n ");
        String RRCode = null;
        int RRCodeVersion = 0;
        do {
            if (firstCheck){
                System.out.println("RR code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Redeeming Rule Code \n");
            RRCode = sc.nextLine();
            System.out.println("Enter version number");
            RRCodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RR_RULE_CODE,RULE_VERSION from RR_RULES where RE_RULE_CODE= '"+RRCode+"' AND RULE_VERSION = '"+RRCodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);
        System.out.println(" Enter Points required to redeem the reward \n");
        int pointsRequired = sc.nextInt();
        sc.nextLine();

        PreparedStatement ps = BrandLandingInterface.conn
        .prepareStatement("INSERT INTO RR_RULES (RR_RULE_CODE, LP_CODE,REWARD_CAT_CODE,RULE_VERSION,POINTS,REWARD_NAME) VALUES (?,?,?,?,?,?);");

        ps.setString(1, RRCode);
        ps.setString(2, BrandLandingInterface.loggedInBrand.getLpCode());
        ps.setString(3, reward.getRewardCategory());
        ps.setInt(4, RRCodeVersion);
        ps.setInt(5, pointsRequired);
        ps.setString(6, reward.getRewardName());
        int id1 = ps.executeUpdate();

        System.out.println(id1);

        if (id1 > 0) {
            System.out.println("RR Rule code updated");
        } else {
            System.out.println("Info not added");
        }


    }

    static void updateRrRule() throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Updating Reward Reddeming rule \n ");
        String RRCode = null;
        int RRCodeVersion = 0;
        do {
            if (firstCheck){
                System.out.println("RR code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Redeeming Rule Code you want to update \n");
            RRCode = sc.nextLine();
            System.out.println("Enter the new version number");
            RRCodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RR_RULE_CODE,RULE_VERSION from RR_RULES where RR_RULE_CODE= '"+RRCode+"' AND RULE_VERSION = '"+RRCodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);

        System.out.println("Choose new reward code");
        int selection = 0;
        boolean flag = true;
        ArrayList<Reward> rewards = new ArrayList<>();
        Reward rewardChosen = null;
        do {
            String getRewardsList = "select REWARD_CAT_CODE,REWARD_NAME from REWARDS_TYPE";
            result = statement.executeQuery(getRewardsList);
            Reward reward = null;
            while (result.next()) {
                reward = new Reward(result.getString("REWARD_CAT_CODE"), result.getString("REWARD_NAME"));
                rewards.add(reward);
            }
            System.out.println("\t\t ADD REWARD TYPE\n\n");
            for(int i = 0;i<rewards.size();i++){
                System.out.println(i+1+". "+rewards.get(i).getRewardName());
            }
            System.out.println((rewards.size()+1)+". Go Back");
            selection = sc.nextInt();
            sc.nextLine();
            if(selection>0 && selection<=rewards.size()){
                rewardChosen = rewards.get(selection-1);
            } else if(selection == rewards.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);
        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRequired = sc.nextInt();
        sc.nextLine();

        
        PreparedStatement ps = BrandLandingInterface.conn
        .prepareStatement("INSERT INTO RR_RULES (RR_RULE_CODE, LP_CODE,REWARD_CAT_CODE,RULE_VERSION,POINTS,REWARD_NAME) VALUES (?,?,?,?,?,?);");

        ps.setString(1, RRCode);
        ps.setString(2, BrandLandingInterface.loggedInBrand.getLpCode());
        ps.setString(3, rewardChosen.getRewardCategory());
        ps.setInt(4, RRCodeVersion);
        ps.setInt(5, pointsRequired);
        ps.setString(6, rewardChosen.getRewardName());
        int id1 = ps.executeUpdate();

        System.out.println(id1);

        if (id1 > 0) {
            System.out.println("RR Rule code updated");
        } else {
            System.out.println("Info not added");
        }


    }

    static void updateReRule() throws SQLException{
        boolean firstCheck = false;
        System.out.println(" Updating Reward Earning rule \n ");
        String ReCode = null;
        int ReCodeVersion = 0;
        do {
            if (firstCheck){
                System.out.println("RE code and Version combination already exists \nPlease select different ones\n\n");
            }
            System.out.println(" Enter Reward Earning Rule Code you want to update \n");
            ReCode = sc.nextLine();
            System.out.println("Enter the new version number");
            ReCodeVersion = sc.nextInt();
            sc.nextLine();
            String sql_check = "Select RE_RULE_CODE,RULE_VERSION from RE_RULES where RE_RULE_CODE= '"+ReCode+"' AND RULE_VERSION = '"+ReCodeVersion+"'";
            // System.out.println(sqlCred);
            result = statement.executeQuery(sql_check);
        } while (result.next() == true);

        System.out.println("Choose new Activity code");
        int selection = 0;
        boolean flag = true;
        ArrayList<Activity> activities = new ArrayList<>();
        Activity activityChosen = null;
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
                activityChosen = activities.get(selection-1);
            } else if(selection == activities.size()+1){
                flag = false;
            } else{
                System.out.println("Wrong selection! Try again! \n");
            }
        } while (flag);

        System.out.println(" Enter Points Earned by performing activity \n");
        int pointsRewarded = sc.nextInt();
        sc.nextLine();
        
        PreparedStatement ps = BrandLandingInterface.conn
        .prepareStatement("INSERT INTO RE_RULES (RE_RULE_CODE, LP_CODE,ACT_CAT_CODE,RULE_VERSION,POINTS,ACTIVITY_NAME) VALUES (?,?,?,?,?,?);");

        ps.setString(1, ReCode);
        ps.setString(2, BrandLandingInterface.loggedInBrand.getBrandId());
        ps.setString(3, activityChosen.getActivityCode());
        ps.setInt(4, ReCodeVersion);
        ps.setInt(5, pointsRewarded);
        ps.setString(6, activityChosen.getActivityName());
        int id1 = ps.executeUpdate();

        System.out.println(id1);

        if (id1 > 0) {
            System.out.println("RE Rule code updated");
        } else {
            System.out.println("Info not added");
        }

    }

    static boolean validateLoyaltyProgram() throws SQLException{
        System.out.println("Initializing Loyalty program validation...");
        String vaidationSQL = "SELECT  LP_CODE, LP_NAME, TIER1, TIER2, TIER3, MULT1, MULT2, MULT3, POINTS_REQ_TIER1, POINTS_REQ_TIER2, POINTS_REQ_TIER3, ISTIERED  WHERE BRAND_ID = '"+BrandLandingInterface.loggedInBrand.getBrandId()+"'";
        // System.out.println(sqlCred);
        result = statement.executeQuery(vaidationSQL);
        result.next();
        int isTiered = result.getInt("ISTIERED");
        if(isTiered == 0){
            return true;
        }else if(isTiered == 1){
            int numTiers = 0;
            String tier3 = result.getString("TIER3");
            int foo = result.getObject("LP_CODE") != null ? result.getInt("LP_CODE") : -1;
            if(foo == -1){
                numTiers = 2;
            } else{
                numTiers = 3;
            }
            int[] multiplier  = new int[3];
            int[] pointsRequired = new int[3];
            int mult1,mult2,mult3,point1,point2,point3;
            if(numTiers == 3){
                multiplier[0] = result.getInt("MULT1");
                multiplier[1] = result.getInt("MULT2");
                multiplier[2] = result.getInt("MULT3");
                pointsRequired[0] = result.getInt("POINTS_REQ_TIER1");
                pointsRequired[1] = result.getInt("POINTS_REQ_TIER2");
                pointsRequired[2] = result.getInt("POINTS_REQ_TIER3");
                boolean mult = false;
                boolean points = false;
                int i=0;
                for (i = 0; i < multiplier.length; i++); { 
                    if (multiplier[i] < multiplier[i + 1]) {
                        mult = true;
                    }
                    else {
                        mult =  false;
                    }
                }

                for (i = 0; i < pointsRequired.length; i++); { 
                    if (pointsRequired[i] < pointsRequired[i + 1]) {
                        points = true;
                    }
                    else {
                        points =  false;
                    }
                }

                return (mult && points);

            } else{
                mult1 = result.getInt("MULT1");
                mult2 = result.getInt("MULT2");
                
                point1 = result.getInt("POINTS_REQ_TIER1");
                point2 = result.getInt("POINTS_REQ_TIER2");
                if(mult1 < mult2 && point1 < point2){
                   return true;
                } else{
                    return false;
                }
            }

            
        }
        return false;
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
