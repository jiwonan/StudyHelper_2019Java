package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.sql.*;

public class Input extends JFrame {
	
	int jumsu[] = new int[5]; // 0: ����  1: ���� 2: ���� 3: ���� 4: �ð�
	// String hakgi1;
	float avg1;
	
	Connection conn = null; // ������ ���̽��� ������ ���� ��ü
	PreparedStatement pstmt = null; // SQL ���� �����ͺ��̽��� ���������� ��ü
	
	String driver = "com.mysql.jdbc.Driver";
	
	String SQL = "insert into dbcot_1(hakgi, korean, math, english, sience, time, avg) values(?, ?, ?, ?, ?, ?, ?)";
	
	Input() {
		setTitle("Input");
		
		// �����̳�
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// ���		
		ImageIcon background = new ImageIcon("img/input_bg.png");
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
		
		// ���� �Է� â
		JTextField inputs[] = new JTextField[5]; // 0: ����,  1: ����, 2: ����, 3: ����, 4: �ð�
		for(int i=0;i<inputs.length;i++) {
			inputs[i] = new JTextField();
			inputs[i].setBounds(150, 116+(i*100), 230, 60);
			inputs[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
			inputs[i].setBackground(Color.GRAY);
			inputs[i].setFont(new Font("����������", Font.CENTER_BASELINE, 30));
			inputs[i].setForeground(Color.WHITE);
		}
		
		// �б� �Է� â
		JTextField Hakgi = new JTextField();
		Hakgi.setBounds(280, 10, 100, 51);
		Hakgi.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		Hakgi.setBackground(Color.GRAY);
		Hakgi.setFont(new Font("����������", Font.CENTER_BASELINE, 30));
		Hakgi.setForeground(Color.WHITE);
		
		// �Է� ��ư
		ImageIcon normalIcon = new ImageIcon("img/inputenterbtn.png");
		ImageIcon pressedIcon = new ImageIcon("img/inputenterbtn_pressed.png");
		JButton enterBtn = new JButton(normalIcon);
		enterBtn.setBounds(160, 600, 81, 61);
		enterBtn.setPressedIcon(pressedIcon);
		enterBtn.setBorderPainted(false);
		enterBtn.setContentAreaFilled(false);
		enterBtn.setFocusPainted(false);
		
		for(int i=0;i<inputs.length;i++) {
			image.add(inputs[i]);
		}
		image.add(Hakgi);
		image.add(enterBtn);
		
		enterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// �� ��������
				int sum = 0;
				for(int i = 0;i<inputs.length;i++) { // 4
					jumsu[i]=Integer.parseInt(inputs[i].getText());
					if(i!=4)
						sum+=jumsu[i];
				}
				avg1 = (sum / ( jumsu.length - 1 ));
				
				// �����ͺ��̽��� �� �ֱ�
				try {
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root", "mirim1");
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println(" [ Connected ! ]");
					
					pstmt = conn.prepareStatement(SQL);
					
					// pstmt.set<������Ÿ��>(? ����, ��) ex).setString(), .setInt ...

					pstmt.setString(1, Hakgi.getText()); // �б�
					pstmt.setInt(2, jumsu[0]); // ����
					pstmt.setInt(3, jumsu[1]); // ����
					pstmt.setInt(4, jumsu[2]); // ����
					pstmt.setInt(5, jumsu[3]); // ����
					pstmt.setInt(6, jumsu[4]); // �ð�
					pstmt.setFloat(7, avg1);
					
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
			}
		});
		
		setSize(420,755);
		setVisible(true);
		setResizable(false);
	}
	public static void main(String ar[]) {
		new Input();
	}
}
