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
	/**
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-1) Save start event
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-1) Save end event
	 */
	public String cleanFlow() {
		events.saveStartEvent("clean flow");
		events.saveEndEvent();
		return RESPONSE;
	}

	@GET
	@Path("test-2")
	public String ejbExceptionBetweenCalls() {
		events.saveStartEvent("ejb exception between calls"); //rollback
		events.throwEjbException(true);
		/**
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-41) Save start event
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-41) Throw ejb exception: true
		 * ERROR [org.jboss.as.ejb3] (default task-41) javax.ejb.EJBTransactionRolledbackException
		 * ERROR [org.jboss.as.ejb3.invocation] (default task-41) JBAS014134: EJB Invocation failed on component
		 *       EventsService for method public void
		 *       pl.vgtworld.test.ejbtransactions.services.EventsService.throwEjbException(boolean):
		 *       javax.ejb.EJBTransactionRolledbackException
		 * Caused by: pl.vgtworld.test.ejbtransactions.exceptions.MyEjbException
		 */
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-3")
	public String catchedEjbExceptionBetweenCalls() {
		events.saveStartEvent("catched ejb exception between calls"); //rollback
		try {
			events.throwEjbException(true);
			/**
			 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-40) Save start event
			 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-40) Throw ejb exception: true
			 * ERROR [org.jboss.as.ejb3] (default task-40) javax.ejb.EJBTransactionRolledbackException
			 * ERROR [org.jboss.as.ejb3.invocation] (default task-40) JBAS014134: EJB Invocation failed on component
			 *       EventsService for method public void
			 *       pl.vgtworld.test.ejbtransactions.services.EventsService.throwEjbException(boolean):
			 *       javax.ejb.EJBTransactionRolledbackException
			 * Caused by: pl.vgtworld.test.ejbtransactions.exceptions.MyEjbException
			 */
		} catch (MyEjbException e) {
			LOGGER.warn("Catched ejb exception"); //not executed
		}
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-4")
	public String runtimeExceptionBetweenCalls() {
		events.saveStartEvent("runtime exception between calls"); //rollback
		events.throwRuntimeException(true);
		/**
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-39) Save start event
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-39) Throw runtime exception: true
		 * ERROR [org.jboss.as.ejb3] (default task-39) javax.ejb.EJBTransactionRolledbackException
		 * ERROR [org.jboss.as.ejb3.invocation] (default task-39) JBAS014134: EJB Invocation failed on component
		 *       EventsService for method public void
		 *       pl.vgtworld.test.ejbtransactions.services.EventsService.throwRuntimeException(boolean):
		 *       javax.ejb.EJBTransactionRolledbackException
		 * Caused by: pl.vgtworld.test.ejbtransactions.exceptions.MyRuntimeException
		 */
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-5")
	public String catchedRuntimeExceptionBetweenCalls() {
		events.saveStartEvent("catched runtime exception between calls"); //rollback
		try {
			events.throwRuntimeException(true);
			/**
			 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-37) Save start event
			 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-37) Throw runtime exception: true
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

	@GET
	@Path("test-6")
	/**
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-9) Save start event
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-9) Throw ejb exception: true
	 * WARN  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-9) Catched ejb exception
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-9) Save end event
	 */
	public String fullFlowWitchCatchedEjbExceptionInside() {
		events.saveFullFlowWithCatchedEjbException();
		return RESPONSE;
	}

	@GET
	@Path("test-7")
	/**
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-10) Save start event
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-10) Throw runtime exception: true
	 * WARN  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-10) Catched runtime exception
	 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-10) Save end event
	 */
	public String fullFlowWitchCatchedRuntimeExceptionInside() {
		events.saveFullFlowWithCatchedRuntimeException();
		return RESPONSE;
	}

	@GET
	@Path("test-8")
	public String persistenceExceptionBetweenCalls() {
		events.saveStartEvent("persistence exception between calls"); //rollback
		events.saveIncorrectEventThrowingPersistenceException();
		/**
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-11) Save start event
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-11) Save incorrect event
		 * WARN  [org.hibernate.engine.jdbc.spi.SqlExceptionHelper] (default task-11) SQL Error: 1048, SQLState: 23000
		 * ERROR [org.hibernate.engine.jdbc.spi.SqlExceptionHelper] (default task-11) Column 'event_name' cannot be null
		 * ERROR [org.jboss.as.ejb3] (default task-11)
		 *       javax.ejb.EJBTransactionRolledbackException: org.hibernate.exception.ConstraintViolationException:
		 *       could not execute statement
		 * ERROR [org.jboss.as.ejb3.invocation] (default task-11) JBAS014134: EJB Invocation failed on component
		 *       EventsDao for method public void
		 *       pl.vgtworld.test.ejbtransactions.dao.EventsDao.createEvent(java.util.Date,java.lang.String):
		 *       javax.ejb.EJBTransactionRolledbackException: org.hibernate.exception.ConstraintViolationException:
		 *       could not execute statement
		 * Caused by: javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException:
		 *            could not execute statement
		 * Caused by: org.hibernate.exception.ConstraintViolationException: could not execute statement
		 * Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
		 *            Column 'event_name' cannot be null
		 */
		events.saveEndEvent(); //not executed
		return RESPONSE;
	}

	@GET
	@Path("test-9")
	public String fullFlowWithPersistenceExceptionCatchedInsideDaoAsLastOperation() {
		events.saveFullFlowWithPersistenceExceptionCatchedInsideDaoAsLastOperation(); //rollback
		/**
		 * Despite no stack trace in logs transaction is rolled back.
		 *
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-14) Save start event
		 * WARN  [org.hibernate.engine.jdbc.spi.SqlExceptionHelper] (default task-14) SQL Error: 1048, SQLState: 23000
		 * ERROR [org.hibernate.engine.jdbc.spi.SqlExceptionHelper] (default task-14) Column 'event_name' cannot be null
		 * WARN  [pl.vgtworld.test.ejbtransactions.dao.EventsDao] (default task-14) Catched persistence exception
		 */
		return RESPONSE;
	}

	@GET
	@Path("test-10")
	public String fullFlowWithPersistenceExceptionCatchedInsideDaoAsMidleOperation() {
		events.saveFullFlowWithPersistenceExceptionCatchedInsideDaoAsMiddleOperation(); //rollback
		/**
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-15) Save start event
		 * WARN  [org.hibernate.engine.jdbc.spi.SqlExceptionHelper] (default task-15) SQL Error: 1048, SQLState: 23000
		 * ERROR [org.hibernate.engine.jdbc.spi.SqlExceptionHelper] (default task-15) Column 'event_name' cannot be null
		 * WARN  [pl.vgtworld.test.ejbtransactions.dao.EventsDao] (default task-15) Catched persistence exception
		 * INFO  [pl.vgtworld.test.ejbtransactions.services.EventsService] (default task-15) Save end event
		 * ERROR [org.jboss.as.ejb3] (default task-15) javax.ejb.EJBTransactionRolledbackException: JBAS011469:
		 *       Transaction is required to perform this operation (either use a transaction or extended persistence context)
		 * ERROR [org.jboss.as.ejb3.invocation] (default task-15) JBAS014134: EJB Invocation failed on component EventsDao
		 *       for method public void pl.vgtworld.test.ejbtransactions.dao.EventsDao.createEvent(java.lang.String):
		 *       javax.ejb.EJBTransactionRolledbackException: JBAS011469: Transaction is required to perform this operation
		 *       (either use a transaction or extended persistence context)
		 * Caused by: javax.persistence.TransactionRequiredException: JBAS011469: Transaction is required to perform
		 *            this operation (either use a transaction or extended persistence context)
		 */
		return RESPONSE;
	}
}
