package Codebase;

import java.sql.*;

public class Inititlize {
    private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    private static final String user = "rrsallar";
    private static final String password = "abcd1234";

    public static Connection connection = null;
    public static void main(String[] args) {
        try {

            Class.forName("oracle.jdbc.OracleDriver");

            try {
                System.out.println("Inititalizing connection...");
                connection = DriverManager.getConnection(jdbcURL, user, password);
                Home.HomeUi(connection);
            } finally {
                close(connection);
            }
        }

        catch (Exception e) {
            System.out.println("Failed to connect to DB");
            e.printStackTrace();
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
