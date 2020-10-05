package co.nuvu.services;

import java.util.List;

import co.nuvu.models.Customers;

public interface ICustomersService {
	

	public List<Customers> findAll();

	public Customers findById(Integer id);

	public Customers save(Customers user);
	
	public void delete(Integer id);
	
	public Customers findByDni(String dni);
	
	public Customers findByUser(Integer userId);
}
