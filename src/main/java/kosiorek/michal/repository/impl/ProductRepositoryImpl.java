package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Producer;
import kosiorek.michal.model.Product;
import kosiorek.michal.repository.ProductRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractCrudRepository<Product,Long> implements ProductRepository {
    @Override
    public Optional<Product> findProductByNameCategoryAndProducerName(String productName, String categoryName, String producerName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Product> productOp = Optional.empty();

        try {

            tx.begin();
            productOp = entityManager
                    .createQuery("select p from Product p join p.category ctr join p.producer prd where p.name = :productName and ctr.name = :categoryName and prd.name = :producerName", Product.class)
                    .setParameter("producerName", producerName)
                    .setParameter("productName", productName)
                    .setParameter("categoryName", categoryName)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find product by name, category name and producer name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return productOp;

    }

    @Override
    public List<Product> findProductsOrderedByCustomerGroupedByProducer(String customerName, String customerSurname, String countryName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Product> products;

        try {

            tx.begin();
            /*products = entityManager
                    .createQuery("select p from Product p join p.producer prd join CustomerOrder co join co.product prdtco join co.customer cust join cust.country cntry where cust.name = :customerName and cust.surname = :customerSurname and cntry.name = :countryName and p.id=prdtco.id group by prd", Product.class)
                    .setParameter("customerName", customerName)
                    .setParameter("customerSurname", customerSurname)
                    .setParameter("countryName", countryName)
                    .getResultList();*/


            products= entityManager
                    .createQuery("select p from CustomerOrder co join co.product p join co.customer c join p.producer pr join c.country ctr  where c.name = :customerName and c.surname = :customerSurname and ctr.name = :countryName order by pr.name", Product.class)
                    .setParameter("customerName", customerName)
                    .setParameter("customerSurname", customerSurname)
                    .setParameter("countryName", countryName)
                    .getResultList();

            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find products by name, surname and country of customer and grouped by producer name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return products;

    }



}
