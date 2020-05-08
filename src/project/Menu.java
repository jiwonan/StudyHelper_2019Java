package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import project.Menu;

public class Menu extends JFrame {
	
	Connection conn1;
	Statement stmt;
	ResultSet rs;
	String gift;
	float avg;
	float jumsu;
	boolean attain;
	
	Menu() {
		attain = false;
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// �����̳�
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// ���		
		ImageIcon background = new ImageIcon("img/menu.png");
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
		
		// ��ư �ʱ� ����
		JButton btns[] = new JButton[4];
		
		// ���� �Է� ��ư
		ImageIcon jumsuBtn = new ImageIcon("img/inputbtn.png");
		ImageIcon jumsuBtn_pressed = new ImageIcon("img/inputbtn_pressed.png");
		btns[0] = new JButton(jumsuBtn);
		btns[0].setPressedIcon(jumsuBtn_pressed);
		btns[0].setBounds(150, 300, 100, 30);
		
		// �ð� ���� ��ư
		ImageIcon timeBtn = new ImageIcon("img/timebtn.png");
		ImageIcon timeBtn_pressed = new ImageIcon("img/timebtn_pressed.png");
		btns[1] = new JButton(timeBtn);
		btns[1].setPressedIcon(timeBtn_pressed);
		btns[1].setBounds(150, 360, 100, 30);
		
		// ���� �� ��ư
		ImageIcon compareBtn = new ImageIcon("img/comparebtn.png");
		ImageIcon compareBtn_pressed = new ImageIcon("img/comparebtn_pressed.png");
		btns[2] = new JButton(compareBtn);
		btns[2].setPressedIcon(compareBtn_pressed);
		btns[2].setBounds(150, 420, 100, 30);
		
		// ���� ��ư
		ImageIcon endBtn = new ImageIcon("img/exitbtn.png");
		ImageIcon endBtn_pressed = new ImageIcon("img/exitbtn_pressed.png");
		btns[3] = new JButton(endBtn);
		btns[3].setPressedIcon(endBtn_pressed);
		btns[3].setBounds(150, 480, 100, 30);
		
		// ���� �Է� â ����
		btns[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Input();
			}
		});
		
		// �ð� ���� â ����
		btns[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Clock();
			}
		});
		
		// ���� �� â ����
		btns[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Compare();
			}
		});
		
		// �����ϱ�
		btns[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.exit(0);
			}
		});
		
		// ��ư ������ �ʱ�ȭ, ��ư ���̱�
		for(int i=0;i<4;i++) {
			btns[i].setBorderPainted(false);
			btns[i].setContentAreaFilled(false);
			btns[i].setFocusPainted(false);
			image.add(btns[i]);
		}
		
		try {
			conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root", "mirim1");
			// conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root", "mirim1");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println(" [ Connected ! ]");
		} catch (Exception e) {
			System.out.println("���� : " + e);
		}
		
		try {
			//Driver ����
			// conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root", "mirim1");
			
			//����
			stmt = conn1.createStatement();
			String sql1 = "select avg from dbcot_1";
			String sql2 = "select * from purpose";
//			//�߰� �⸻ ������ ��
//			if(e.getSource() == btnA) {
//			} else if(e.getSource() == btnM) {	
//				sql += " where gimal like '%-1%'";
//				//sql += " where substr(gogek_jumin,8,1)=1";	
//			} else if(e.getSource() == btnF) {	
//				sql += " where jungan like '%-2%'";
//			}
			
			//�б�
			rs = stmt.executeQuery(sql1);
			while(rs.next()) {
				//���� ������ TextArea�� �Ѹ���
				avg = rs.getFloat("avg");
			}
			rs = stmt.executeQuery(sql2);
			while(rs.next()) {
				//���� ������ TextArea�� �Ѹ���
				jumsu = rs.getFloat("jumsu");
				gift = rs.getString("present");
			}
			if (avg >= jumsu) {
				System.out.println(avg+","+jumsu+"\n");
				attain = true;
				System.out.println(attain);
			}
		} catch (Exception e2) {
			System.out.println("actionPerformed err : " + e2);
		} finally {						
			// �ݱ�
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn1 != null) conn1.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		// ��ǥ �޼� Ȯ�� ��ư
		ImageIcon presentboxBtn = new ImageIcon("img/presentbox_close.png");
		ImageIcon presentboxBtn_pressed = new ImageIcon("img/presentbox_open.png");
		JButton present = new JButton(presentboxBtn);
		present.setPressedIcon(presentboxBtn_pressed);
		
		present.setBorderPainted(false);
		present.setContentAreaFilled(false);
		present.setFocusPainted(false);
		present.setBounds(300, 600, 70, 73);
		
		// ��ǥ �޼� â ����
		present.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(attain == true) {
					attain = false;
					new Attainment();
				}
				else {
					
				}
			}
		});
		
		image.add(present);
		
		setSize(420,755);
		setVisible(true);
		setResizable(false);
	}
	public static void main(String ar[]) {
		new Menu();
	}
}
