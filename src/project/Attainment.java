package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class Attainment extends JFrame {
	Attainment() {
		setUndecorated(true);
		
		// 컨테이너
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// 배경		
		ImageIcon background = new ImageIcon("img/attainment.png");
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
		
		// 창 닫기 버튼
		ImageIcon normalIcon = new ImageIcon("img/xbtn.png");
		ImageIcon pressedIcon = new ImageIcon("img/xbtn_pressed.png");
		JButton xBtn = new JButton(normalIcon);
		xBtn.setBounds(360, -15, 71, 72);
		xBtn.setPressedIcon(pressedIcon);
		
		// 버튼 기본 디자인 초기화
		xBtn.setBorderPainted(false);
		xBtn.setContentAreaFilled(false);
		xBtn.setFocusPainted(false);
		
		image.add(xBtn);
		
		xBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Purpose();
				dispose();
			}
		});
		
		setLocation(0,270);
		setSize(420, 208);
		setVisible(true);
		setResizable(false);
	}
	public static void main(String ar[]) {
		new Attainment();
	}
}
