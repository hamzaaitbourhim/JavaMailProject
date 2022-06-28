package model.dao;

import java.util.List;

import model.beans.ArchivedEmail;

public interface ArchivedEmailDao {
	
	public void insert(ArchivedEmail archivedEmail);
	public ArchivedEmail select(String from, Long id);
	public List<ArchivedEmail> selectAll(String from);
	public boolean delete(String from, Long id);
	public Long getNextId(String from);
}
