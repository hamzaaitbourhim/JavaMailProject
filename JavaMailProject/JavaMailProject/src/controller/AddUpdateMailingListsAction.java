package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import model.beans.MailingList;
import model.dao.FilePaths;
import model.dao.MailingListDao;
import model.dao.MailingListDaoXml;
import view.LabeledTextField;
import view.MailingListsPage;

public class AddUpdateMailingListsAction implements ActionListener{
	
	private LabeledTextField name;
	private JTextArea text;
	
	public AddUpdateMailingListsAction(LabeledTextField name, JTextArea text) {
		this.name = name;
		this.text = text;
	}


	public void actionPerformed(ActionEvent e) {
		MailingListDao mailingListDao = new MailingListDaoXml(FilePaths.MAILINGLISTS_XML_FILE_PATH);
		MailingList mailingList = new MailingList(name.getText());
		String[] emails = text.getText().split("\n");
		for(String email : emails)
			mailingList.add(email);
		
		if(mailingListDao.exists(name.getText())) {
			//update
			mailingListDao.update(mailingList);
		}else {
			//add
			mailingListDao.insert(mailingList);
		}
		
		SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
		new MailingListsPage("", "");
		
	}
	
	
	

}
