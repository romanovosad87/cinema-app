package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.OrderDao;
import cinema.exception.DataProcessingException;
import cinema.model.Order;
import cinema.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    public OrderDaoImpl(SessionFactory factory) {
        super(factory, Order.class);
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        try (Session session = factory.openSession()) {
            session.setDefaultReadOnly(true);
            return session.createQuery("""
                            SELECT DISTINCT o FROM Order o
                            JOIN FETCH o.user
                            JOIN FETCH o.tickets t
                            JOIN FETCH t.movieSession ms
                            JOIN FETCH ms.cinemaHall
                            JOIN FETCH ms.movie
                            WHERE o.user = :user""", Order.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Not found shopping cart for user " + user, e);
        }
    }
}
