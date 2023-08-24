import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorker {

    private final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    public DBWorker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver has been loaded!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fillInTheCoinsTable() {

        String fileNameInsert = "sql_insert.txt";
        String dataInsert = "insert into coins (face_value, currency, country, year) values ('1', 'penny', 'United Kingdom', 1971);\n" +
                "insert into coins (face_value, currency, country, year) values ('5', 'pence', 'United Kingdom', 1968);\n" +
                "insert into coins (face_value, currency, country, year) values ('1', 'pound', 'United Kingdom', 1983);";

        String fileNameSelect = "sql_select.txt";
        String dataSelect = "SELECT * FROM coins";

        List<Coin> coins = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()){

            FileWriter fileWriterInsert = new FileWriter(fileNameInsert);
            fileWriterInsert.write(dataInsert);
            fileWriterInsert.close();

            FileWriter fileWriterSelect = new FileWriter(fileNameSelect);
            fileWriterSelect.write(dataSelect);
            fileWriterSelect.close();

            FileReader fileReaderInsert = new FileReader(fileNameInsert);
            BufferedReader bufferedReaderInsert = new BufferedReader(fileReaderInsert);
            String line;

            while ((line = bufferedReaderInsert.readLine()) != null) {
                int res = statement.executeUpdate(line);
            }

            FileReader fileReaderSelect = new FileReader(fileNameSelect);
            BufferedReader bufferedReaderSelect = new BufferedReader(fileReaderSelect);
            String select = bufferedReaderSelect.readLine();

            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                String face_value = resultSet.getString(2);
                String currency = resultSet.getString(3);
                String country = resultSet.getString(4);
                int year = resultSet.getInt(5);
                Coin coin = new Coin(face_value, currency, country, year);
                coins.add(coin);
            }

            for (Coin coin : coins) {
                System.out.println(coin.getFace_value() + " " + coin.getCurrency() + " " + coin.getCountry() + " " + coin.getYear());
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}