package com.example.alexisapp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class Backhaul {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        Properties p = new Properties();
//load properties file
        try {
            p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfg.setProperties(p);
        cfg.addAnnotatedClass(BackhaulData.class);
// build session factory


        try (SessionFactory sessionFactory = cfg.buildSessionFactory()) {
            persist(sessionFactory);
            load(sessionFactory);
        }
    }

    private static void load(SessionFactory sessionFactory) {
        System.out.println("-- loading persons --");
        Session session = sessionFactory.openSession();
        List<BackhaulData> persons = session.createQuery("FROM BackhaulData", BackhaulData.class).list();
        persons.forEach(System.out::println);
        session.close();
    }

    private static void persist(SessionFactory sessionFactory) {
        BackhaulData p1 = new BackhaulData();
        BackhaulData p2 = new BackhaulData();
        System.out.println(p1);
        System.out.println(p2);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(p1);
        session.save(p2);
        session.getTransaction().commit();
    }
}
