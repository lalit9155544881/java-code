import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableDDB extends JFrame {
    JPanel contentPane;

    public TableDDB() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","database","3435");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM table1");

            ResultSetMetaData rm = rs.getMetaData();
            int columnCount = rm.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i-1] = rm.getColumnName(i);
            }
            Object[][] data = new Object[100][columnCount];
            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[rowCount][i-1] = rs.getObject(i);
                }
                rowCount++;
            }
            Object[][] trimmedData = new Object[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    trimmedData[i][j] = data[i][j];
                }
            }
            JTable table = new JTable(trimmedData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            contentPane.add(scrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        TableDDB frame = new TableDDB();
        frame.setVisible(true);
    }
}
