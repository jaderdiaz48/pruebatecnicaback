package co.nuvu.services;

import java.util.List;

import co.nuvu.models.Roles;

public interface IRolesService {
	

	public List<Roles> findAll();

	public Roles findById(Integer id);

	public Roles save(Roles user);
	
	public void delete(Integer id);
	
}
