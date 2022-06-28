package view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ArchiveAction;
import controller.LogInAction;

public class LoginPage extends JFrame{

	private static final long serialVersionUID = 1L;

	public LoginPage() {

		setVisible(true);
		setTitle("Login");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(new EmptyBorder(20, 40, 20, 40));


		LabeledTextField email = new LabeledTextField("Email", 18, 90);
		p.add(email);
		LabeledPasswordField password = new LabeledPasswordField("Password", 18, 90);
		p.add(password);

		JPanel buttons = new JPanel();
		JButton offline = new JButton("Offline Archive");
		offline.addActionListener(new ArchiveAction(email, password));
		buttons.add(offline);
		
		buttons.add(Box.createHorizontalStrut(30));

		JButton logIn = new JButton("Log in");
		logIn.addActionListener(new LogInAction(email, password));
		buttons.add(logIn);

		p.add(buttons);
		
		setContentPane(p);
		pack();
	}
	

}
