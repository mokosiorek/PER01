package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.Shop;
import kosiorek.michal.repository.ShopRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractCrudRepository<Shop,Long> implements ShopRepository {
    @Override
    public Optional<Shop> findShopByNameAndCountryName(String shopName, String countryName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Shop> shopOp = Optional.empty();

        try {

            tx.begin();
            shopOp = entityManager
                    .createQuery("select s from Shop s join s.country ctr where s.name = :shopName and ctr.name = :countryName", Shop.class)
                    .setParameter("shopName", shopName)
                    .setParameter("countryName", countryName)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find shop by name and country name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return shopOp;

    }

}
