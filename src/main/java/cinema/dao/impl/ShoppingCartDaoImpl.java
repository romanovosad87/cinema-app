package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.ShoppingCartDao;
import cinema.exception.DataProcessingException;
import cinema.model.ShoppingCart;
import cinema.model.User;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl extends AbstractDao<ShoppingCart> implements ShoppingCartDao {
    public ShoppingCartDaoImpl(SessionFactory factory) {
        super(factory, ShoppingCart.class);
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User user = session.getReference(User.class, shoppingCart.getUser().getId());
            shoppingCart.setUser(user);
            session.persist(shoppingCart);
            transaction.commit();
            return shoppingCart;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert "
                    + ShoppingCart.class.getSimpleName() + " " + shoppingCart, e);
        } finally {
            if (Objects.nonNull(session)) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = factory.openSession()) {
            session.setDefaultReadOnly(true);
            return session.createQuery("""
                            SELECT DISTINCT sc FROM ShoppingCart sc
                            JOIN FETCH sc.user
                            LEFT JOIN FETCH sc.tickets t
                            LEFT JOIN FETCH t.movieSession ms
                            LEFT JOIN FETCH ms.cinemaHall
                            LEFT JOIN FETCH ms.movie
                            WHERE sc.user = :user""", ShoppingCart.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Not found shopping cart for user " + user, e);
        }
    }
}
