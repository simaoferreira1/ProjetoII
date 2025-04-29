package com.example.proj2.repository;

import com.example.proj2.models.Especialista;
import com.example.proj2.models.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public class EspecialistaRepository {

    public Especialista findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Especialista> query = session.createQuery(
                    "FROM Especialista WHERE email = :email AND password = :password", Especialista.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }

    public void save(Especialista especialista) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(especialista);
            tx.commit();
        }
    }

    public Especialista findById(BigDecimal id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Especialista.class, id);
        }
    }

    public List<Especialista> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Especialista", Especialista.class).list();
        }
    }

    public void deleteById(BigDecimal id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Especialista especialista = session.get(Especialista.class, id);
            if (especialista != null) {
                session.delete(especialista);
            }
            tx.commit();
        }
    }

    public boolean existsByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(e) FROM Especialista e WHERE e.email = :email", Long.class);
            query.setParameter("email", email);
            return query.uniqueResult() > 0;
        }
    }
}
