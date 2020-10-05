package co.nuvu.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.nuvu.models.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nuvu.dao.IRolesDao;

@Service
public class RolesServiceImpl implements IRolesService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IRolesDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Roles> findAll() {
		return (List<Roles>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Roles findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM roles WHERE roles.id = ?1", Roles.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (Roles) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Roles) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM roles WHERE roles.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public Roles save(Roles object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}

}
