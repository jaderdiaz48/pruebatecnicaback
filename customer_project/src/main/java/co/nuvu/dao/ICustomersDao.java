package co.nuvu.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import co.nuvu.models.Customers;

@Component
public interface ICustomersDao extends CrudRepository<Customers,Integer>{

}
