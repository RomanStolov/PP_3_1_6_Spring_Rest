package ru.romanstolov.spring.rest.pp_3_1_6_spring_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;
import ru.romanstolov.spring.rest.pp_3_1_6_spring_rest.models.User;

import java.util.List;

@Component
public class Communications {
    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    @Autowired
    public Communications(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                // Это вспомогательный класс цель которого - передача generic-типа
                new ParameterizedTypeReference<List<User>>() {
                });
        // Получаем полезную нагрузку - список всех работников payload (пэйлоад)
        List<User> allUsers = responseEntity.getBody();
        HttpHeaders responseEntityHeaders = responseEntity.getHeaders();
        System.out.println("Хеадерс: " + responseEntityHeaders);
        return allUsers;
    }

    public User getUserById(Long id) {
        User user = restTemplate.getForObject(
                URL + "/" + id,
                User.class
                );
        return user;
    }

    public void saveUser(User user) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                URL,
                user,
                // Тут указываем в каком виде нам будет возвращаться ответ - в виде джейсон-формата
                // в текстовом виде наш новосозданный работник
                String.class);
        System.out.print("Новый работник был добавлен в базу: ");
        System.out.println(responseEntity.getBody());

    }

    public void editUser(User user) {
        restTemplate.put(
                URL,
                user
        );
        System.out.println("Пользователь с id=" + user.getId() + " ,был добавлен в БД.");
    }

    public void deleteUserById(Long id) {
        restTemplate.delete(URL + "/" + id);
        System.out.println("Пользователь с id=" + id + " был удалён из БД.");

    }

}
