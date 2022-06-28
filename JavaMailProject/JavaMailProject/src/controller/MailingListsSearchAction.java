package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.beans.MailingList;
import model.dao.FilePaths;
import model.dao.MailingListDao;
import model.dao.MailingListDaoXml;
import view.LabeledTextField;
import view.MailingListsPage;

public class MailingListsSearchAction implements ActionListener{

	private LabeledTextField name;
	
	public MailingListsSearchAction(LabeledTextField name) {
		this.name = name;
	}

	public void actionPerformed(ActionEvent e) {
		
		MailingListDao mailingListDao = new MailingListDaoXml(FilePaths.MAILINGLISTS_XML_FILE_PATH);
		MailingList list = mailingListDao.select(name.getText());
		String emails = "";
		
		if(list != null) {
		for(String email : list.getEmails()) {
			emails += email + "\n" ;
		}
		SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
		new MailingListsPage(name.getText(), emails);
		}else {
			JOptionPane.showMessageDialog(new JFrame(), "No Such Mailing List!", "Error",
			        JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
