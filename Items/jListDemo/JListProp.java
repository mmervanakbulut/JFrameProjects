package jListDemo;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JListProp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JListProp frame = new JListProp();
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
	public JListProp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		JList lstStudent = new JList();
		
		DefaultListModel model = new DefaultListModel();
		lstStudent.setModel(model);
		
		
		
		
		JLabel lblStudentName = new JLabel("Öğrenci adı: ");
		lblStudentName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblStudentName);
		
		JLabel lblMessage = new JLabel("");
		
		
		textName = new JTextField();
		textName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(textName);
		textName.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addElement(textName.getText());
				
				lblMessage.setText("Öğrenci Eklendi");
				textName.setText("");
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				model.removeElement(textName.getText());
				textName.setText("");
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnRemove);
		
		
		JButton btnRemoveSelected = new JButton("Seçili olanı sil");
		btnRemoveSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = lstStudent.getSelectedIndex();
				if(index != -1) {
					model.removeElementAt(index);
				}else {
					lblMessage.setText("seçili bir eleman yok.");
				}
				
			}
		});
		btnRemoveSelected.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		contentPane.add(btnRemoveSelected);
		contentPane.add(lblMessage);
		contentPane.add(lstStudent);
	}

}
