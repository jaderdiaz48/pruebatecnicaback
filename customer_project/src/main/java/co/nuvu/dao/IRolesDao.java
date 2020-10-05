package co.nuvu.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import co.nuvu.models.Roles;

@Component
public interface IRolesDao extends CrudRepository<Roles,Integer>{

}
