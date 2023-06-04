package ru.romanstolov.spring.rest.pp_3_1_6_spring_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.romanstolov.spring.rest.pp_3_1_6_spring_rest.configs.MvcConfig;
import ru.romanstolov.spring.rest.pp_3_1_6_spring_rest.models.User;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
// ***************************************************************************************
// Эта строка была при генерации проекта на сайте Спринга
//		SpringApplication.run(Application.class, args);

// ***************************************************************************************
// Ниже весь код - это по курсам Трегулова по REST !!!!!!!!
//		AnnotationConfigApplicationContext context =
//				new AnnotationConfigApplicationContext(MvcConfig.class);
//		Communications communications = context.getBean("communications", Communications.class);
//		// Получение списка всех работников
//		List<User> userList = communications.getAllUsers();
//		System.out.println("***** Список всех работников: ");
//		for (User user: userList) {
//			System.out.println(user);
//		}
		// Получение работников по "id"
//		for (User user: userList) {
//			System.out.println("***** Получение работников по \"id\": ");
//			System.out.println(
//					"Работник с " + user.getId() + ": " + communications.getUserById(user.getId()));
//
//		}

// ***************************************************************************************
// Вот такой код я набирал в среде разработки через меню:
// "Tools"->"HTTP Client"->"Create Request in HTTP Client":
// GET http://94.198.50.185:7081/api/users
//Accept: application/json
//###
//POST http://94.198.50.185:7081/api/users
//Content-Type: application/json
//
//{
//  "id": 3,
//  "name": "James",
//  "lastName": "Brown",
//  "age": 25
//}
//###
//PUT http://94.198.50.185:7081/api/users
//Content-Type: application/json
//
//{
//  "id": 3,
//  "name": "Thomas",
//  "lastName": "Shelby",
//  "age": 25
//}
//###
//DELETE http://94.198.50.185:7081/api/users/3
//Content-Type: application/json
//###

// ***************************************************************************************
// Ниже весь код - это код по видео индусов для ручного получения ответа по задаче 1.3.6 !!!!!!!!
		// Получение "Cookie" из ответа на запрос списка пользователей
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://94.198.50.185:7081/api/users";
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url,
				HttpMethod.GET,
				requestEntity,
				String.class);
		HttpStatusCode httpStatusCode = responseEntity.getStatusCode();
		System.out.println("StatusCode: " + httpStatusCode);
		String bodyUsers = responseEntity.getBody();
		System.out.println("BodyUsers: " + bodyUsers);
		HttpHeaders httpHeaders1 = responseEntity.getHeaders();
		System.out.println("HttpHeaders: " + httpHeaders1);
		String cookie = httpHeaders1.getFirst("Set-Cookie");
		System.out.println("Cookie: " + cookie);

		// Создание нового заголовка (содержащего "Cookie") для составления следующих трёх запросов
		HttpHeaders httpHeaders2 = new HttpHeaders();
		httpHeaders2.add("Cookie", cookie);

		// Добавление нового пользователя с заданными параметрами (id=3,...)
		User userAdd = new User();
		userAdd.setId((long) 3);
		userAdd.setName("James");
		userAdd.setLastName("Brown");
		userAdd.setAge((byte) 30);
		HttpEntity<Object> requestEntityPost = new HttpEntity<>(userAdd, httpHeaders2);
		ResponseEntity<String> responseEntityPost = restTemplate.exchange(
				url,
				HttpMethod.POST,
				requestEntityPost,
				String.class
		);
		String answer1 = responseEntityPost.getBody();
		System.out.println("Первая часть ответа: " + answer1);

		// Внесение изменений в пользователя с id=3 (изменение имени и фамилии)
		User userEdit = new User();
		userEdit.setId((long) 3);
		userEdit.setName("Thomas");
		userEdit.setLastName("Shelby");
		userEdit.setAge((byte) 30);
		HttpEntity<Object> requestEntityPut = new HttpEntity<>(userEdit, httpHeaders2);
		ResponseEntity<String> responseEntityPut = restTemplate.exchange(
				url,
				HttpMethod.PUT,
				requestEntityPut,
				String.class
		);
		String answer2 = responseEntityPut.getBody();
		System.out.println("Вторая часть ответа: " + answer2);

		// Удаление пользователя под id=3 (задаём id в url)
		HttpEntity<Object> requestEntityDelete = new HttpEntity<>(userEdit, httpHeaders2);
		ResponseEntity<String> responseEntityDelete = restTemplate.exchange(
				url + "/3",
				HttpMethod.DELETE,
				requestEntityDelete,
				String.class
		);
		String answer3 = responseEntityDelete.getBody();
		System.out.println("Третья часть ответа: " + answer3);
		System.out.println("Итоговый ответ: " + answer1 + answer2 + answer3);
	}

}
