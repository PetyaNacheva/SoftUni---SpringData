import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class GetMinionNames {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "0538555");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        Scanner scanner = new Scanner(System.in);
        int villainId= Integer.parseInt(scanner.nextLine());



        PreparedStatement statement = connection.prepareStatement("select name from villains where id=?");

        statement.setInt(1, villainId);


        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next()){
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            return;

        }
        System.out.println("Villain: "+ resultSet.getString("name"));

        PreparedStatement minionStatement = connection.prepareStatement("select  m.name, m.age from minions as m " +
                "join minions_villains mv on m.id = mv.minion_id " +
                "where mv.villain_id=?;");

        minionStatement.setInt(1, villainId);
        ResultSet minionSet = minionStatement.executeQuery();

        for (int i = 1; minionSet.next() ; i++) {
            String name = minionSet.getString("name");
            int age = minionSet.getInt("age");
            System.out.printf("%d. %s %d%n", i, name, age);
        }

    }
}
