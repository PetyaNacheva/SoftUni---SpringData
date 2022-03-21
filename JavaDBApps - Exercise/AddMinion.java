import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AddMinion {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "0538555");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);
        Scanner scanner = new Scanner(System.in);
        String [] minionInfo = scanner.nextLine().split(" ");

        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];
        String villianName = scanner.nextLine().split(" ")[1];

        int townId = getOrInsertTown(connection, minionTown);

        int villainId = getOrinsertVillains(connection, villianName);

        PreparedStatement insertMinion = connection.prepareStatement("Insert into  minions(name, age,town_id) values(?,?,?)");
                insertMinion.setString(1, minionName);
                insertMinion.setInt(2,minionAge);
                insertMinion.setInt(3, townId);
                insertMinion.executeUpdate();

                PreparedStatement getLastMinion = connection.prepareStatement("select id from minions order by id desc");
        ResultSet lastMinionSet = getLastMinion.executeQuery();
            lastMinionSet.next();
            int lastMinionId= lastMinionSet.getInt("id");
        PreparedStatement insertMinionsVillains = connection.prepareStatement("insert into minions_villains values(?,?)");
        insertMinionsVillains.setInt(1, lastMinionId);
        insertMinionsVillains.setInt(2, villainId);
        insertMinionsVillains.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villianName);
    }

    private static int getOrinsertVillains(Connection connection, String villainName) throws SQLException{

        PreparedStatement selectVillain = connection.prepareStatement("Select id from villains where name =?");
        selectVillain.setString(1, villainName);

        ResultSet villainSet = selectVillain.executeQuery();
        int villainId=0;
        if(!villainSet.next()) {
        PreparedStatement insertVillain = connection.prepareStatement("insert into villains (name, evilness_factor) values(?, ?)");

        insertVillain.setString(1, villainName);
        insertVillain.setString(2, "evil");
        insertVillain.executeUpdate();
            ResultSet newVillainSet = selectVillain.executeQuery();

            newVillainSet.next();
            villainId = newVillainSet.getInt("id");
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }else{
            villainId= villainSet.getInt("id");
        }
        return villainId;
    }

    private static int getOrInsertTown(Connection connection, String minionTown) throws SQLException {
        PreparedStatement selectTown = connection.prepareStatement("Select id from towns where name =?");
        selectTown.setString(1, minionTown);

        ResultSet townSet = selectTown.executeQuery();


        int townId = 0;
        if(!townSet.next()){
            PreparedStatement insertTown = connection.prepareStatement("Insert into towns(name) values(?);");

            insertTown.setString(1, minionTown);
            insertTown.executeUpdate();

            ResultSet newTownSet = selectTown.executeQuery();
            newTownSet.next();
            townId = newTownSet.getInt("id");
            System.out.printf("Town %s was added to the database.%n", minionTown);
        }else{
            townId = townSet.getInt("id");
        }
        return townId;
    }
}
