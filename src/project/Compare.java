package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Compare extends JFrame implements ActionListener {
	JButton btnA = new JButton("��ü");
	JButton btnM = new JButton("1�б�");
	JButton btnF = new JButton("2�б�");
	JTextArea txtResult = new JTextArea();
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public Compare() {
		setTitle("Compare");
		
		layInit();	//���̾ƿ� �޼ҵ�
		accDb();	//db���ٸ޼ҵ�
		
		setSize(420,755);
		setVisible(true);
		setResizable(false);
	}
	
	//���̾ƿ�
	private void layInit() {
		JPanel panel = new JPanel();		// Flowlayout
		panel.add(btnA); panel.add(btnM); panel.add(btnF);
		txtResult.setEditable(false);			// read only
		JScrollPane pane = new JScrollPane(txtResult);           // ScrollBar �߰�
		
		add("Center", pane);
		add("North", panel);
		
		btnA.addActionListener(this);
		btnM.addActionListener(this);
		btnF.addActionListener(this);
	}
	
	//Database Driver �ε�
	private void accDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("���� : " + e);
		}
	}
	
	//�� ��ư�� ���� ���� ����
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			//Driver ����
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "1234");
			
			//����
			stmt = conn.createStatement();
			String sql = "select hakgi, korean, math, english, sience, time from dbcot_1";
			//�߰� �⸻ ������ �κ�
			if(e.getSource() == btnA) {
			} else if(e.getSource() == btnM) {
				sql += " where hakgi like '1�б�'";
				//sql += " where substr(gogek_jumin,8,1)=1";	
			} else if(e.getSource() == btnF) {	
				sql += " where hakgi like '2�б�'";
			}
			
			//�б�
			rs = stmt.executeQuery(sql);
			txtResult.setText("�б�          ����        ����        ����        ����        �ð�\n");
			while(rs.next()) {
			//���� ������ TextArea�� �Ѹ���
			String str = rs.getString("hakgi") + "         " + rs.getString("korean") + "       " + rs.getString("math") + "       " + rs.getString("english") + "       " + rs.getString("sience") + "       " + rs.getString("time") + "\n"; 
			txtResult.append(str);
						}
						
		} catch (Exception e2) {
			System.out.println("actionPerformed err : " + e);
		} finally {						
			// �ݱ�
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new Compare();
	}
}