package mygui.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyLogin {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField name;
	private JPasswordField password;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyLogin window = new MyLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("登录");
		frame.setLocationRelativeTo(null);//设置窗口打开的位置居中
		frame.setBounds(100, 100, 557, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 0, 5, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MyLogin.class.getResource("/Images/4f321ae9fa813abf6522eccf72d0095f_5.jpg")));
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lbl_zhanghao = new JLabel("账号");
		lbl_zhanghao.setBounds(120, 96, 32, 19);
		lbl_zhanghao.setFont(new Font("宋体", Font.PLAIN, 16));
		panel.add(lbl_zhanghao);
		
		JLabel lbl_mima = new JLabel("密码");
		lbl_mima.setBounds(120, 137, 32, 19);
		lbl_mima.setFont(new Font("宋体", Font.PLAIN, 16));
		panel.add(lbl_mima);
		
		name = new JTextField();
		name.setBounds(190, 95, 76, 22);
		name.setFont(new Font("黑体", Font.PLAIN, 14));
		name.setColumns(10);
		panel.add(name);
		
		password = new JPasswordField();
		password.setBounds(190, 137, 76, 21);
		panel.add(password);
		
		JButton btn_chongzhi = new JButton("重置");
		btn_chongzhi.setBounds(120, 184, 57, 23);
		panel.add(btn_chongzhi);
		btn_chongzhi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name.setText("");
				password.setText("");
			}
		});
		
		JButton btn_denglu = new JButton("登录");
		btn_denglu.setBounds(209, 184, 57, 23);
		panel.add(btn_denglu);
		btn_denglu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name.getText().trim().length()==0||new String(password.getPassword()).trim().length()==0){
					JOptionPane.showMessageDialog(null, "账号或密码不允许为空！");
					return;
				}
				if(name.getText().trim().equals("xxy")&&new String(password.getPassword()).trim().equals("123456")){
					JOptionPane.showMessageDialog(null, "登录成功！");
					System.out.println(name.getText());
				}
				else{
					JOptionPane.showMessageDialog(null, "账号或密码错误！");
				}
				
			}
		});
	}

}
