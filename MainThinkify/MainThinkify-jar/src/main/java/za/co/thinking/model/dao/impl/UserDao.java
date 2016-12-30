package za.co.thinking.model.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import za.co.thinking.model.security.User;

/**
 * File Name : UserDao.java Project Name : MainThinkify-jar
 *
 * @since Dec 20, 2016, 10:22:58 AM
 * @author Abel Chavanga <achavanga@fnb.co.za>
 *
 */
@Stateless
//@LocalBean
public class UserDao extends AbstractDao<User> {

    @PersistenceContext(unitName = "malaicha-pu")
    private EntityManager em;

    public UserDao() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> member = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(member).where(cb.equal(member.get("emailAddress"), email));
        return em.createQuery(criteria).getSingleResult();
    }

     public User findById (Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> member = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(member).where(cb.equal(member.get("id"), id));
        return em.createQuery(criteria).getSingleResult();
    }
}
