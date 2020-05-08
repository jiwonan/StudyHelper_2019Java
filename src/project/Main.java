package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class Main extends JFrame {
	
	int chk;
	
	Main() {
		setTitle("Main");
		// 파일 객체 생성
		File file = new File("result.txt");
		
		try{
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            int singleCh = 0;
            while((singleCh = filereader.read()) != -1){
                System.out.println((char)singleCh);
                chk = ((int)singleCh)-48;
            }
            System.out.println(chk);
            filereader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
		
		// 컨테이너
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// 배경
		ImageIcon background = new ImageIcon("img/startimg.png");
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
		ImageIcon normalIcon = new ImageIcon("img/btn.png");
		ImageIcon pressedIcon = new ImageIcon("img/btn_pressed.png");
		JButton btn = new JButton(normalIcon);
		btn.setBounds(150, 430, 90, 29);
		btn.setPressedIcon(pressedIcon);
		
		// 버튼 기본 디자인 초기화
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		
		// 다른 창 띄우기
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chk == 1) {
					new Menu();
					dispose();
				}
				else if (chk == 0) {
					new Purpose();
					dispose();
				}
				try {
					FileWriter filewriter = new FileWriter(file);
					// if(chk == 0)
						filewriter.write("1");
					filewriter.close();
				}  catch (FileNotFoundException e1) {
					// TODO: handle exception
				} catch(IOException e1) {
					System.out.println(e1);
				}
			}	
		});
		
		image.add(btn);
		
		setSize(420,755);
		setVisible(true);
		setResizable(false);
		
	}
	public static void main(String ar[]) {
		new Main();
	}
}
