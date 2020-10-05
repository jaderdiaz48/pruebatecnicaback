package co.nuvu.services;

import java.math.BigInteger;
import java.util.List;

import co.nuvu.models.CreditCards;

public interface ICreditCardsService {
	

	public List<CreditCards> findAll();

	public CreditCards findById(Integer id);

	public CreditCards save(CreditCards user);
	
	public void delete(Integer id);
	
	public List<CreditCards> findByUser(Integer userId);
	
	public CreditCards validateExist(Integer customerId, Integer cardTypeId, long number);
}
