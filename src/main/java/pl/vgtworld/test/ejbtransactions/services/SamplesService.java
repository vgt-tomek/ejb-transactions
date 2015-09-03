package pl.vgtworld.test.ejbtransactions.services;

import pl.vgtworld.test.ejbtransactions.dao.SamplesDao;
import pl.vgtworld.test.ejbtransactions.entities.Sample;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SamplesService {

	@EJB
	private SamplesDao dao;

	public Sample findSingleSampleByTypeWithNoResultExceptionCatched(int type) {
		return dao.findSingleSampleWithCatchedNoResultException(type);
	}

	public Sample findSingleSampleByTypeWithNoUniqueResultExceptionCatched(int type) {
		return dao.findSingleSampleWithCatchedNoUniqueResultException(type);
	}
}
