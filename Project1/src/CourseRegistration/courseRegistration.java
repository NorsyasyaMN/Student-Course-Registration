package CourseRegistration;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class courseRegistration extends JFrame {

	private JPanel contentPane;
	private JTextField stdName;
	private JTextField stdMatricNum;
	private JTextField stdCourseName;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					courseRegistration frame = new courseRegistration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public courseRegistration() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 784, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Student Name", "Matric Number", "Course Name" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, Integer.class, String.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(92);
		table.getColumnModel().getColumn(1).setPreferredWidth(97);
		table.getColumnModel().getColumn(2).setPreferredWidth(81);
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBounds(347, 147, 366, 293);
		contentPane.add(table);
		table_update();

		JLabel lblNewLabel = new JLabel("STUDENT COURSE REGISTERATION");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(171, 63, 435, 35);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Student Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(54, 168, 120, 19);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Student Matric Number");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(54, 229, 163, 19);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Course Name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(54, 293, 150, 19);
		contentPane.add(lblNewLabel_3);

		stdName = new JTextField();
		stdName.setBounds(54, 198, 234, 20);
		contentPane.add(stdName);
		stdName.setColumns(10);

		stdMatricNum = new JTextField();
		stdMatricNum.setBounds(54, 259, 233, 20);
		contentPane.add(stdMatricNum);
		stdMatricNum.setColumns(10);

		stdCourseName = new JTextField();
		stdCourseName.setBounds(55, 321, 233, 20);
		contentPane.add(stdCourseName);
		stdCourseName.setColumns(10);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name = stdName.getText();
				String matricNum = stdMatricNum.getText();
				String courseName = stdCourseName.getText();

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mysql", "root", "");

					String sqlCreate = "CREATE TABLE IF NOT EXISTS stdcourseregister"
							+ "  (name           VARCHAR(20) NOT NULL,"
							+ "   matric            INTEGER NOT NULL PRIMARY KEY,"
							+ "   course          VARCHAR(20) NOT NULL)";

					Statement stmt = con.createStatement();
					stmt.execute(sqlCreate);

					PreparedStatement pst = con
							.prepareStatement("insert into stdcourseregister(name,matric,course)values(?,?,?)");
					pst.setString(1, name);
					pst.setString(2, matricNum);
					pst.setString(3, courseName);
					pst.executeUpdate();
					con.close();
					JOptionPane.showMessageDialog(contentPane, "Record Addedd");
					table_update();

					stdName.setText("");
					stdMatricNum.setText("");
					stdCourseName.setText("");
					stdName.requestFocus();

				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});

		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegister.setBounds(10, 371, 99, 27);
		contentPane.add(btnRegister);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(219, 371, 94, 27);
		contentPane.add(btnDelete);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.setBounds(119, 371, 87, 27);
		contentPane.add(btnEdit);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void table_update() {
		int c;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mysql", "root", "");

			PreparedStatement stmt = con.prepareStatement("select * from stdcourseregister");
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData Rss = rs.getMetaData();

			c = Rss.getColumnCount();

			DefaultTableModel Df = (DefaultTableModel) table.getModel();
			Df.setRowCount(0);

			while (rs.next()) {
				Vector v2 = new Vector();

				for (int a = 1; a <= c; a++) {
					v2.add(rs.getString("name"));
					v2.add(rs.getString("matric"));
					v2.add(rs.getString("course"));
				}

				Df.addRow(v2);

			}

		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(courseRegistration.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
