import java.sql.*;
/**
 *
 * @author Isha
 */
// Acknowledgments: This example is a modification of code provided 
// by Dimitri Rakitine.

// Usage from command line on key.csc.ncsu.edu: 
// see instructions in FAQ
// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

//Note: If you run the program more than once, it will not be able to create the COFFEES table anew after the first run; 
//	you can remove the COFFEES tables between the runs by typing "drop table COFFEES;" in SQL*Plus.

public class firstExample {

    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    public static void main(String[] args) {
        try {
			System.out.println("Hello this is a test!!!");

// Load the driver. This creates an instance of the driver
// and calls the registerDriver method to make Oracle Thin
// driver available to clients.

//  Class.forName("oracle.jdbc.driver.OracleDriver"); older


        Class.forName("oracle.jdbc.OracleDriver");

	    String user = "svengal";	// For example, "jsmith"
	    String passwd = "abcd1234";	// Your 9 digit student ID number


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

		// Get a connection from the first driver in the
		// DriverManager list that recognizes the URL jdbcURL

		conn = DriverManager.getConnection(jdbcURL, user, passwd);

		// Create a statement object that will be sending your
		// SQL statements to the DBMS

		stmt = conn.createStatement();

			// Create the COFFEES table

		// Populate the COFFEES table

		stmt.executeUpdate("INSERT INTO COFFEES1 " +
			   "VALUES ('French_Roast_Decaf2', 49, 9.99, 0, 0)");

		// Get data from the COFFEES table

		rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES1");

		// Now rs contains the rows of coffees and prices from
		// the COFFEES table. To access the data, use the method
		// NEXT to access all rows in rs, one row at a time

		while (rs.next()) {
		    String s = rs.getString("COF_NAME");
		    float n = rs.getFloat("PRICE");
		    System.out.println(s + "   " + n);
		}

            } finally {
				System.out.println("Done done");
                close(rs);
                close(stmt);
                close(conn);
            }
        } catch(Throwable oops) {
			System.out.println("Error");
            oops.printStackTrace();
        }
    }

    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }
}



