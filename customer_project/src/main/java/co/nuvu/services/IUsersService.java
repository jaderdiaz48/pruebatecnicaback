package co.nuvu.services;

import java.util.List;

import co.nuvu.models.Users;

public interface IUsersService {
	

	public List<Users> findAll();

	public Users findById(Integer id);

	public Users save(Users user);
	
	public Users findByUser(String user);
	
	public void delete(Integer id);
	
}
