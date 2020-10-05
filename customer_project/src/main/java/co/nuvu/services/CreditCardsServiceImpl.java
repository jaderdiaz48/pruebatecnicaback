package co.nuvu.services;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.nuvu.models.CreditCards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nuvu.dao.ICreditCardsDao;

@Service
public class CreditCardsServiceImpl implements ICreditCardsService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ICreditCardsDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<CreditCards> findAll() {
		return (List<CreditCards>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CreditCards findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM credit_cards WHERE credit_cards.id = ?1",
				CreditCards.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (CreditCards) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (CreditCards) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CreditCards> findByUser(Integer userId) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery(
				"SELECT * FROM credit_cards INNER JOIN customers WHERE customers.user = ?1", CreditCards.class);
		q.setParameter(1, userId);
		return q.getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public CreditCards validateExist(Integer customerId, Integer cardTypeId, long number) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery(
				"SELECT * FROM credit_cards WHERE credit_cards.customer_id = ?1 AND credit_cards.card_types_id = ?2  AND credit_cards.number = ?3", CreditCards.class);
		q.setParameter(1, customerId);
		q.setParameter(2, cardTypeId);
		q.setParameter(3, number);
		if (q.getResultList().size() == 1) {
			return (CreditCards) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (CreditCards) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM credit_cards WHERE credit_cards.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public CreditCards save(CreditCards object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}

}
