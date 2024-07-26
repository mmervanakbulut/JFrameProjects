package worldCities;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Font;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Cties extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableCities;

	
	DefaultTableModel model;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblCityName;
	private JLabel lblDistrict;
	private JTextField textCityName;
	private JTextField textDistrict;
	private JLabel lblCountryCode;
	private JTextField textCountryCode;
	private JLabel lblPopulation;
	private JTextField textPopulation;
	private JButton btnAdd;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cties frame = new Cties();
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
	
	
	public Cties() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Aranacak Değer");
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchKey = textField.getText();
				TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(model);
				tableCities.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchKey));
			
			}
		});
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblCityName = new JLabel("Şehir İsmi");
		contentPane.add(lblCityName);
		
		textCityName = new JTextField();
		contentPane.add(textCityName);
		textCityName.setColumns(10);
		
		lblDistrict = new JLabel("Bölge");
		contentPane.add(lblDistrict);
		
		textDistrict = new JTextField();
		contentPane.add(textDistrict);
		textDistrict.setColumns(10);
		
		lblCountryCode = new JLabel("Ülke Kodu");
		contentPane.add(lblCountryCode);
		
		textCountryCode = new JTextField();
		contentPane.add(textCountryCode);
		textCountryCode.setColumns(10);
		
		lblPopulation = new JLabel("Nüfus");
		contentPane.add(lblPopulation);
		
		textPopulation = new JTextField();
		contentPane.add(textPopulation);
		textPopulation.setColumns(10);
		
		btnAdd = new JButton("Ekle");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection connection = null;
				DbHelper dbHelper = new DbHelper();
				PreparedStatement statement = null;
				
				try {
					connection = dbHelper.getConnection();
					
					String sqlString = "insert into city (Name, CountryCode, District, Population) values(?,?,?,?)";
					statement = connection.prepareStatement(sqlString);
					statement.setString(1, textCityName.getText());
					statement.setString(2, textCountryCode.getText());
					statement.setString(3, textDistrict.getText());
					statement.setInt(4, Integer.valueOf(textPopulation.getText()));
					int result = statement.executeUpdate();
					System.out.println(result);
					populateTabe();
				} catch (SQLException exception) {
					dbHelper.showErrorMessage(exception);
				}finally {
					try {
						statement.close();
						connection.close();
					} catch (SQLException e1) {
					}
				}
				
				
				
				
				
				
				
				
			}
		});
		contentPane.add(btnAdd);
		
		
		
		tableCities = new JTable();
		tableCities.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tableCities.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Name", "CountryCode", "District", "Population"
			}
		));
		
		
		contentPane.add(tableCities);
		
		populateTabe();
		
		
	}
	
	public void populateTabe() {
		model = (DefaultTableModel)tableCities.getModel();
		model.setRowCount(0);
		try {
			ArrayList<City> cities = getCities();
			for (City city : cities) {
				Object[] rowObjects = {city.getId(),city.getName(),
						city.getCountryCode(), city.getDistrict(),city.getPopulation()};
				model.addRow(rowObjects);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<City> getCities() throws SQLException{
		Connection connection = null;
		DbHelper helper = new DbHelper();
		Statement statement = null;
		ResultSet resultSet = null;
		
		ArrayList<City> cities = null;
		
		try {
			connection = helper.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from city");
			cities = new ArrayList<City>();	
			while(resultSet.next()) {
				cities.add(new City(
					resultSet.getInt("Id"),
					resultSet.getString("Name"),
					resultSet.getString("CountryCode"),
					resultSet.getString("District"),
					resultSet.getInt("Population")
				));
			}
		} catch (SQLException exception) {
			helper.showErrorMessage(exception);
		}finally {
			statement.close();
			connection.close();
		}
	
		return cities;
	}

}
