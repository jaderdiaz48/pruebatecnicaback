package co.nuvu.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import co.nuvu.models.CardTypes;

@Component
public interface ICardTypesDao extends CrudRepository<CardTypes,Integer>{

}
