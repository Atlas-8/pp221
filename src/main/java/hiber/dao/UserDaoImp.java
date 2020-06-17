package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> listCarOwners() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where car != null");
      return query.getResultList();
   }

   @Override
   public User carOwner(String name, int series) throws NoResultException {
      String carQuerryString = "from Car where name =:name and series=:series";
      TypedQuery<Car> carQuery=sessionFactory.getCurrentSession().createQuery(carQuerryString);
      carQuery.setParameter("name", name);
      carQuery.setParameter("series", series);
      Long id = carQuery.setMaxResults(1).getSingleResult().getId();
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User where car ="+id);
      return query.getSingleResult();
   }

}
