package co.nuvu.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.nuvu.models.CardTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nuvu.dao.ICardTypesDao;

@Service
public class CardTypesServiceImpl implements ICardTypesService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ICardTypesDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<CardTypes> findAll() {
		return (List<CardTypes>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CardTypes findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM card_types WHERE card_types.id = ?1", CardTypes.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (CardTypes) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (CardTypes) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM card_types WHERE card_types.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public CardTypes save(CardTypes object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}

}
