package co.nuvu.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.nuvu.models.Customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nuvu.dao.ICustomersDao;

@Service
public class CustomersServiceImpl implements ICustomersService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ICustomersDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Customers> findAll() {
		return (List<Customers>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Customers findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM customers WHERE customers.id = ?1", Customers.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (Customers) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Customers) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	
	@Override
	@Transactional(readOnly = true)
	public Customers findByDni(String dni) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM customers WHERE customers.dni = ?1", Customers.class);
		q.setParameter(1, dni);
		if (q.getResultList().size() == 1) {
			return (Customers) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Customers) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Customers findByUser(Integer userId) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM customers WHERE customers.user = ?1", Customers.class);
		q.setParameter(1, userId);
		if (q.getResultList().size() == 1) {
			return (Customers) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Customers) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM customers WHERE customers.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public Customers save(Customers object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}

}
