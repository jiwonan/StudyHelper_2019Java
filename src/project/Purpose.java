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
	Connection conn = null; // 데이터 베이스와 연결을 위한 객체
	PreparedStatement pstmt = null; // SQL 문을 데이터베이스에 보내기위한 객체
	
	String driver = "com.mysql.jdbc.Driver";
	
	String SQL = "insert into purpose(jumsu, present) values(?, ?)";
	String DeleteSQL1 = "delete from purpose";
	String DeleteSQL2 = "delete from dbcot_1";	
	
	Purpose() {
		setTitle("Purpose");
		// 컨테이너
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// 배경		
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
		
		// 시작 버튼
		ImageIcon normalIcon = new ImageIcon("img/inputenterbtn.png");
		ImageIcon pressedIcon = new ImageIcon("img/inputenterbtn_pressed.png");
		JButton btn = new JButton(normalIcon);
		btn.setBounds(160, 550, 81, 61);
		btn.setPressedIcon(pressedIcon);
		
		// 입력 창
		JTextField inputs[] = new JTextField[2]; // 0: 점수  1: 선물 설정.
		for(int i=0;i<inputs.length;i++) {
			inputs[i] = new JTextField();
			inputs[i].setBounds(100, 380+(90*i), 200, 60);
			inputs[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
			inputs[i].setBackground(Color.GRAY);
			inputs[i].setFont(new Font("나눔스퀘어", Font.CENTER_BASELINE, 30));
			inputs[i].setForeground(Color.WHITE);
		}
		
		// 버튼 기본 디자인 초기화
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		
		// 다른 창 띄우기
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 값 가져오기
				int sum = 0;
				
				// 데이터베이스에 값 넣기
				try {
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root", "mirim1");
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println(" [ Connected ! ]");
					
					pstmt = conn.prepareStatement(SQL);
					pstmt = conn.prepareStatement(DeleteSQL1);
					pstmt = conn.prepareStatement(DeleteSQL2);
					
					// pstmt.set<데이터타입>(? 순서, 값) ex).setString(), .setInt ...

					pstmt.setInt(1, Integer.parseInt(inputs[0].getText())); // 학기
					pstmt.setString(2, inputs[1].getText()); // 국어
					
					// 5. SQL 문장을 실행하고 결과를 리턴 - SQL 문장 실행 후, 변경된 row 수 int type 리턴
					int r = pstmt.executeUpdate();
					
					// pstmt.excuteQuery() : select
					// pstmt.excuteUpdate() : insert, update, delete ..
					
					System.out.println("변경된 row : " + r);
				} catch (SQLException e2) {
					System.out.println("[SQL Error : " + e2.getMessage() + "]");
					} catch (ClassNotFoundException e1) {
						System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
						} finally { //사용순서와 반대로 close 함 
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
