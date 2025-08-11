package mypack;


	import javax.swing.*;
	import javax.swing.table.DefaultTableModel;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.Statement;

	public class DatabaseManagementApp1 extends JFrame {
	    private DefaultTableModel tableModel;
	    private JTable table;

	    public DatabaseManagementApp1() {
	        super("Database Management Program");

	        // Initialize the Swing components
	        tableModel = new DefaultTableModel();
	        table = new JTable(tableModel);
	        tableModel.addColumn("ID");
	        tableModel.addColumn("Name");
	        tableModel.addColumn("Age");

	        JScrollPane scrollPane = new JScrollPane(table);
	        JButton loadButton = new JButton("Load Data");

	        // Add components to the frame
	        setLayout(new BorderLayout());
	        add(scrollPane, BorderLayout.CENTER);
	        add(loadButton, BorderLayout.SOUTH);

	        // Load data from the database when the "Load Data" button is clicked
	        loadButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                loadDataFromDatabase();
	            }
	        });

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 300);
	        setLocationRelativeTo(null);
	    }

	    private void loadDataFromDatabase() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection connection = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/mydatabase",
	                "your_username",
	                "your_password"
	            );

	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT * FROM mytable");

	            // Clear existing data
	            tableModel.setRowCount(0);

	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                int age = resultSet.getInt("age");
	                tableModel.addRow(new Object[]{id, name, age});
	            }

	            resultSet.clone();
	            statement.clone();
	            connection.clone();
	        } catch (Exception e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	  public class DatabaseManagementApp1 {

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                DatabaseManagementApp1 app = new DatabaseManagementApp1();
	                app.setVisible(true);
	            }
	        });
	    }
	}


}
