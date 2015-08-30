package pl.vgtworld.test.ejbtransactions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.vgtworld.test.ejbtransactions.dao.EventsDao;
import pl.vgtworld.test.ejbtransactions.exceptions.MyEjbException;
import pl.vgtworld.test.ejbtransactions.exceptions.MyRuntimeException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class EventsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventsService.class);

	@EJB
	private EventsDao events;

	public void saveStartEvent(String name) {
		LOGGER.info("Save start event");
		events.createEvent("start " + name);
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

	public void throwRuntimeException(boolean state) {
		LOGGER.info("Throw runtime exception: {}", state);
		if (state) {
			throw new MyRuntimeException();
		}
	}

	public void saveFullFlowWithCatchedEjbException() {
		saveStartEvent("full flow with catched ejb exception");
		try {
			throwEjbException(true);
		} catch (MyEjbException e) {
			LOGGER.warn("Catched ejb exception");
		}
		saveEndEvent();
	}

	public void saveFullFlowWithCatchedRuntimeException() {
		saveStartEvent("full flow with catched runtime exception");
		try {
			throwRuntimeException(true);
		} catch (MyRuntimeException e) {
			LOGGER.warn("Catched runtime exception");
		}
		saveEndEvent();
	}

	public void saveIncorrectEventThrowingPersistenceException() {
		events.createEvent(new Date(), null);
	}

	public void saveFullFlowWithPersistenceExceptionCatchedInsideDaoAsLastOperation() {
		saveStartEvent("save full flow with persistence exception catched inside dao as last operation");
		events.createEventWithCatchingPersistenceException(new Date(), null);
	}

	public void saveFullFlowWithPersistenceExceptionCatchedInsideDaoAsMiddleOperation() {
		saveStartEvent("save full flow with persistence exception catched inside dao as middle operation");
		events.createEventWithCatchingPersistenceException(new Date(), null);
		saveEndEvent();
	}

}
