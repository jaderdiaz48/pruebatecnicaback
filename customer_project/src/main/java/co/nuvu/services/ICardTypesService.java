package co.nuvu.services;

import java.util.List;

import co.nuvu.models.CardTypes;

public interface ICardTypesService {
	

	public List<CardTypes> findAll();

	public CardTypes findById(Integer id);

	public CardTypes save(CardTypes user);
	
	public void delete(Integer id);
	
}
