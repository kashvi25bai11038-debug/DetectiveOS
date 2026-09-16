package com.detectiveos.repository;
import com.detectiveos.config.JPAUtil;
import com.detectiveos.model.User;
import jakarta.persistence.EntityManager;
public class UserRepository extends GenericRepository<User>{
    public UserRepository(){super(User.class);}
    public User findByUsername(String username){try(EntityManager em=JPAUtil.createEntityManager()){var list=em.createQuery("from User u where u.username=:u",User.class).setParameter("u",username).getResultList();return list.isEmpty()?null:list.get(0);}}
}
