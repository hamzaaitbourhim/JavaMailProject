package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.AddUpdateMailingListsAction;
import controller.MailingListsSearchAction;
import model.dao.FilePaths;
import model.dao.MailingListDao;
import model.dao.MailingListDaoXml;


public class MailingListsPage extends JFrame{

	private static final long serialVersionUID = 1L;

	public MailingListsPage(String namee, String emails) {
		
		setVisible(true);
		setTitle("Mailing Lists Manager");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 721, 493);
		setResizable(false);
		setLocationRelativeTo(null);
		pack();
		
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(new EmptyBorder(20, 40, 20, 40));

		JPanel p2 = new JPanel();
		LabeledTextField name = new LabeledTextField("Name", 25, 50);
		name.setText(namee);
		p2.add(name);
		
		JButton search = new JButton("Search");
		search.addActionListener(new MailingListsSearchAction(name));
		
		p2.add(search);
		
		p.add(p2);
		

		JTextArea text = new JTextArea(10, 40);
		text.setText(emails);
		p.add(text);
		
		p.add(Box.createVerticalStrut(30));

		JPanel p3 = new JPanel();
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailingListDao mailingListDao = new MailingListDaoXml(FilePaths.MAILINGLISTS_XML_FILE_PATH);
				mailingListDao.delete(name.getText());
				SwingUtilities.windowForComponent((JButton)e.getSource()).dispose();
				new MailingListsPage("", "");
			}
		});
		p3.add(delete);
		
		p3.add(Box.createHorizontalStrut(30));
		
		JButton addUpdate = new JButton("Add/Update");
		addUpdate.addActionListener(new AddUpdateMailingListsAction(name, text));
		p3.add(addUpdate);
		
		p.add(p3);

		setContentPane(p);
		pack();
	}
}
