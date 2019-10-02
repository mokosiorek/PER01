package kosiorek.michal.repository.generic;

import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.repository.connection.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {

    protected final EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();

    private final Class<T> entityType = ( Class<T> ) ((ParameterizedType)super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private final Class<ID> entityId = ( Class<ID> ) ((ParameterizedType)super.getClass().getGenericSuperclass()).getActualTypeArguments()[1];


    @Override
    public Optional<T> addOrUpdate(T t) {

        /*if ( t == null ) {
            throw new AppException("add or update - object is null");
        }*/

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<T> insertedElement = Optional.empty();

        try {

            tx.begin();
            insertedElement = Optional.of(entityManager.merge(t));
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("add or update exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

        return insertedElement;
    }

    @Override
    public Optional<T> findById(ID id) {

        /*if ( id == null ) {
            throw new AppException("find by id - object is null");
        }*/

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<T> element = Optional.empty();

        try {

            tx.begin();
            element = Optional.of(entityManager.find(entityType, id));
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("find by id exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

        return element;

    }

    @Override
    public List<T> findAll() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<T> elements = new ArrayList<>();

        try {

            tx.begin();
            // JPQL
            elements = entityManager
                    .createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                    .getResultList();
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("find all exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

        return elements;

    }

    @Override
    public void deleteById(ID id) {

        /*if ( id == null ) {
            throw new AppException("delete by id - id is null");
        }*/

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {

            tx.begin();
            T t = entityManager.find(entityType, id);
            entityManager.remove(t);
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("delete by id exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }

    }

    @Override
    public void deleteAll() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {

            tx.begin();
            List<T> elements = entityManager
                    .createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                    .getResultList();
            elements.forEach(entityManager::remove);
            tx.commit();

        } catch ( Exception e ) {

            if ( tx != null ) {
                tx.rollback();
            }

            throw new AppException("delete all exception");
        } finally {

            if ( entityManager != null ) {
                entityManager.close();
            }

        }
    }
}
