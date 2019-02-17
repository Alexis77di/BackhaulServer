package com.example.alexisapp;

import featureSelectionMetricsPackage.Entropy;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Backhaul {
    final static String edgeIp = "127.0.0.1";

    public static void main(String[] args) {
        parseTrain();
        try {
            WebSocketServer.send("test.csv", 15123);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(BackhaulData.class);

        try (SessionFactory sessionFactory = cfg.buildSessionFactory()) {
            persist(sessionFactory);
            load(sessionFactory);
        }
    }


    private static void parseTrain() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource("train");

        if (url == null) {
            return;
        }

        File[] files = new File(url.getFile()).listFiles();

        if (files == null) {
            return;
        }
        CSVFormat pFormat = CSVFormat.DEFAULT.withHeader("FILE", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4");
        try (CSVPrinter printer = pFormat.print(new File("test.csv"), Charset.defaultCharset())) {
            for (File file : files) {
                Reader in = new FileReader(file);
                List<Object> ob = new ArrayList<>(Entropy.calculate(in));
                ob.add(0, file.getName());
                printer.printRecord(ob);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load(SessionFactory sessionFactory) {
        System.out.println("-- loading data --");
        Session session = sessionFactory.openSession();
        List<BackhaulData> data = session.createQuery("FROM BackhaulData", BackhaulData.class).list();
        data.forEach(System.out::println);
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
