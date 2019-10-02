package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.model.Product;
import kosiorek.michal.repository.CustomerOrderRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerOrderRepositoryImpl extends AbstractCrudRepository<CustomerOrder, Long> implements CustomerOrderRepository {

    @Override
    public List<CustomerOrder> findAllOrdersWithProductsOfMaxPriceInCategories() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<CustomerOrder> customerOrders;

        try {

            tx.begin();
            //noinspection unchecked
            customerOrders = entityManager
                    .createNativeQuery("SELECT * FROM per01.customer_orders c " +
                            "where c.product_id in ( " +
                            "SELECT  p.id  " +
                            "FROM    products AS p " +
                            "        JOIN " +
                            "        ( " +
                            "            SELECT category_id, MAX(price) maxPrice " +
                            "            FROM products AS max_pr_price_in_cat " +
                            "            GROUP BY category_id " +
                            "        ) c ON p.category_id = c.category_id AND " +
                            "                p.price = c.maxPrice" +
                            ")",
                            CustomerOrder.class)
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

        return customerOrders;

    }

    @Override
    public List<CustomerOrder> findAllOrdersOrderedByCustomersFromCountryAndOfAgeBetween(String countryName, Integer ageFrom, Integer ageTo) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<CustomerOrder> customerOrders;

        try {

            tx.begin();
            customerOrders = entityManager
                    .createQuery("select co from CustomerOrder co join co.product prd join co.customer cst where cst.country.name = :countryName and cst.age>=:ageFrom and cst.age<=:ageTo", CustomerOrder.class)
                    .setParameter("countryName", countryName)
                    .setParameter("ageFrom", ageFrom)
                    .setParameter("ageTo", ageTo)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find Orders Ordered By Customers From Country And Of Age Between exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return customerOrders;

    }

    @Override
    public List<CustomerOrder> findOrdersWithDateBetweenAndTotalPriceAbove(LocalDate dateFrom, LocalDate dateTo, BigDecimal amount) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<CustomerOrder> customerOrders;

        try {

            tx.begin();
            customerOrders = entityManager
                    //.createQuery("select co from CustomerOrder co join co.product prd where co.date >= :dateFrom AND co.date <= :dateTo and co.quantity*(prd.price-prd.price*co.discount)>:amount", CustomerOrder.class)
                    .createQuery("select co from CustomerOrder co join co.product prd where co.date BETWEEN :dateFrom AND :dateTo and co.quantity*(prd.price-prd.price*co.discount)>:amount", CustomerOrder.class)
                    .setParameter("amount", amount)
                    .setParameter("dateFrom", dateFrom)
                    .setParameter("dateTo", dateTo)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find Orders With Date Between And Total Price Above exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return customerOrders;


    }

    @Override
    public List<CustomerOrder> findOrdersWithTradeNameOfProducerGiven(String tradeName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<CustomerOrder> customerOrders;

        try {

            tx.begin();
            customerOrders = entityManager
                    .createQuery("select co from CustomerOrder co join co.product prd join prd.producer prdr join prdr.trade tr where tr.name=:tradeName", CustomerOrder.class)
                    .setParameter("tradeName", tradeName)

                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find Orders With Trade Name Of Producer Given exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return customerOrders;

    }

}
