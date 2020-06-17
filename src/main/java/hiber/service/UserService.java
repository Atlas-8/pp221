package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import javax.persistence.NoResultException;
import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    List<User> listCarOwners();
    User carOwner(String name, int series) throws NoResultException;
}
