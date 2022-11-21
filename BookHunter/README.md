# 1 этап BookHunter

### System prob
1. Реализовал требуемые эндпоинты в контроллере [SystemController](./src/main/java/ru/tinkoff/academy/bookhunter/controller/SystemController.java)
2. Добавил Swagger в pom-файл и добавил системный тэг к контроллеру

Посмотреть документацию можно по ссылке 
```
http://localhost:8080/swagger-ui.html
```

### User Profile
1. Реализовал CRUD эндпоинты для UserProfile с помощью контроллера 
[UserProfileController](./src/main/java/ru/tinkoff/academy/bookhunter/controller/UserProfileController.java),
сервиса [UserProfileService](./src/main/java/ru/tinkoff/academy/bookhunter/service/UserProfileService.java), а хранение сделал
с помощью обернутого HashMap в [UserProfileMapRepository](./src/main/java/ru/tinkoff/academy/bookhunter/repo/UserProfileMapRepository.java)
2. Создал контроллер [UserNearestController](./src/main/java/ru/tinkoff/academy/bookhunter/controller/UserNearestController.java)
для обработки эндпоинтов для поиска ближайших UserProfile, создал сервис [EarthDistanceService](./src/main/java/ru/tinkoff/academy/bookhunter/service/EarthDistanceService.java)
для поиска расстояний между двумя объектами по их широтам и долготам, сервис для расчета логики эндпоинтов 
[UserNearestService](./src/main/java/ru/tinkoff/academy/bookhunter/service/UserNearestService.java)

