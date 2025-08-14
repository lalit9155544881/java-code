import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataEntryGUI extends JFrame {
    private JPanel contentPane;
    private JTextField[] inputFields;
    private String[] columnNames = {"ID", "First Name", "Last Name", "Email"};

    public DataEntryGUI() {
        setTitle("Data Entry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 150);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Add input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));
        contentPane.add(inputPanel, BorderLayout.CENTER);

        inputFields = new JTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            JLabel label = new JLabel(columnNames[i] + ":");
            label.setBackground(Color.ORANGE);
            label.setOpaque(true);
            inputPanel.add(label);
            inputFields[i] = new JTextField();
            inputPanel.add(inputFields[i]);
        }

        // Add submit button
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get data from input fields
                String[] data = new String[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    data[i] = inputFields[i].getText();
                }

                // Insert data into database
                String url = "jdbc:mysql://localhost:3306/mydatabase";
                String user = "username";
                String password = "password";
                try {
                    Connection conn = DriverManager.getConnection(url, user, password);
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO mytable (id, firstName, lastName, email) VALUES (?, ?, ?, ?)");
                    for (int i = 0; i < columnNames.length; i++) {
                        stmt.setString(i + 1, data[i]);
                    }
                    stmt.executeUpdate();
                    stmt.close();
                    conn.close();

                    // Clear input fields
                    for (int i = 0; i < columnNames.length; i++) {
                        inputFields[i].setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(submitButton);
    }

    public static void main(String[] args) {
        DataEntryGUI frame = new DataEntryGUI();
        frame.setVisible(true);
    }
}
