package view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.beans.ArchivedEmail;
import model.beans.User;
import model.dao.ArchivedEmailDao;
import model.dao.ArchivedEmailDaoXml;
import model.dao.FilePaths;

public class ArchivePage extends JFrame {

	private static final long serialVersionUID = 1L;

	public ArchivePage(User user) {
		
		setTitle("Archive");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		ArchivedEmailDao dao = new ArchivedEmailDaoXml(FilePaths.ARCHIVE_XML_FILE_PATH);
		ArrayList<ArchivedEmail> archivedEmails = (ArrayList<ArchivedEmail>)dao.selectAll(user.getEmail());
		String[][] messages = new String[archivedEmails.size()][4];
		for (int i = 0; i <  archivedEmails.size(); i++) {
				messages[i][0] = archivedEmails.get(i).getTo();
				messages[i][1] = archivedEmails.get(i).getSubject();
				messages[i][2] = archivedEmails.get(i).getDate();
				messages[i][3] = archivedEmails.get(i).getBody();
		}

		String header[] = { "To", "Subject", "Date", "Body" };
		final JTable jt = new JTable(messages, header);

		jt.setCellSelectionEnabled(true);
		ListSelectionModel select = jt.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		select.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String Data = null;
				int[] row = jt.getSelectedRows();
				int[] columns = jt.getSelectedColumns();
				for (int i = 0; i < row.length; i++) {
					for (int j = 0; j < columns.length; j++) {
						Data = (String) jt.getValueAt(row[i], columns[j]);
					}
				}
				System.out.println(Data);

			}

		});

		JScrollPane sp = new JScrollPane(jt);
		add(sp);

	}
}
