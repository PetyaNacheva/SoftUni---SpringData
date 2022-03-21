import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "0538555");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);



        PreparedStatement statement = connection.prepareStatement("select name, count( distinct mv.minion_id) as minion_count from villains as v " +
                "join minions_villains mv on v.id = mv.villain_id " +
                "group by mv.villain_id " +
                "having minion_count>15 " +
                "order by minion_count desc;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            String villainName = resultSet.getString("name");
            Integer minionCount = resultSet.getInt("minion_count");

            System.out.println(villainName + " "+ minionCount);
        }
    }
}
