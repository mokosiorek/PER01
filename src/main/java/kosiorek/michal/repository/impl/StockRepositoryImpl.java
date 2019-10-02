package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Producer;
import kosiorek.michal.model.Product;
import kosiorek.michal.model.Stock;
import kosiorek.michal.repository.StockRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockRepositoryImpl extends AbstractCrudRepository<Stock, Long> implements StockRepository {
    @Override
    public Optional<Stock> findByProductNameAndShop(String productName, String shopName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Stock> stockOp = Optional.empty();

        try {

            tx.begin();
            stockOp = entityManager
                    .createQuery("select s from Stock s join s.product prd join s.shop shp where prd.name = :productName and shp.name = :shopName", Stock.class)
                    .setParameter("productName", productName)
                    .setParameter("shopName", shopName)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find stock by product name, shop name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return stockOp;


    }

    @Override
    public void findByIdAndAddToQuantity(Long id, Integer amount) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {

            tx.begin();
            entityManager
                    .createNativeQuery("update stocks set quantity = quantity + ? where id = ?", Stock.class)
                    .setParameter(1, amount)
                    .setParameter(2, id)
                    .executeUpdate();

            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("add to stock quantity exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

    }

    @Override
    public List<Stock> findAllStocksWithProduct(String productName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Stock> stocks = new ArrayList<>();

        try {

            tx.begin();
            stocks = entityManager
                    .createQuery("select s from Stock s join s.product prd where prd.name = :productName", Stock.class)
                    .setParameter("productName", productName)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find stocks by product name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return stocks;

    }

    @Override
    public List<Stock> findAllStocksWithProductInCountry(String productName, String countryName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Stock> stocks;

        try {

            tx.begin();
            stocks = entityManager
                    .createQuery("select s from Stock s join s.product prd join s.shop shp where prd.name = :productName and shp.country.name = :countryName order by s.quantity DESC", Stock.class)
                    .setParameter("productName", productName)
                    .setParameter("countryName", countryName)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find stock by product name, shop country, ordered by quantity desc");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return stocks;


    }

    @Override
    public List<Stock> findAllStocksWithProductOutsideCountry(String productName, String countryName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Stock> stocks;

        try {

            tx.begin();
            stocks = entityManager
                    .createQuery("select s from Stock s join s.product prd join s.shop shp where prd.name = :productName and shp.country.name <> :countryName ORDER BY s.quantity DESC", Stock.class)
                    .setParameter("productName", productName)
                    .setParameter("countryName", countryName)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find stock by product name, outside country, ordered by quantity desc exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return stocks;
    }

    @Override
    public List<Stock> findStocksWithProductsFromOtherCountries() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Stock> stocks;

        try {

            tx.begin();
            stocks = entityManager
                    .createQuery("select distinct s from Stock s join s.product pr join pr.producer prd join s.shop sh join prd.country pr_c join sh.country s_c where pr_c.id <> s_c.id", Stock.class)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find stocks with products from other countries name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return stocks;

    }

    @Override
    public List<Stock> findStocksWithProductProducerTradeNameGiven(String tradeName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Stock> stocks;

        try {

            tx.begin();
            stocks = entityManager
                    .createQuery("select s from Stock s join s.product pr join pr.producer prd join prd.trade tr where tr.name=:tradeName", Stock.class)
                    .setParameter("tradeName",tradeName)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find stocks with products from other countries name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return stocks;


    }

}
