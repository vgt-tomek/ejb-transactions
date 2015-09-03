package pl.vgtworld.test.ejbtransactions.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.vgtworld.test.ejbtransactions.entities.Sample;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SamplesDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(SamplesDao.class);

	@PersistenceContext
	private EntityManager em;

	public Sample findSingleSampleWithCatchedNoResultException(int type) {
		try {
			return findSingleSampleByType(type);
		} catch (NoResultException e) {
			LOGGER.warn("No result exception for type: {}", type);
			return null;
		}
	}

	public Sample findSingleSampleWithCatchedNoUniqueResultException(int type) {
		try {
			return findSingleSampleByType(type);
		} catch (NonUniqueResultException e) {
			LOGGER.warn("No unique result exception for type: {}", type);
			return null;
		}
	}

	private Sample findSingleSampleByType(int type) {
		Query query = em.createNamedQuery(Sample.QUERY_FIND_BY_TYPE);
		query.setParameter("TYPE", type);
		return (Sample) query.getSingleResult();
	}

}
