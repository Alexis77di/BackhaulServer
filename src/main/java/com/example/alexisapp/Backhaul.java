package com.example.alexisapp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class Backhaul {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(BackhaulData.class);


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
        BackhaulData p3 = new BackhaulData();
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(p1);
        session.save(p2);
        session.save(p3);
        session.getTransaction().commit();
    }
}
