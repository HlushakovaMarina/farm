# Архитектура приложения Farm Management API

## Обзор

Приложение построено по принципам **слоистой архитектуры** с использованием **интерфейсов** для обеспечения гибкости, тестируемости и расширяемости.

## Слои архитектуры

### 1. Controller Layer (Слой контроллеров)
- **Назначение**: Обработка HTTP запросов и ответов
- **Компоненты**: 
  - `FarmController`
  - `VegetableController` 
  - `FruitController`
  - `HomeController`
- **Зависимости**: Использует интерфейсы сервисов

### 2. Service Interface Layer (Слой интерфейсов сервисов)
- **Назначение**: Определение контрактов для бизнес-логики
- **Компоненты**:
  - `FarmServiceInterface`
  - `VegetableServiceInterface`
  - `FruitServiceInterface`
- **Преимущества**: 
  - Четкое разделение контракта и реализации
  - Легкое создание моков для тестирования
  - Возможность замены реализаций

### 3. Service Implementation Layer (Слой реализаций сервисов)
- **Назначение**: Реализация бизнес-логики
- **Компоненты**:
  - `FarmService` (implements `FarmServiceInterface`)
  - `VegetableService` (implements `VegetableServiceInterface`)
  - `FruitService` (implements `FruitServiceInterface`)
- **Зависимости**: Использует репозитории для работы с данными

### 4. Repository Layer (Слой репозиториев)
- **Назначение**: Абстракция доступа к данным
- **Компоненты**:
  - `FarmRepository`
  - `VegetableRepository`
  - `FruitRepository`
- **Особенности**: Расширяют `JpaRepository` для автоматической реализации CRUD операций

### 5. Entity Layer (Слой сущностей)
- **Назначение**: Представление данных в объектном виде
- **Компоненты**:
  - `Farm` - фермы
  - `Vegetable` - овощи
  - `Fruit` - фрукты
- **Особенности**: 
  - JPA аннотации для маппинга на БД
  - Jackson аннотации для предотвращения циклических ссылок
  - Валидация данных

### 6. Database Layer (Слой базы данных)
- **Назначение**: Хранение данных
- **Технология**: H2 (встроенная база данных)
- **Особенности**: Автоматическое создание схемы при запуске

## Диаграмма зависимостей

```
Controllers
    ↓ (depends on)
Service Interfaces
    ↑ (implemented by)
Service Implementations
    ↓ (depends on)
Repositories
    ↓ (depends on)
Entities
    ↓ (mapped to)
Database
```

## Преимущества архитектуры с интерфейсами

### 1. Гибкость (Flexibility)
- Легко заменить реализацию сервиса без изменения контроллеров
- Возможность создания различных реализаций для разных сценариев

### 2. Тестируемость (Testability)
- Простое создание моков для unit-тестов
- Изоляция слоев при тестировании
- Возможность тестирования контроллеров без реальной БД

### 3. Разделение ответственности (Separation of Concerns)
- Четкое разделение контракта и реализации
- Каждый слой имеет свою ответственность
- Легкость понимания и поддержки кода

### 4. Расширяемость (Extensibility)
- Легко добавить новые реализации сервисов
- Возможность создания декораторов и прокси
- Поддержка различных стратегий (кэширование, логирование и т.д.)

## Примеры использования

### Создание мока для тестирования
```java
@MockBean
private FarmServiceInterface farmService;

@Test
void testGetFarm() {
    when(farmService.getFarmById(1L)).thenReturn(Optional.of(testFarm));
    // тест логика
}
```

### Замена реализации
```java
@Service("cachedFarmService")
public class CachedFarmService implements FarmServiceInterface {
    // реализация с кэшированием
}
```

### Создание декоратора
```java
@Service
public class LoggingFarmService implements FarmServiceInterface {
    private final FarmServiceInterface delegate;
    
    @Override
    public List<Farm> getAllFarms() {
        log.info("Getting all farms");
        return delegate.getAllFarms();
    }
}
```

## Заключение

Использование интерфейсов в архитектуре приложения значительно улучшает его качество, делая код более гибким, тестируемым и расширяемым. Это соответствует принципам SOLID и лучшим практикам разработки на Spring Boot.
