package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import controller.AppSession;

public class HomePage extends JFrame {

	private static final long serialVersionUID = 1L;

	public HomePage() {

		setVisible(true);
		setTitle("Boite Messagerie");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 493);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setBounds(0, 0, 200, 456);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton sendEmail = new JButton("Send Email");
		sendEmail.setBounds(27, 60, 145, 23);
		sendEmail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new SendMessagePage();
			}
		});
		panel.add(sendEmail);

		JButton offlineArchive = new JButton("Offline Archive");
		offlineArchive.setBounds(27, 120, 145, 23);
		offlineArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ArchivePage(AppSession.getAppUser());
			}
		});
		panel.add(offlineArchive);

		JButton mailingLists = new JButton("Mailing lists");
		mailingLists.setBounds(27, 180, 145, 23);
		mailingLists.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new MailingListsPage("", "");
			}
		});
		panel.add(mailingLists);

		JButton logOut = new JButton("Log Out");
		logOut.setBounds(27, 240, 145, 23);
		logOut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AppSession.setSession(null);
				new LoginPage();
				dispose();
			}
		});
		panel.add(logOut);

		/*
		JTextField ZoneRecherche = new JTextField();
		ZoneRecherche.setBounds(384, 10, 313, 28);
		contentPane.add(ZoneRecherche);
		ZoneRecherche.setColumns(10);
	
		JButton RechercheButton = new JButton("Rechercher");
		RechercheButton.setBounds(230, 10, 127, 26);
		contentPane.add(RechercheButton);
		*/
		
		//messages
		Message msgs[] = AppSession.getMessages();
		String[][] messages = new String[msgs.length][3];
		for (int i = 0; i < msgs.length; i++) {
			try {
				messages[i][0] = "" + msgs[i].getFrom()[0];
				messages[i][1] = msgs[i].getSubject();
				messages[i][2] = msgs[i].getReceivedDate().toString();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
		String header[] = { "From", "Suject", "Date" };
		final JTable jt = new JTable(messages, header);

		/*
		 * jt.setCellSelectionEnabled(true); ListSelectionModel select =
		 * jt.getSelectionModel();
		 * select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * select.addListSelectionListener(new ListSelectionListener() {
		 * 
		 * @Override public void valueChanged(ListSelectionEvent e) { String Data =
		 * null; int[] row = jt.getSelectedRows(); int[] columns =
		 * jt.getSelectedColumns(); for (int i = 0; i < row.length; i++) { for (int j =
		 * 0; j < columns.length; j++) { Data = (String) jt.getValueAt(row[i],
		 * columns[j]); } } System.out.println("Table element selected is: " + Data);
		 * 
		 * }
		 * 
		 * });
		 */
		
		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(230, 50, 465, 400);
		add(sp);
		

	}
}
