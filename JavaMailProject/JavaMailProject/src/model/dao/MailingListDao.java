package model.dao;

import java.util.List;

import model.beans.MailingList;

public interface MailingListDao {
	
	public void insert(MailingList mailingList);
	public MailingList select(String name);
	public List<MailingList> selectAll();
	public boolean update(MailingList mailingList);
	public boolean delete(String name);
	public boolean exists(String name);

}