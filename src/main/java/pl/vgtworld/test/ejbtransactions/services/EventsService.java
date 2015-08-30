package pl.vgtworld.test.ejbtransactions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.vgtworld.test.ejbtransactions.dao.EventsDao;
import pl.vgtworld.test.ejbtransactions.exceptions.MyEjbException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EventsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventsService.class);

	@EJB
	private EventsDao events;

	public void saveStartEvent() {
		LOGGER.info("Save start event");
		events.createEvent("start");
	}

	public void saveEndEvent() {
		LOGGER.info("Save end event");
		events.createEvent("end");
	}

	public void throwEjbException(boolean state) {
		LOGGER.info("Throw ejb exception: {}", state);
		if (state) {
			throw new MyEjbException();
		}
	}
}
