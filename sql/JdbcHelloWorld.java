/* Instructions:
 * Download the latest Oracle JDBC driver "ojdbcX.jar", where X is the driver's version
 * Compile: javac -cp .:lib/ojdbcX.jar JdbcHelloWorld.java
 * Execute: java -cp .:lib/ojdbcX.jar JdbcHelloWorld
 *
 * Author: Rafael Angarita
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class JdbcLab {

  private static Connection connection = null;
  private static OracleDataSource ods = null;

  public static void main(String...args) {

    try  {
      Class.forName ("oracle.jdbc.OracleDriver");
    } catch(ClassNotFoundException e){
      e.printStackTrace();
    }

    try {
      ods = new OracleDataSource();
      ods.setURL("jdbc:oracle:thin:@server:port:ORCL");
      ods.setUser("username");
      ods.setPassword("password");
      connection = ods.getConnection();

      System.out.println("Connected to the db!");

      displayDepartment(connection);

    } catch(SQLException e) {
      e.printStackTrace();
    } finally {
      if(connection != null) {
        try {
          connection.close();
        } catch(SQLException ignore) {
          ignore.printStackTrace();
        }
      }
    }
  }

  private static void displayDepartment(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(
      "SELECT deptno, dname FROM dept"
    );
    while(result.next()) {
      int deptno = result.getInt("deptno");
      String dname = result.getString("dname");
      System.out.println("Department " + deptno + " has the name "
        + dname);
    }
  }

}
