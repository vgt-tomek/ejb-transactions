package pl.vgtworld.test.ejbtransactions.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.vgtworld.test.ejbtransactions.entities.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Date;

@Stateless
public class EventsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventsDao.class);

	@PersistenceContext
	private EntityManager em;

	public void createEvent(String name) {
		createEvent(new Date(), name);
	}

	public void createEvent(Date createdAt, String name) {
		Event event = new Event();
		event.setCreatedAt(createdAt);
		event.setEventName(name);
		em.persist(event);
	}

	public void createEventWithCatchingPersistenceException(Date createdAt, String name) {
		try {
			createEvent(createdAt, name);
		} catch (PersistenceException e) {
			LOGGER.warn("Catched persistence exception");
		}
	}

}
