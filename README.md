# Курсовой проект "Сетевой чат"
## Описание проекта
Два приложения для обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями.

  * Первое приложение - сервер чата, должно ожидать подключения пользователей.
    
  * Второе приложение - клиент чата, подключается к серверу чата и осуществляет доставку и получение новых сообщений.

Все сообщения логируются как на сервере, так и на клиентах. Выход из чата осуществлется по команде /exit.

## Сервер
- Установка порта для подключения клиентов через файл настроек settings.json;
- Возможность подключиться к серверу в любой момент и присоединиться к чату;
- Отправка новых сообщений клиентам;
- Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки.

## Клиент
- Выбор имени для участия в чате;
- Прочитать настройки приложения из файла настроек settings.json;
- Подключение к указанному в настройках серверу;
- Для выхода из чата нужно набрать команду выхода - “/exit”;
