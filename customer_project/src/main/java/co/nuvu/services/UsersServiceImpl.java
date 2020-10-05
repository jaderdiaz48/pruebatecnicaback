package co.nuvu.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.nuvu.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nuvu.dao.IUsersDao;

@Service
public class UsersServiceImpl implements IUsersService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IUsersDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		return (List<Users>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Users findById(Integer id) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM users WHERE users.id = ?1", Users.class);
		q.setParameter(1, id);
		if (q.getResultList().size() == 1) {
			return (Users) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Users) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional
	public Users findByUser(String user) {
		em.clear();
		javax.persistence.Query q = em.createNativeQuery("SELECT * FROM users WHERE users.user_name = ?1", Users.class);
		q.setParameter(1, user);
		if (q.getResultList().size() == 1) {
			return (Users) q.getSingleResult();
		} else {
			if (q.getResultList().size() > 1) {
				return (Users) q.getResultList().get(0);
			} else {
				return null;
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		javax.persistence.Query q = em.createNativeQuery("DELETE FROM users WHERE users.id = ?1");
		q.setParameter(1, id);
		q.executeUpdate();
	}

	@Override
	@Transactional
	public Users save(Users object) {
		// TODO Auto-generated method stub
		return dao.save(object);
	}


}
