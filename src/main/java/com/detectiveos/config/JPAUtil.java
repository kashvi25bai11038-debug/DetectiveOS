package com.detectiveos.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public final class JPAUtil {
    private static EntityManagerFactory emf;
    private JPAUtil() {}
    public static synchronized EntityManagerFactory factory(){
        if (emf == null) {
            Map<String,Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", AppConfig.get("db.url"));
            props.put("jakarta.persistence.jdbc.user", AppConfig.get("db.username"));
            props.put("jakarta.persistence.jdbc.password", AppConfig.get("db.password"));
            props.put("hibernate.show_sql", AppConfig.get("hibernate.show_sql"));
            props.put("hibernate.format_sql", AppConfig.get("hibernate.format_sql"));
            props.put("hibernate.hbm2ddl.auto", AppConfig.get("hibernate.hbm2ddl.auto"));
            emf = Persistence.createEntityManagerFactory("detectivePU", props);
        }
        return emf;
    }
    public static EntityManager createEntityManager(){ return factory().createEntityManager(); }
    public static synchronized void close(){ if(emf!=null){emf.close();emf=null;} }
}
