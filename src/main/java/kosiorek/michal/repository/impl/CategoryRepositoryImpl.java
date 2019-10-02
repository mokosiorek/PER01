package kosiorek.michal.repository.impl;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Category;
import kosiorek.michal.model.Country;
import kosiorek.michal.repository.CategoryRepository;
import kosiorek.michal.repository.generic.AbstractCrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class CategoryRepositoryImpl extends AbstractCrudRepository<Category, Long> implements CategoryRepository {
    @Override
    public Optional<Category> findByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Category> categoryOp = Optional.empty();

        try {

            tx.begin();
            categoryOp = entityManager
                    .createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("find category by name exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

        return categoryOp;
    }
}
