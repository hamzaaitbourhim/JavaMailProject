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
import view.ArchivePage;
import view.LabeledPasswordField;
import view.LabeledTextField;


public class ArchiveAction implements ActionListener {
	
	private LabeledTextField email;
	private LabeledPasswordField password;
	private UserDao userDao;
	
	public ArchiveAction(LabeledTextField email, LabeledPasswordField password) {
		this.email = email;
		this.password = password;
	}

	public void actionPerformed(ActionEvent e) {
		
		userDao = new UserDaoXml(FilePaths.USERS_XML_FILE_PATH);
		User user = new User(email.getText(), password.getText());
		
		if(userDao.exists(user)) { //correct email & password
			
			SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
			new ArchivePage(user);
			
			
		}else { //incorrect email or password
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect Email or Password!", "Dialog",
			        JOptionPane.ERROR_MESSAGE);
		}

	}
}