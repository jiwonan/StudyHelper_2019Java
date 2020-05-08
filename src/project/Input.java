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
	
	int jumsu[] = new int[5]; // 0: 국어  1: 수학 2: 영어 3: 과학 4: 시간
	// String hakgi1;
	float avg1;
	
	Connection conn = null; // 데이터 베이스와 연결을 위한 객체
	PreparedStatement pstmt = null; // SQL 문을 데이터베이스에 보내기위한 객체
	
	String driver = "com.mysql.jdbc.Driver";
	
	String SQL = "insert into dbcot_1(hakgi, korean, math, english, sience, time, avg) values(?, ?, ?, ?, ?, ?, ?)";
	
	Input() {
		setTitle("Input");
		
		// 컨테이너
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// 배경		
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
		
		// 점수 입력 창
		JTextField inputs[] = new JTextField[5]; // 0: 국어,  1: 수학, 2: 영어, 3: 과학, 4: 시간
		for(int i=0;i<inputs.length;i++) {
			inputs[i] = new JTextField();
			inputs[i].setBounds(150, 116+(i*100), 230, 60);
			inputs[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
			inputs[i].setBackground(Color.GRAY);
			inputs[i].setFont(new Font("나눔스퀘어", Font.CENTER_BASELINE, 30));
			inputs[i].setForeground(Color.WHITE);
		}
		
		// 학기 입력 창
		JTextField Hakgi = new JTextField();
		Hakgi.setBounds(280, 10, 100, 51);
		Hakgi.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		Hakgi.setBackground(Color.GRAY);
		Hakgi.setFont(new Font("나눔스퀘어", Font.CENTER_BASELINE, 30));
		Hakgi.setForeground(Color.WHITE);
		
		// 입력 버튼
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
				// 값 가져오기
				int sum = 0;
				for(int i = 0;i<inputs.length;i++) { // 4
					jumsu[i]=Integer.parseInt(inputs[i].getText());
					if(i!=4)
						sum+=jumsu[i];
				}
				avg1 = (sum / ( jumsu.length - 1 ));
				
				// 데이터베이스에 값 넣기
				try {
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root", "mirim1");
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println(" [ Connected ! ]");
					
					pstmt = conn.prepareStatement(SQL);
					
					// pstmt.set<데이터타입>(? 순서, 값) ex).setString(), .setInt ...

					pstmt.setString(1, Hakgi.getText()); // 학기
					pstmt.setInt(2, jumsu[0]); // 국어
					pstmt.setInt(3, jumsu[1]); // 수학
					pstmt.setInt(4, jumsu[2]); // 영어
					pstmt.setInt(5, jumsu[3]); // 과학
					pstmt.setInt(6, jumsu[4]); // 시간
					pstmt.setFloat(7, avg1);
					
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
