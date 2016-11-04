package com.shtyka.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static HibernateUtil util = getHibernateUtil();
    private static Logger log = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = getSessionFactory();
    private static final ThreadLocal sessions = new ThreadLocal();


    HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            log.error("Initial SessionFactory creation failed. "+ e);
            System.exit(0);
        }
    }


    public static Session getSession(){
        Session session = (Session) sessions.get();
        if(session == null){
        session = sessionFactory.openSession();
        sessions.set(session);
        }
        return session;
    }
    public static synchronized HibernateUtil getHibernateUtil(){
        if (util == null){
            util = new HibernateUtil();
        }
        return util;
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
//    public static void closeSession() {
//        if (getSession() != null && getSession().isOpen()) {
//            log.info("Closing Session of this thread.");
//            getSession().close();
//        }
//        sessions.set(null);
//    }
}
