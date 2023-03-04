package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.MovieSessionDao;
import cinema.exception.DataProcessingException;
import cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl extends AbstractDao<MovieSession> implements MovieSessionDao {
    public MovieSessionDaoImpl(SessionFactory factory) {
        super(factory, MovieSession.class);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            session.setDefaultReadOnly(true);
            return session.createQuery("""
                            FROM MovieSession ms
                            JOIN FETCH ms.movie
                            JOIN FETCH ms.cinemaHall
                            WHERE ms.movie.id = :id
                            AND DATE_FORMAT(ms.showTime, '%Y-%m-%d') = :date""", MovieSession.class)
                    .setParameter("id", movieId)
                    .setParameter("date", date.toString())
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Session for movie with id "
                    + movieId + " and show date " + date + " not found", e);
        }
    }
}
