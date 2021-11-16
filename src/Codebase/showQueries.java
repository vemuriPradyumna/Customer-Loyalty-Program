package src.Codebase;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;


public class showQueries {
    
    public static Statement statement = null;
    public static ResultSet result = null;
    public static Scanner sc = null;
    public static Connection conn = null;

    public static void queryLandingInterface(Connection conn){

    showQueries.conn = conn;
    Scanner sc = new Scanner(System.in);
    int selection = 0;
    boolean main_flag = true;

    try {
        try {
            do{
                System.out.println("\n\nSTORED QUERIES\n\n");
                System.out.println("1. List all customers that are not part of Brand02’s program.");
                System.out.println("2. List customers that have joined a loyalty program but have not participated in any activity in that program (list the customerid and the loyalty program id).");
                System.out.println("3. List the rewards that are part of Brand01 loyalty program.");
                System.out.println("4. List all the loyalty programs that include refer a friend as an activity in at least one of their reward rules.");
                System.out.println("5. For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.");
                System.out.println("6. List customers of Brand01 that have redeemed at least twice.");
                System.out.println("7. All brands where total number of points redeemed overall is less than 500 points");
                System.out.println("8. For Customer C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021.");
                System.out.println("9. Go Back.");
                selection = sc.nextInt();
                sc.nextLine();
                switch (selection) {
                case 1:
                    System.out.println("List all customers that are not part of Brand02’s program.");
                    Query1(showQueries.conn);
                    break;
                case 2:
                    System.out.println("List customers that have joined a loyalty program but have not participated in any activity in that program.");
                    Query2(showQueries.conn);
                    break;
                case 3:
                    System.out.println("List the rewards that are part of Brand01 loyalty program.");
                    Query3(showQueries.conn);
                    break;
                case 4:
                    System.out.println("List all the loyalty programs that include refer a friend as an activity in at least one of their reward rules.");
                    Query4(showQueries.conn);
                    break;
                case 5:
                    System.out.println("For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.");
                    Query5(showQueries.conn);
                    break;
                case 6:
                    System.out.println("List customers of Brand01 that have redeemed at least twice.");
                    Query6(showQueries.conn);
                    break;
                case 7:
                    System.out.println("All brands where total number of points redeemed overall is less than 500 points");
                    Query7(showQueries.conn);
                    break;   
                case 8:
                    System.out.println("For Customer C0003, and Brand02, number of activities they have done in the period of 08/1/2021 and 9/30/2021.");     
                    Query8(showQueries.conn);
                    break;
                case 9:
                    System.out.println("Going back....");
                    main_flag = false;
                    break;    
                default:
                    System.out.println("You have entered an incorrect selection try again");
                }
            } while(main_flag);
        } finally {
            //close(showQueries.conn);
        }
    }

    catch (Exception e) {
        System.out.println("Failed to connect to DB");
        e.printStackTrace();
    }
    }


    public static void Query1(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement();
        //String sql = "SELECT DISTINCT customer_id FROM customer MINUS (SELECT DISTINCT customer_id FROM customer WHERE wallet_id IN (SELECT C.wallet_id FROM cust_wallet C, brand B WHERE C.lp_code = B.lp_code AND brand_id = 'Brand02'))";
        String sql = "select c.customer_id, c.customer_name from customer c where wallet_id NOT IN(select c.wallet_id from brand b, cust_wallet w,customer c where b.lp_code = w.lp_code  and c.wallet_id = w.wallet_id and b.brand_id = 'Brand02')";
        result = statement.executeQuery(sql);
        while(result.next()){
            System.out.println(result.getString("customer_id"));
        }
    }

    public static void Query2(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement();
        //String sql = "SELECT DISTINCT C.customer_id FROM customer C, cust_wallet C1 WHERE C.wallet_id = C1.wallet_id MINUS (SELECT C.customer_name FROM customer C WHERE C.wallet_id IN ( SELECT wallet_id FROM cust_wallet WHERE lp_code IN ( SELECT lp_code FROM brand WHERE brand_id IN ( SELECT brand_id FROM re_rules WHERE act_cat_code IN ( SELECT act_cat_code FROM customer_activity_log )))))";
        //String sql = "SELECT cust_id, lp_code FROM cust_wallet MINUS SELECT C.cust_id, C.lp_code FROM cust_wallet C WHERE C.cust_id IN (SELECT cust_id FROM customer_activity_log WHERE re_rule_code IN (SELECT re_rule_code FROM re_rules WHERE lp_code = C.lp_code ))";
        //String sql = "SELECT cust_id, lp_code FROM cust_wallet MINUS SELECT C.cust_id, C.lp_code FROM cust_wallet C WHERE C.cust_id IN (SELECT cust_id FROM customer_activity_log WHERE re_rule_code IN (SELECT re_rule_code FROM re_rules WHERE lp_code = C.lp_code ))";
        String sql = "SELECT DISTINCT cust_id, lp_code FROM cust_wallet MINUS SELECT DISTINCT cust_id, lp_code FROM customer_activity_log";
        result = statement.executeQuery(sql);
        while(result.next()){
              System.out.println("Customer ID: "+result.getString("cust_id")+"\n"+"Loyalty Program Code: "+result.getString("lp_code")+"\n");
        }  

    }

    public static void Query3(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement(); 
        //String sql = "SELECT reward_name FROM reward_type WHERE reward_cat_code IN ( SELECT reward_cat_code FROM rewards WHERE lp_code IN ( SELECT lp_code FROM brand WHERE brand_id = 'Brand01'))";
        String sql = "select rt.reward_name,r.REWARD_CAT_CODE, r.DATAVALUE, r.NO_INSTANCES from rewards r, brand b, reward_type rt where r.lp_code = b.lp_code and rt.reward_cat_code = r.reward_cat_code and b.brand_id = 'Brand01'";
        result = statement.executeQuery(sql);
        while(result.next()){
            System.out.println("\nReward name: "+result.getString("reward_name")+"\nReward value:"+result.getString("datavalue")+"\nNumber of Rewards available: "+result.getString("no_instances")+"\n");
        }
    }

    public static void Query4(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement(); 
        String sql = "SELECT lp_name FROM brand WHERE lp_code in (SELECT lp_code FROM re_rules WHERE act_cat_code IN (SELECT act_cat_code FROM activities WHERE act_name = 'Refer a Friend'))";
        result = statement.executeQuery(sql);
        while(result.next()){
            System.out.println("\n"+result.getString("lp_name")+"\n");
        }
    }

    public static void Query5(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement(); 
        String sql = "SELECT A.act_name, count(*) as count FROM customer_activity_log C INNER JOIN activities A ON C.act_cat_code = a.act_cat_code WHERE c.act_cat_code IN (SELECT act_cat_code FROM re_rules WHERE lp_code IN (SELECT lp_code FROM brand WHERE brand_id = 'Brand01')) AND c.lp_code = (SELECT lp_code FROM brand WHERE brand_id = 'Brand01') group by C.act_cat_code, a.act_name";
        result = statement.executeQuery(sql);
        while(result.next()){
            System.out.println(result.getString("act_name")+"\t\t"+result.getString("count"));
        }
    }

    public static void Query6(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement(); 
        String sql = "select crl.cust_id,c.customer_name from customer c, CUSTOMER_REWARD_LOG crl, brand b where c.customer_id = crl.cust_id and b.lp_code = crl.lp_code and b.brand_id = 'Brand01' group by crl.cust_id,c.customer_name having count(crl.cust_id) >=2";
        result = statement.executeQuery(sql);
        while(result.next()){
            System.out.println("Customer ID: "+result.getString("cust_id")+"\n"+"Customer Name: "+result.getString("customer_name")+"\n");
        }
    }

    public static void Query7(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement(); 
        String sql = "select b.BRAND_ID, b.brand_name from brand b, CUSTOMER_REWARD_LOG crl where crl.lp_code = b.lp_code group by b.brand_id,b.brand_name having SUM(crl.rr_points) < 500";
        result = statement.executeQuery(sql);
        while(result.next()){
            System.out.println("Brand ID: "+result.getString("brand_id")+"\n"+"Brand Name: "+result.getString("brand_name")+"\n");
        }
    }

    public static void Query8(Connection conn) throws SQLException{

        sc = new Scanner(System.in);
        showQueries.statement = conn.createStatement(); 
        String sql = "SELECT count(*) as count FROM customer_activity_log WHERE cust_id = 'C0003' AND re_rule_code IN ( SELECT re_rule_code FROM re_rules WHERE lp_code IN (SELECT lp_code from brand WHERE brand_id='Brand02') ) AND date_of_activity BETWEEN TO_DATE('08/01/2021', 'MM/DD/YYYY') AND TO_DATE('09/30/2021', 'MM/DD/YYYY')";
        result = statement.executeQuery(sql);
        int a;
        int b;
        result.next();
        a = result.getInt("count");
       /* ResultSet res = null;
        res = statement.executeQuery("SELECT count(*) as count FROM customer_reward_log WHERE cust_id = 'anisha' AND lp_code IN (SELECT lp_code from brand WHERE brand_id='Brand02') AND date_of_reward BETWEEN TO_DATE('08/01/2021', 'MM/DD/YYYY') AND TO_DATE('09/30/2021', 'MM/DD/YYYY')");
        res.next();
        b = res.getInt("count");*/
        int c = a;
        System.out.println("\nTotal number of activities done is: "+c);
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
