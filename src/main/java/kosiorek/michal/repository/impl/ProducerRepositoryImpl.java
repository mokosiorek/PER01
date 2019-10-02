package kosiorek.michal.repository.impl;

import kosiorek.michal.dto.ProducerDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.Producer;
import kosiorek.michal.model.Product;
import kosiorek.michal.repository.ProducerRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProducerRepositoryImpl extends AbstractCrudRepository<Producer,Long> implements ProducerRepository {
    @Override
    public Optional<Producer> findProducerByNameTradeAndCountryName(String producerName, String tradeName, String countryName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Producer> producerOp = Optional.empty();

        try {

            tx.begin();
            producerOp = entityManager
                    .createQuery("select p from Producer p join p.country ctr join p.trade trd where p.name = :producerName and ctr.name = :countryName and trd.name = :tradeName", Producer.class)
                    .setParameter("producerName", producerName)
                    .setParameter("countryName", countryName)
                    .setParameter("tradeName", tradeName)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find producer by name, trade name and country name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return producerOp;

    }
}
