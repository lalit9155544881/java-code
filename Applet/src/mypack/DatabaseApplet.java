package mypack;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DatabaseApplet extends JApplet {
    private JTextArea resultTextArea;

    public void init() {
        resultTextArea = new JTextArea(10, 30);
        add(new JScrollPane(resultTextArea));
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydatabase",
                "your_username",
                "your_password"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mytable");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                resultTextArea.append("ID: " + id + ", Name: " + name + ", Age: " + age + "\n");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            resultTextArea.setText("Error: " + e.getMessage());
        }
    }
}

