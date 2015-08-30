package pl.vgtworld.test.ejbtransactions.handlers;

import pl.vgtworld.test.ejbtransactions.services.EventsService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class MainHandler {

	private static final String RESPONSE = "OK";

	@EJB
	private EventsService events;

	@GET
	@Path("clean-flow")
	public String cleanFlow() {
		events.saveStartEvent();
		events.saveEndEvent();
		return RESPONSE;
	}
}
