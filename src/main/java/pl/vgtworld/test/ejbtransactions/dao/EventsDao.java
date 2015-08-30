package pl.vgtworld.test.ejbtransactions.dao;

import pl.vgtworld.test.ejbtransactions.entities.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Stateless
public class EventsDao {

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

}
