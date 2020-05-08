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
	JButton btnA = new JButton("전체");
	JButton btnM = new JButton("1학기");
	JButton btnF = new JButton("2학기");
	JTextArea txtResult = new JTextArea();
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public Compare() {
		setTitle("Compare");
		
		layInit();	//레이아웃 메소드
		accDb();	//db접근메소드
		
		setSize(420,755);
		setVisible(true);
		setResizable(false);
	}
	
	//레이아웃
	private void layInit() {
		JPanel panel = new JPanel();		// Flowlayout
		panel.add(btnA); panel.add(btnM); panel.add(btnF);
		txtResult.setEditable(false);			// read only
		JScrollPane pane = new JScrollPane(txtResult);           // ScrollBar 추가
		
		add("Center", pane);
		add("North", panel);
		
		btnA.addActionListener(this);
		btnM.addActionListener(this);
		btnF.addActionListener(this);
	}
	
	//Database Driver 로딩
	private void accDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("오류 : " + e);
		}
	}
	
	//각 버튼에 대한 실행 내용
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			//Driver 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "1234");
			
			//열기
			stmt = conn.createStatement();
			String sql = "select hakgi, korean, math, english, sience, time from dbcot_1";
			//중간 기말 나누는 부분
			if(e.getSource() == btnA) {
			} else if(e.getSource() == btnM) {
				sql += " where hakgi like '1학기'";
				//sql += " where substr(gogek_jumin,8,1)=1";	
			} else if(e.getSource() == btnF) {	
				sql += " where hakgi like '2학기'";
			}
			
			//읽기
			rs = stmt.executeQuery(sql);
			txtResult.setText("학기          국어        수학        영어        과학        시간\n");
			while(rs.next()) {
			//읽은 내용을 TextArea에 뿌리기
			String str = rs.getString("hakgi") + "         " + rs.getString("korean") + "       " + rs.getString("math") + "       " + rs.getString("english") + "       " + rs.getString("sience") + "       " + rs.getString("time") + "\n"; 
			txtResult.append(str);
						}
						
		} catch (Exception e2) {
			System.out.println("actionPerformed err : " + e);
		} finally {						
			// 닫기
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