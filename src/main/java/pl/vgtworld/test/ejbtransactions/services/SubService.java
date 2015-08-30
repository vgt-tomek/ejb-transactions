package pl.vgtworld.test.ejbtransactions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.vgtworld.test.ejbtransactions.exceptions.MyEjbException;
import pl.vgtworld.test.ejbtransactions.exceptions.MyRuntimeException;

import javax.ejb.Stateless;

@Stateless
public class SubService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubService.class);

	public void throwEjbException(boolean state) {
		LOGGER.info("Subservice throw ejb exception: {}", state);
		if (state) {
			throw new MyEjbException();
		}
	}

	public void throwRuntimeException(boolean state) {
		LOGGER.info("Subservice throw runtime exception: {}", state);
		if (state) {
			throw new MyRuntimeException();
		}
	}

}
