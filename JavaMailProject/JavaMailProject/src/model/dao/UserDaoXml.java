package model.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import model.beans.User;

public class UserDaoXml implements UserDao{
	
	private String filePath = "";
	private Document doc;
	private Element root;
	
	public UserDaoXml(String filePath) {
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
		
	public void insert(User user) {
		if(!exists(user)) {

		Element e = new Element("user");
		Attribute a = new Attribute("email", user.getEmail());
		e.setAttribute(a);
		
		Element e2 = new Element("password");
		e2.addContent(user.getPassword());
		e.addContent(e2);

		root.addContent(e);
		save();
		}
	}

	@SuppressWarnings("unchecked")
	public User select(String email) {
		List<Element> listOfElements = root.getChildren();
		if(listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getAttributeValue("email").equals(email)) {
					return new User(element.getAttributeValue("email"), element.getChildText("password"));
				}
			}
		}
		return null;
	}

	
	public List<User> selectAll() {
		return null;
	}

	public boolean update(User user) {
		save();
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean delete(User user) {
		List<Element> listOfElements = root.getChildren();
		if(listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getAttributeValue("email").equals(user.getEmail())) {
					element.detach();
					save();
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean exists(User user) {
		List<Element> listOfElements = root.getChildren();
		if(listOfElements != null) {
			for (Element element : listOfElements) {
				if (element.getAttributeValue("email").equals(user.getEmail()) && element.getChildText("password").equals(user.getPassword())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
