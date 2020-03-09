import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;

public class PlantInfo {

    public static void main(String[] args) throws SQLException {

        String dbName = "generalPlantInformation";
        String plantName = "Parlor Palm";
        int wateringPrd = 14;
        String sunlightLevel = "Medium Indirect";
        String query = String.format("insert into %s values ('%s', '%s', '%s')", dbName, plantName, wateringPrd, sunlightLevel);

//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Driver driver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
//        DriverManager.registerDriver(driver);
        try {
            String myUrl = "jdbc:sqlserver://plantinfo.database.windows.net:1433;database=PlantInfoSource;user=wluh@" +
                    "plantinfo;password=Swift123!;encrypt=true;trustServerCertificate=false;hostNameInCer" +
                    "tificate=*.database.windows.net;loginTimeout=30;";
            myUrl = "jdbc:sqlserver://plantinfo.database.windows.net:1433;databaseName=PlantInfoSource;user=wluh" +
            "@plantinfo;password=Swift123!";
            Connection connection = DriverManager.getConnection(myUrl);
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//
//    public static void initJdbc() throws SQLException {
//        Driver driver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
//        DriverManager.registerDriver(driver);
//    }

}