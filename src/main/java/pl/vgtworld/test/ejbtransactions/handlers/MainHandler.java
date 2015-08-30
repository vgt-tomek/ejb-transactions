package pl.vgtworld.test.ejbtransactions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.vgtworld.test.ejbtransactions.exceptions.MyEjbException;
import pl.vgtworld.test.ejbtransactions.exceptions.MyRuntimeException;
import pl.vgtworld.test.ejbtransactions.services.EventsService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@Stateless
public class MainHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainHandler.class);

	private static final String RESPONSE = "OK";

	@EJB
	private EventsService events;

	@GET
	@Path("test-1")
	public String cleanFlow() {
		events.saveStartEvent();
		events.saveEndEvent();
		return RESPONSE;
	}

	@GET
	@Path("test-2")
	public String ejbExceptionBetweenCalls() {
		events.saveStartEvent(); //rollback
		events.throwEjbException(true);
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-3")
	public String catchedEjbExceptionBetweenCalls() {
		events.saveStartEvent(); //rollback
		try {
			events.throwEjbException(true);
		} catch (MyEjbException e) {
			LOGGER.warn("Catched ejb exception"); //not executed
		}
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-4")
	public String runtimeExceptionBetweenCalls() {
		events.saveStartEvent(); //rollback
		events.throwRuntimeException(true);
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-5")
	public String catchedRuntimeExceptionBetweenCalls() {
		events.saveStartEvent(); //rollback
		try {
			events.throwRuntimeException(true);
			/**
			 * ERROR [org.jboss.as.ejb3] (default task-37) javax.ejb.EJBTransactionRolledbackException
			 * ERROR [org.jboss.as.ejb3.invocation] (default task-37) JBAS014134: EJB Invocation failed on component
			 *       EventsService for method public void
			 *       pl.vgtworld.test.ejbtransactions.services.EventsService.throwRuntimeException(boolean):
			 *       javax.ejb.EJBTransactionRolledbackException
			 * Caused by: pl.vgtworld.test.ejbtransactions.exceptions.MyRuntimeException
			 */
		} catch (MyRuntimeException e) {
			LOGGER.warn("Catched runtime exception"); //not executed
		}
		events.saveEndEvent();
		return RESPONSE;
	}

}
