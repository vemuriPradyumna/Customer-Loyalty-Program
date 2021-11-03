import java.sql.*;
import java.util.Scanner;

public class Home {
    private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    private static final String user = "rrsallar";
    private static final String password = "abcd1234";

    public static Connection connection = null;
    public static Statement statement = null;
    public static ResultSet result = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int selection = 0;
        try {

            Class.forName("oracle.jdbc.OracleDriver");

            try {
                System.out.println("Inititalizing connection...");
                connection = DriverManager.getConnection(jdbcURL, user, password);
                statement = connection.createStatement();
                do{
                    System.out.println("\t\tHOME\n\n");
                    System.out.println("1. LOGIN");
                    System.out.println("2. SIGN UP ");
                    System.out.println("3. SHOW QUERIES");
                    System.out.println("4. EXIT");
                    selection = sc.nextInt();
                    switch (selection) {
                    case 1:
                        System.out.println("login page");
                        break;
                    case 2:
                        System.out.println("Sign up page");
                        break;
                    case 3:
                        System.out.println("Show queries");
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("You have entered an incorrect selection try again");
                    }
                } while(selection<=0 || selection>4);
            } finally {
                sc.close();
                close(result);
                close(statement);
                close(connection);
            }
        }

        catch (Throwable oops) {
            oops.printStackTrace();
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