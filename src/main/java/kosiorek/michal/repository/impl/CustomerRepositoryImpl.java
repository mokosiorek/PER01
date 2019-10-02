package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.repository.CustomerRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractCrudRepository<Customer, Long> implements CustomerRepository {

    @Override
    public Optional<Customer> findCustomerByNameSurnameAndCountryName(String customerName, String customerSurname, String countryName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Customer> customerOp = Optional.empty();

        try {

            tx.begin();
            customerOp = entityManager
                    .createQuery("select c from Customer c join c.country ctr where c.name = :customerName and c.surname = :customerSurname and ctr.name = :countryName", Customer.class)
                    .setParameter("customerName", customerName)
                    .setParameter("countryName", countryName)
                    .setParameter("customerSurname", customerSurname)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find customer by name, surname and country name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return customerOp;

    }

    @Override
    public List<Customer> findAllFromCountry(String countryName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Customer> customers = Collections.emptyList();

        try {

            tx.begin();
            customers = entityManager
                    .createQuery("select c from Customer c join c.country ctr where ctr.name = :countryName", Customer.class)
                    .setParameter("countryName", countryName)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find customers by country name exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return customers;
    }

    @Override
    public List<Customer> findCustomersWhoOrderedAtLeastOneProductFromCountryOfOrigin() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<Customer> customers;

        try {

            tx.begin();
            customers = entityManager
                    .createQuery("select c from CustomerOrder co join  co.customer c join c.country c_ctr join co.product co_prdt join co_prdt.producer prdr join prdr.country prdr_country where c_ctr.name=prdr_country.name", Customer.class)
                    .getResultList();
            tx.commit();

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new AppException("find customers who ordered at least one product from country of origin exception");
        } finally {

            if (entityManager != null) {
                entityManager.close();
            }

        }

        return customers;

    }


}
