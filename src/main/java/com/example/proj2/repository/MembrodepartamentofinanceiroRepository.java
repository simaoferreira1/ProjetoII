package com.example.proj2.repository;

import com.example.proj2.models.Membrodepartamentofinanceiro;
import com.example.proj2.models.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MembrodepartamentofinanceiroRepository {

    public Membrodepartamentofinanceiro findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Membrodepartamentofinanceiro> query = session.createQuery(
                    "FROM Membrodepartamentofinanceiro WHERE email = :email AND password = :password",
                    Membrodepartamentofinanceiro.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }

    public void save(Membrodepartamentofinanceiro membro) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(membro);
            tx.commit();
        }
    }

    public Membrodepartamentofinanceiro findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Membrodepartamentofinanceiro.class, id);
        }
    }

    public List<Membrodepartamentofinanceiro> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Membrodepartamentofinanceiro", Membrodepartamentofinanceiro.class).list();
        }
    }

    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Membrodepartamentofinanceiro membro = session.get(Membrodepartamentofinanceiro.class, id);
            if (membro != null) {
                session.delete(membro);
            }
            tx.commit();
        }
    }
}
