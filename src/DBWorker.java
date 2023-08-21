import java.io.*;
import java.sql.*;

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
        Connection connection = null;
        Statement statement = null;

        String fileName = "sql.txt";
        String data = "insert into coins (face_value, currency, country, year) values ('1', 'penny', 'United Kingdom', 1971);\n" +
                "insert into coins (face_value, currency, country, year) values ('5', 'pence', 'United Kingdom', 1968);\n" +
                "insert into coins (face_value, currency, country, year) values ('1', 'pound', 'United Kingdom', 1983);";

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();

            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(data);
            fileWriter.close();

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                int res = statement.executeUpdate(line);
            }

            ResultSet resultSet = statement.executeQuery("SELECT * FROM coins");
            while (resultSet.next()) {
                System.out.println(resultSet);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}