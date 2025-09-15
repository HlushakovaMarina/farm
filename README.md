# Farm Management REST API

Spring Boot REST API для управления овощами, фруктами и фермами.

## Описание

Это приложение предоставляет RESTful API для управления тремя основными сущностями:
- **Farm (Ферма)** - фермы, которые выращивают овощи и фрукты
- **Vegetable (Овощ)** - овощи, принадлежащие фермам
- **Fruit (Фрукт)** - фрукты, принадлежащие фермам

## Технологии

- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (встроенная база данных)
- **Springdoc OpenAPI** (Swagger UI)
- **Spring Validation**
- **Java 17**

## Структура проекта

```
src/main/java/by/ilyushenko/farm/
├── entity/           # JPA сущности
│   ├── Farm.java
│   ├── Vegetable.java
│   └── Fruit.java
├── repository/       # Репозитории для работы с БД
│   ├── FarmRepository.java
│   ├── VegetableRepository.java
│   └── FruitRepository.java
├── service/          # Бизнес-логика с интерфейсами
│   ├── FarmServiceInterface.java
│   ├── FarmService.java
│   ├── VegetableServiceInterface.java
│   ├── VegetableService.java
│   ├── FruitServiceInterface.java
│   └── FruitService.java
├── controller/       # REST контроллеры
│   ├── FarmController.java
│   ├── VegetableController.java
│   ├── FruitController.java
│   └── HomeController.java
├── exception/        # Обработка ошибок
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── config/           # Конфигурация
│   ├── OpenApiConfig.java
│   └── DataInitializer.java
└── FarmApplication.java
```

## Архитектура приложения

Приложение построено по принципам **слоистой архитектуры** с использованием **интерфейсов**:

### Слои архитектуры:

1. **Controller Layer** - REST контроллеры, обрабатывают HTTP запросы
2. **Service Interface Layer** - интерфейсы сервисов, определяют контракты
3. **Service Implementation Layer** - реализации сервисов, содержат бизнес-логику
4. **Repository Layer** - репозитории для работы с базой данных
5. **Entity Layer** - JPA сущности, представляют данные
6. **Database Layer** - H2 встроенная база данных

### Преимущества использования интерфейсов:

- **Гибкость**: Легко заменить реализацию сервиса
- **Тестируемость**: Простое создание моков для unit-тестов
- **Разделение ответственности**: Четкое разделение контракта и реализации
- **Расширяемость**: Легко добавить новые реализации сервисов

## Запуск приложения

### Предварительные требования

- Java 17 или выше
- Gradle (или используйте встроенный gradlew)

### Команды для запуска

1. **Сборка проекта:**
   ```bash
   ./gradlew build
   ```

2. **Запуск приложения:**
   ```bash
   ./gradlew bootRun
   ```

3. **Или запуск через JAR:**
   ```bash
   ./gradlew build
   java -jar build/libs/farm-0.0.1-SNAPSHOT.jar
   ```

Приложение будет доступно по адресу: `http://localhost:8080`

## API Endpoints

### Farm API

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/farms` | Получить список всех ферм |
| GET | `/api/farms/{id}` | Получить ферму по ID |
| POST | `/api/farms` | Создать новую ферму |
| PUT | `/api/farms/{id}` | Обновить существующую ферму |
| DELETE | `/api/farms/{id}` | Удалить ферму (каскадно удаляет овощи и фрукты) |

### Vegetable API

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/vegetables` | Получить список всех овощей |
| GET | `/api/vegetables/{id}` | Получить овощ по ID |
| POST | `/api/vegetables?farmId={farmId}` | Создать новый овощ и привязать к ферме |
| PUT | `/api/vegetables/{id}?farmId={farmId}` | Обновить овощ (с возможностью сменить ферму) |
| DELETE | `/api/vegetables/{id}` | Удалить овощ |
| GET | `/api/vegetables/farm/{farmId}` | Получить овощи по ID фермы |

### Fruit API

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/fruits` | Получить список всех фруктов |
| GET | `/api/fruits/{id}` | Получить фрукт по ID |
| POST | `/api/fruits?farmId={farmId}` | Создать новый фрукт и привязать к ферме |
| PUT | `/api/fruits/{id}?farmId={farmId}` | Обновить фрукт (с возможностью сменить ферму) |
| DELETE | `/api/fruits/{id}` | Удалить фрукт |
| GET | `/api/fruits/farm/{farmId}` | Получить фрукты по ID фермы |

### Дополнительные endpoints

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/` | Главная страница с информацией об API |
| GET | `/health` | Проверка состояния приложения |

## Документация API

### Swagger UI
Интерактивная документация API доступна по адресу:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

### H2 Database Console
Встроенная консоль базы данных доступна по адресу:
- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## Примеры запросов

### Создание фермы
```bash
curl -X POST http://localhost:8080/api/farms \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Farm",
    "location": "California, USA"
  }'
```

### Создание овоща
```bash
curl -X POST "http://localhost:8080/api/vegetables?farmId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Carrot",
    "color": "Orange",
    "weight": 150.0
  }'
```

### Создание фрукта
```bash
curl -X POST "http://localhost:8080/api/fruits?farmId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Apple",
    "color": "Red",
    "weight": 250.0
  }'
```

## Валидация данных

Приложение включает валидацию для всех полей:

### Farm
- `name`: обязательно, длина 2-100 символов
- `location`: необязательно, максимум 255 символов

### Vegetable/Fruit
- `name`: обязательно, длина 2-50 символов
- `color`: необязательно, максимум 50 символов
- `weight`: обязательно, должно быть больше 0

## Обработка ошибок

API возвращает структурированные ошибки:

- **400 Bad Request**: Невалидные данные
- **404 Not Found**: Ресурс не найден
- **500 Internal Server Error**: Внутренняя ошибка сервера

Пример ответа с ошибкой:
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Farm name is required",
    "weight": "Weight must be greater than 0"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

## Начальные данные

При запуске приложения автоматически создаются тестовые данные:
- 3 фермы
- 5 овощей
- 5 фруктов

## Тестирование

Для тестирования API можно использовать:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Postman**
- **curl** команды
- **Любой HTTP клиент**

## Лицензия

MIT License
