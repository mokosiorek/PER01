package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Country;
import kosiorek.michal.model.Trade;
import kosiorek.michal.repository.TradeRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractCrudRepository<Trade,Long> implements TradeRepository {
    @Override
    public Optional<Trade> findByName(String name) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Trade> tradeOp = Optional.empty();

        try {

            tx.begin();
            tradeOp = entityManager
                    .createQuery("select t from Trade t where t.name = :name", Trade.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("find trade by name exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

        return tradeOp;
    }
}
