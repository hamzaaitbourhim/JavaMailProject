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

import model.beans.ArchivedEmail;

public class ArchivedEmailDaoXml implements ArchivedEmailDao {

	private String filePath = "";
	private Document doc;
	private Element root;

	public ArchivedEmailDaoXml(String filePath) {
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

	public void insert(ArchivedEmail archivedEmail) {
		
			Element e = new Element("ArchivedEmail");
			Attribute a = new Attribute("id", archivedEmail.getId().toString());
			e.setAttribute(a);

			Element e1 = new Element("from");
			e1.addContent(archivedEmail.getFrom());
			e.addContent(e1);

			Element e2 = new Element("to");
			e2.addContent(archivedEmail.getTo());
			e.addContent(e2);

			Element e3 = new Element("date");
			e3.addContent(archivedEmail.getDate());
			e.addContent(e3);

			Element e4 = new Element("subject");
			e4.addContent(archivedEmail.getSubject());
			e.addContent(e4);

			Element e5 = new Element("body");
			e5.addContent(archivedEmail.getBody());
			e.addContent(e5);

			root.addContent(e);
			save();

	}

	@SuppressWarnings("unchecked")
	public ArchivedEmail select(String from, Long id) {
		List<Element> listOfElements = root.getChildren();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("from").equals(from)
						&& element.getAttributeValue("id").equals(id.toString())) {
					ArchivedEmail archivedEmail = new ArchivedEmail(id, element.getAttributeValue("from"),
							element.getAttributeValue("to"), element.getAttributeValue("date"),
							element.getAttributeValue("subject"), element.getAttributeValue("body"));
					return archivedEmail;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ArchivedEmail> selectAll(String from) {
		List<Element> listOfElements = root.getChildren();
		List<ArchivedEmail> archivedEmails = new ArrayList<ArchivedEmail>();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("from").equals(from)) {
					ArchivedEmail archivedEmail = new ArchivedEmail(Long.parseLong(element.getAttributeValue("id")), element.getChildText("from"),
							element.getChildText("to"), element.getChildText("date"),
							element.getChildText("subject"), element.getChildText("body"));
					archivedEmails.add(archivedEmail);
				}
			}
		}
		return archivedEmails;
	}

	@SuppressWarnings("unchecked")
	public boolean delete(String from, Long id) {
		List<Element> listOfElements = root.getChildren();
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("from").equals(from)
						&& element.getAttributeValue("id").equals(id.toString())) {
					element.detach();
					save();
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Long getNextId(String from) {
		List<Element> listOfElements = root.getChildren();
		Long n = Long.valueOf(0);
		if (listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getChildText("from").equals(from)) {
					n++;
				}
			}
		}
		return n;
	}


}
