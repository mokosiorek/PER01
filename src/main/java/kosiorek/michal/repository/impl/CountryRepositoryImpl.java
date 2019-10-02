package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Country;
import kosiorek.michal.repository.CountryRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Long> implements CountryRepository {

    @Override
    public Optional<Country> findByName(String name) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Country> countryOp = Optional.empty();

        try {

            tx.begin();
            countryOp = entityManager
                    .createQuery("select c from Country c where c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("find country by name exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

        return countryOp;

    }
}
