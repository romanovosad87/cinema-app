package cinema.dao;

import cinema.exception.DataProcessingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    private final Class<T> clazz;

    public AbstractDao(SessionFactory factory, Class<T> clazz) {
        this.factory = factory;
        this.clazz = clazz;
    }

    public T add(T t) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert "
                    + clazz.getSimpleName() + " " + t, e);
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
        }
    }

    public Optional<T> get(Long id) {
        try (Session session = factory.openSession()) {
            session.setDefaultReadOnly(true);
            return Optional.ofNullable(session.get(clazz, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get "
                    + clazz.getSimpleName() + ", id: " + id, e);
        }
    }

    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            session.setDefaultReadOnly(true);
            return session.createQuery("from " + clazz.getSimpleName(), clazz).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all "
                    + clazz.getSimpleName() + "s from db", e);
        }
    }

    public T update(T t) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update "
                    + clazz.getSimpleName() + " " + t, e);
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            T movieSession = session.get(clazz, id);
            session.remove(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete "
                    + clazz.getSimpleName() + " with id: " + id, e);
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
        }
    }
}
