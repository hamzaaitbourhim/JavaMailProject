package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import model.beans.ArchivedEmail;
import model.beans.MailingList;
import model.dao.ArchivedEmailDao;
import model.dao.ArchivedEmailDaoXml;
import model.dao.FilePaths;
import model.dao.MailingListDao;
import model.dao.MailingListDaoXml;
import view.LabeledTextField;

public class SendMessageAction implements ActionListener {
	
	private JRadioButton r1;
	private JRadioButton r2;
	private LabeledTextField sujet ;
	private LabeledTextField to;
	private JTextArea text;
	
	
	
	public SendMessageAction(JRadioButton r1, JRadioButton r2 , LabeledTextField sujet, LabeledTextField to, JTextArea text) {
		this.r1 = r1;
		this.r2 = r2;
		this.sujet = sujet;
		this.to = to;
		this.text = text;
	}


	
	public void actionPerformed(ActionEvent e) {

		MailingListDao dao = new MailingListDaoXml(FilePaths.MAILINGLISTS_XML_FILE_PATH);
		ArchivedEmailDao dao2 = new ArchivedEmailDaoXml(FilePaths.ARCHIVE_XML_FILE_PATH);
		
		if(r1.isSelected()) {
			AppSession.send(sujet.getText(), text.getText(), to.getText().split(","));
			
			//save email
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			dao2.insert(new ArchivedEmail(dao2.getNextId(AppSession.getAppUser().getEmail()), AppSession.getAppUser().getEmail(), to.getText(), dtf.format(now).toString(), sujet.getText(), text.getText()));
			
			SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
		}else if(r2.isSelected()) {
			if(dao.exists(to.getText())) {
				MailingList mailingList = dao.select(to.getText());
				AppSession.send(sujet.getText(), text.getText(), mailingList);
				
				//save email
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();  
				String emailss = "";
				for(String email : mailingList.getEmails())
					emailss += email + ", ";
				String emails = emailss.substring(0, emailss.length()-2);
				dao2.insert(new ArchivedEmail(dao2.getNextId(AppSession.getAppUser().getEmail()), AppSession.getAppUser().getEmail(), emails, dtf.format(now).toString(), sujet.getText(), text.getText()));
				
				SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
			}else {//incorrect Mailing List
				JOptionPane.showMessageDialog(new JFrame(), "No Such Mailing List!", "Error",
				        JOptionPane.ERROR_MESSAGE);
			}
				
			
		}

	}

}
