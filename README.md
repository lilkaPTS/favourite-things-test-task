# **Техническое задание:**

Необходимо разработать форму опроса о любимых вещах (веб-приложение) с регистрацией и аутентификацией. (Проходить опрос
могут только зарегистрированные пользователи).

# **Используемые зависимости:**

* **springdoc-openapi-ui** - позволяет автоматически сгенерировать документацию к API, а также удобный веб-интерфейс для
  работы с ним <br/>
  Документация: api-docs.json, Интерфейс: /swagger-ui/index.html


* **lombok** - генератор кода, позволяет с помощью аннотаций создать геттеры, сеттеры, конструкторы и т.д.


* **jjwt** - библиотека для создания и проверки веб-токенов JSON (JWT)


* **modelmapper** - предоставляет удобный инструментарий для конвертации сущностей

# **Инструкция по запуску проекта:**

### Запуск:

1. mvn clean install
2. docker-compose up

# **Документация для работы с API:**

Развернутая документация представлена в файл api-docs.json или по ссылке /swagger-ui/index.html. Краткое описание: <br/>

### Public API:

Path                            | Method  |  description
------------------------------- | ------- | -----------
/api/public/registration        | POST    | Регистрация (можно зарегистрировать пользователя с ролью USER, роль ADMIN можно выдать только при прямом обращении к бд)
/api/public/login               | POST    | Авторизация. В ответ будет выдан jwt-токен, который необходимо вставить в header Authorization, для доступа к защищённым API
/api/public/sendConfirmationCode| GET     | Повторная отправка ссылки для активации аккаунта
/api/public/activate            | GET     | Активация аккаунта

### User API:

Path                            | Method  |  description
------------------------------- | ------- | -----------
/api/user/favourite             | POST    | Форма опроса о любимых вещах. Если пользователь попробует повторно заполнить форму, старый ответ будет обнволён

### Admin API:

Path                            | Method  |  description
------------------------------- | ------- | -----------
/api/admin/getAllUsernames      | GET     | Выгрузка email-адресов пользователей зарегистрированных в системе
  






