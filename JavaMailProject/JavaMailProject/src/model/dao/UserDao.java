package model.dao;

import java.util.List;

import model.beans.User;

public interface UserDao {

	public void insert(User user);
	public User select(String email);
	public List<User> selectAll();
	public boolean update(User user);
	public boolean delete(User user);
	public boolean exists(User user);

	
}
