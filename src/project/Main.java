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
		// ���� ��ü ����
		File file = new File("result.txt");
		
		try{
            //�Է� ��Ʈ�� ����
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
		
		// �����̳�
		Container cp = getContentPane();
		cp.setLayout(null);
		
		// ���
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
		
		// ���� ��ư
		ImageIcon normalIcon = new ImageIcon("img/btn.png");
		ImageIcon pressedIcon = new ImageIcon("img/btn_pressed.png");
		JButton btn = new JButton(normalIcon);
		btn.setBounds(150, 430, 90, 29);
		btn.setPressedIcon(pressedIcon);
		
		// ��ư �⺻ ������ �ʱ�ȭ
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		
		// �ٸ� â ����
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
