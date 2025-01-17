
# Book-storage API


## Описание приложения
Проект является практической работой 5 модуля курса "Разработка на Spring Framework" 
образовательной платформы Skillbox.

Проект представляет собой REST API приложение, реализующее функции для управления хранилищем данных о книгах, которое через HTTP-запросы позволяет:
- находить книги по их названию и автору;
- находить список книг по имени категории к которой они относятся;
- создавать книги;
- обновлять информацию о книге;
- удалять книгу по ID.

### Описание логики работы сервиса:
1. Возврат данных о книгах осуществляется соответствующими запросами по названию книги и ее автору или по названию категории:
  - **/api/book/by_category?category=Рассказы**
  - **/api/book/by_name_and_author?name=Детство&author=Толстой**
2. При выполнении запросов на получение списка книг, осуществляется кеширование данных в хранилище на базе Redis.
3. При выполнении запросов на создание, обновление или удаление книг, осуществляется инвалидация данных в кеш-хранилище.
4. Создание категории для книги осуществляется при выполнении запроса на добавление первой книги новой категории, 
при добавлении новой книги уже имеющейся категории - категория переиспользуется.
5. Реализована обработка ошибок, которые могут быть вклиентских запросах, с возвратом клиенту результата с пояснениями.
6. Наглядное отображение контроллера и его методов реализовано с помощью **SpringDoc OpenAPI Starter WebMVC UI**,
доступно (после запуска приложения) по ссылке: http://localhost:8080/swagger-ui/index.html#/

## Настройка приложения
Параметры подключения к базе данных приложения задаются в конфигурационном файле **application.yml**.

По умолчанию файл содержит настройки для подключения к тестовой базе данных создаваемой скрипт-файлом **docker-compose.yml**

## Запуск приложения осуществляется средствами среды разработки
Для запуска приложения c тестовой базой данных необходимо выполнить последоватьельность действий: 
- если на машине отсутствуют docker-образы _postgres 12.3_ и _redis 7.0.12_ - скачать их соответствующими командами:
  - _**docker pull postgres:12.3**_
  - _**docker pull redis:7.0.12**_
- запустить основную базу данных и кеш-базу данных для работы приложения командой:
  - _**docker compose up**_ из директория **docker**, проекта;
- запустить приложение средствами среды разработки.