import java.sql.*;
import java.util.Scanner;
public class PlantInfo {

    public static void main(String[] args) throws SQLException {
        Driver driver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
        Scanner scanner = new Scanner(System.in);
        String dbName = "PlantInfoSource";
        Statement stmt = connectToDb(dbName);
        String retrieveTables = String.format("use %s; select name, create_date from sys.tables;", dbName);
        System.out.print("Displaying list of tables:\n");
        queryDB(stmt, retrieveTables, true);
        System.out.print("Enter desired table: "); String tableName = scanner.next();
        System.out.print("Enter desired plant: "); scanner.nextLine(); String plantName = scanner.nextLine();
        System.out.print("Enter desired watering period: "); int wateringPrd = scanner.nextInt();
        System.out.print("Enter desired sunlight level: "); scanner.nextLine(); String sunlightLevel = scanner.nextLine();
        String query = String.format("insert into %s values ('%s', '%d', '%s')",
                tableName, plantName, wateringPrd, sunlightLevel);
        queryDB(stmt, query, false);
        query = String.format("select %s, %s, %s from %s", "plantName", "wateringPrd", "sunlightLevel", tableName);
        queryDB(stmt, query, true);
    }

    private static void queryDB(Statement stmt, String query, boolean display) {
        ResultSet result;
        boolean executed;
        try {
            if(display){
                result = stmt.executeQuery(query);
                displayTable(result);
            }
            else{
                executed = stmt.execute(query);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void displayTable(ResultSet result) throws SQLException {
        ResultSetMetaData rsmd = result.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print("\t | \t");
                String columnValue = result.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
            }
            System.out.println("");
        }
    }

    public static Statement connectToDb(String dbName) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter username to access database %s: ", dbName);
        String username = scanner.next();
        System.out.printf("Enter password to access database %s: ", dbName);
        String password = scanner.next();
        String connectionUrl = "jdbc:sqlserver://plantinfo.database.windows.net:1433;databaseName=PlantInfoSource;" +
                "user=" + username + "@plantinfo;password=" + password;
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection.createStatement();
    }

}