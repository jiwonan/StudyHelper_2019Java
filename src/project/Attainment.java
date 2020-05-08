package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class Attainment extends JFrame {
	Attainment() {
		setUndecorated(true);
		
		// �����̳�
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// ���		
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
		
		// â �ݱ� ��ư
		ImageIcon normalIcon = new ImageIcon("img/xbtn.png");
		ImageIcon pressedIcon = new ImageIcon("img/xbtn_pressed.png");
		JButton xBtn = new JButton(normalIcon);
		xBtn.setBounds(360, -15, 71, 72);
		xBtn.setPressedIcon(pressedIcon);
		
		// ��ư �⺻ ������ �ʱ�ȭ
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
