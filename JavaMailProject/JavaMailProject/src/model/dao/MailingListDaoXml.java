package model.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import controller.AppSession;
import model.beans.MailingList;

public class MailingListDaoXml implements MailingListDao {

	private String filePath = "";
	private Document doc;
	private Element root;

	public MailingListDaoXml(String filePath) {
		this.filePath = filePath;
		load();
	}

	private void load() {
		try {
			SAXBuilder sxb = new SAXBuilder();
			doc = sxb.build(new File(filePath));
			root = doc.getRootElement();
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	private void save() {
		try {
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			out.output(doc, new FileOutputStream(filePath));
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}


	public void insert(MailingList mailingList) {
		if(!exists(mailingList.getName())) {
		Element e = new Element("mailingList");
		Attribute a = new Attribute("owner", AppSession.getAppUser().getEmail());
		e.setAttribute(a);

		Element e2 = new Element("name");
		e2.addContent(mailingList.getName());
		e.addContent(e2);

		for (String email : mailingList.getEmails()) {
			Element e3 = new Element("email");
			e3.addContent(email);
			e.addContent(e3);
		}

		root.addContent(e);
		save();
		}
	}

	@SuppressWarnings("unchecked")
	public MailingList select(String name) {
		List<Element> listOfElements = root.getChildren();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("name").equals(name)
						&& element.getAttributeValue("owner").equals(AppSession.getAppUser().getEmail())) {
					MailingList mailingList = new MailingList(name);
					List<Element> listOfEmails = element.getChildren("email");
					for (Element email : listOfEmails) {
						mailingList.add(email.getText());
					}
					return mailingList;
				}
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<MailingList> selectAll() {
		List<Element> listOfElements = root.getChildren();
		List<MailingList> mailingLists = new ArrayList<MailingList>();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getAttributeValue("owner").equals(AppSession.getAppUser().getEmail())) {
					MailingList mailingList = new MailingList(element.getChildText("name"));
					List<Element> listOfEmails = element.getChildren("email");
					for (Element email : listOfEmails) {
						mailingList.add(email.getText());
					}
					mailingLists.add(mailingList);
				}
			}
		}
		return mailingLists;
	}


	public boolean update(MailingList mailingList) {
		if (exists(mailingList.getName())) {
			delete(mailingList.getName());
			insert(mailingList);
			return true;
		}
			return false;
	}


	@SuppressWarnings("unchecked")
	public boolean delete(String name) {
		List<Element> listOfElements = root.getChildren();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("name").equals(name)
						&& element.getAttributeValue("owner").equals(AppSession.getAppUser().getEmail())) {
					element.detach();
					save();
					return true;
				}
			}
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	public boolean exists(String name) {
		List<Element> listOfElements = root.getChildren();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("name").equals(name)
						&& element.getAttributeValue("owner").equals(AppSession.getAppUser().getEmail())) {
					return true;
				}
			}
		}
		return false;
	}

}
