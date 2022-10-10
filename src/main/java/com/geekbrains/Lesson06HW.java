package com.geekbrains;

import com.geekbrains.persistence.entities.CartEntry;
import com.geekbrains.persistence.entities.Order;
import com.geekbrains.persistence.entities.Product;
import com.geekbrains.persistence.entities.User;
import com.geekbrains.persistence.repositories.ProductRepository;
import com.geekbrains.persistence.repositories.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Component
public class Lesson06HW {

    private final EntityManager em;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Lesson06HW(EntityManager em, UserRepository userRepository, ProductRepository productRepository) {
        this.em = em;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @PostConstruct
    private void homework() {
        User user;
        Product product;
        int numSelected;
        String str = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\n ТЕСТИРОВАНИЕ ЗАВИСИМОСТЕЙ (СВЯЗЕЙ) HIBERNATE (урок 6)");

        while(!str.equals("exit")) {
            System.out.println("\n----------------------------------------");
            System.out.println("1. Список товаров по ID покупателя");
            System.out.println("2. Список покупателей по ID товара");
            System.out.println("3. Завершить");
            System.out.print("Выберите действие: ");
            try {
                str = in.readLine();
                numSelected = Integer.parseInt(str);
                if (numSelected < 1 || numSelected > 3) throw new NumberFormatException();
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Неверное значение. Попробуйте снова...");
                continue;
            }

            System.out.println("\n----------------------------------------");

            switch (numSelected) {
                case 1:

                    System.out.print("Введите id покупателя: ");
                    try {
                        str = in.readLine();
                        Long userId = Long.parseLong(str);
                        user = userRepository.findById(userId);
                    } catch (Exception e) {
                        // e.printStackTrace();
                        System.out.println("ID покупателя указан неверно!");
                        continue;
                    }

                    if (user == null) {
                        System.out.println("Покупателя с указанным ID не существует!");
                        continue;
                    }
                    detailsByUser(user);
                    break;

                case 2:
                    System.out.print("Введите id товара: ");
                    try {
                        str = in.readLine();
                        Long prodId = Long.parseLong(str);
                        product = productRepository.findById(prodId);
                    } catch (Exception e) {
                        // e.printStackTrace();
                        System.out.println("ID товара указан неверно!");
                        continue;
                    }

                    if (product == null) {
                        System.out.println("Товара с таким ID нет в базе данных!");
                        continue;
                    }

                    detailsByProduct(product);
                    break;

                case 3:
                    str = "exit";
                    break;
            }
        }
    }

    private void detailsByUser(User user) {
        System.out.println("Пользователь: " + user);
        System.out.println("Количество заказов: " + user.getOrders().size());
        System.out.println("Список заказов: \n" + user.getOrders());
        System.out.println("Список заказанных товаров с детализацией на момент покупки:");
        for (Order order : user.getOrders()) {
            for (CartEntry cartEntry : order.getCartEntries()) {
                System.out.println("Товар: id = " + cartEntry.getProduct().getId() +
                        ", name = " + cartEntry.getProduct().getName() +
                        ", количество: " + cartEntry.getQuantity() +
                        ", цена на момент покупки: " + cartEntry.getAcquirePrice());
            }
        }
    }

    private void detailsByProduct(Product product) {
        System.out.println("Товар: " + product);
        System.out.println("Количество заказов: " + product.getOrders().size());
        Set<User> userList = new HashSet<>();
        for (Order order : product.getOrders()) {
            userList.add(order.getUser());
        }
        System.out.println("Количество покупателей: " + userList.size());
        System.out.println("Список покупателей: \n" + userList);
    }
}