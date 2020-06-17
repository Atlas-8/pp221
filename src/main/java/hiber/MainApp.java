package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));
      userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("volvo", 737)));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("volvo", 626)));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));

      List<User> users = userService.listUsers();

      System.out.println();
      System.out.println("List of users");
      System.out.println("_____________________________");

      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         if (user.getCar() != null) {
            System.out.println("Car = " + user.getCar().getName() + " " + user.getCar().getSeries());
         }
         System.out.println();
      }

      List<User> carOwners = userService.listCarOwners();
      System.out.println();
      System.out.println("List of car owners");
      System.out.println("_____________________________");
      for (User carOwner : carOwners) {
         System.out.println("Id = "+carOwner.getId());
         System.out.println("First Name = "+carOwner.getFirstName());
         System.out.println("Last Name = "+carOwner.getLastName());
         System.out.println("Email = "+carOwner.getEmail());
         System.out.println("Car = " + carOwner.getCar().getName() + " " + carOwner.getCar().getSeries());
         System.out.println();
      }

      System.out.println();
      System.out.println("Single car owner (in case of collision returnes first result)");
      System.out.println("_____________________________");
      try {
         User carOwner = userService.carOwner("volvo", 737);
         System.out.println("Car = " + carOwner.getCar().getName() + " " + carOwner.getCar().getSeries());
         System.out.println("User id = "+carOwner.getId());
         System.out.println("First Name = "+carOwner.getFirstName());
         System.out.println("Last Name = "+carOwner.getLastName());
         System.out.println("Email = "+carOwner.getEmail());
         System.out.println();
      } catch (NoResultException e){
         System.out.println("Car owner didn't exist, check the request parameters");
      }
      context.close();
   }
}
