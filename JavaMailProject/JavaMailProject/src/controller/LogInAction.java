package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.beans.User;
import model.dao.FilePaths;
import model.dao.UserDao;
import model.dao.UserDaoXml;
import view.HomePage;
import view.LabeledPasswordField;
import view.LabeledTextField;



public class LogInAction implements ActionListener {
	
	private LabeledTextField email;
	private LabeledPasswordField password;
	
	public LogInAction(LabeledTextField email, LabeledPasswordField password) {
		this.email = email;
		this.password = password;
	}

	
	public void actionPerformed(ActionEvent e) {

		UserDao userDao = new UserDaoXml(FilePaths.USERS_XML_FILE_PATH);
		User user = new User(email.getText(), password.getText());
		
		if(AppSession.createAppSession(user)) { //correct email & password
			if(!userDao.exists(user))
				userDao.insert(user);
			else
				userDao.update(user);
			
			SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
			new HomePage();
			
			
		}else { //incorrect email or password
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect Email or Password (or bad connection)!", "Error",
			        JOptionPane.ERROR_MESSAGE);
		}

	}
}