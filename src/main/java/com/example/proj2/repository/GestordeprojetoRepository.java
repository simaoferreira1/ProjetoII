package com.example.proj2.repository;

import com.example.proj2.models.Gestordeprojeto;
import com.example.proj2.models.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class GestordeprojetoRepository {

    // Login
    public Gestordeprojeto findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Gestordeprojeto> query = session.createQuery(
                    "FROM Gestordeprojeto WHERE email = :email AND password = :password", Gestordeprojeto.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }

    // Guardar ou atualizar
    public void save(Gestordeprojeto gestor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(gestor);
            tx.commit();
        }
    }

    // Buscar por ID
    public Gestordeprojeto findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Gestordeprojeto.class, id);
        }
    }

    // Verificar se existe por ID
    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    // Buscar todos os gestores
    public List<Gestordeprojeto> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Gestordeprojeto", Gestordeprojeto.class).list();
        }
    }

    // Apagar por ID
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Gestordeprojeto gestor = session.get(Gestordeprojeto.class, id);
            if (gestor != null) {
                session.delete(gestor);
            }
            tx.commit();
        }
    }
}
