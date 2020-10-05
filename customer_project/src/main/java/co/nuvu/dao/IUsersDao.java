package co.nuvu.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import co.nuvu.models.Users;

@Component
public interface IUsersDao extends CrudRepository<Users,Integer>{

}
