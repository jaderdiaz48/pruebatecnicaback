package co.nuvu.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import co.nuvu.models.CreditCards;

@Component
public interface ICreditCardsDao extends CrudRepository<CreditCards,Integer>{

}
