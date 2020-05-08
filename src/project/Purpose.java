package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

import project.Menu;

public class Purpose extends JFrame {
	Connection conn = null; // ������ ���̽��� ������ ���� ��ü
	PreparedStatement pstmt = null; // SQL ���� �����ͺ��̽��� ���������� ��ü
	
	String driver = "com.mysql.jdbc.Driver";
	
	String SQL = "insert into purpose(jumsu, present) values(?, ?)";
	String DeleteSQL1 = "delete from purpose";
	String DeleteSQL2 = "delete from dbcot_1";	
	
	Purpose() {
		setTitle("Purpose");
		// �����̳�
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// ���		
		ImageIcon background = new ImageIcon("img/purpose_bg.png");
		Image backfround_1=background.getImage();
		
		JPanel image = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(backfround_1, 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		image.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(image);
		setContentPane(scrollPane);
		
		// ���� ��ư
		ImageIcon normalIcon = new ImageIcon("img/inputenterbtn.png");
		ImageIcon pressedIcon = new ImageIcon("img/inputenterbtn_pressed.png");
		JButton btn = new JButton(normalIcon);
		btn.setBounds(160, 550, 81, 61);
		btn.setPressedIcon(pressedIcon);
		
		// �Է� â
		JTextField inputs[] = new JTextField[2]; // 0: ����  1: ���� ����.
		for(int i=0;i<inputs.length;i++) {
			inputs[i] = new JTextField();
			inputs[i].setBounds(100, 380+(90*i), 200, 60);
			inputs[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
			inputs[i].setBackground(Color.GRAY);
			inputs[i].setFont(new Font("����������", Font.CENTER_BASELINE, 30));
			inputs[i].setForeground(Color.WHITE);
		}
		
		// ��ư �⺻ ������ �ʱ�ȭ
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		
		// �ٸ� â ����
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// �� ��������
				int sum = 0;
				
				// �����ͺ��̽��� �� �ֱ�
				try {
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root", "mirim1");
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println(" [ Connected ! ]");
					
					pstmt = conn.prepareStatement(SQL);
					pstmt = conn.prepareStatement(DeleteSQL1);
					pstmt = conn.prepareStatement(DeleteSQL2);
					
					// pstmt.set<������Ÿ��>(? ����, ��) ex).setString(), .setInt ...

					pstmt.setInt(1, Integer.parseInt(inputs[0].getText())); // �б�
					pstmt.setString(2, inputs[1].getText()); // ����
					
					// 5. SQL ������ �����ϰ� ����� ���� - SQL ���� ���� ��, ����� row �� int type ����
					int r = pstmt.executeUpdate();
					
					// pstmt.excuteQuery() : select
					// pstmt.excuteUpdate() : insert, update, delete ..
					
					System.out.println("����� row : " + r);
				} catch (SQLException e2) {
					System.out.println("[SQL Error : " + e2.getMessage() + "]");
					} catch (ClassNotFoundException e1) {
						System.out.println("[JDBC Connector Driver ���� : " + e1.getMessage() + "]");
						} finally { //�������� �ݴ�� close �� 
							if (pstmt != null) {
								try {
									pstmt.close();
							} catch (SQLException e2) {
								e2.printStackTrace();
								}
							} if (conn != null) {
								try { conn.close();
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
							}
				}
				new Menu();
				dispose();
			}
		});
		
		image.add(btn);
		image.add(inputs[0]);
		image.add(inputs[1]);
		
		setSize(420,755);
		setVisible(true);
		setResizable(false);
		
	}
	public static void main(String[] args) {
		new Purpose();
	}

}
