package com.detectiveos.repository;
import com.detectiveos.config.JPAUtil;
import com.detectiveos.model.*;
import jakarta.persistence.EntityManager;
import java.util.List;
public class CaseRepository extends GenericRepository<CaseFile>{
    public CaseRepository(){super(CaseFile.class);}
    public List<CaseFile> findByStatus(CaseStatus status){try(EntityManager em=JPAUtil.createEntityManager()){return em.createQuery("from CaseFile c where c.status=:s order by c.crimeDate desc",CaseFile.class).setParameter("s",status).getResultList();}}
}
