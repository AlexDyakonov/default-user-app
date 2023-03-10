<p align="center" style ="font-size: 24px"><strong>Пользовательское приложение </br>
С простым UI интерфейсом
</p>
Выполнил: <strong>Дьяконов Александр</strong></br>
</p>

## Задача:
Разработать приложение, которое позволит хранить различную информацию о пользователях.
1. Приложение должно позволять просматривать, добавлять, изменять, удалять пользователей, каждый пользователь уникален
2. Каждый пользователь может иметь 0..* городов, где работал
3. Каждый пользователь может иметь 0..* городов где жил
4. Возможность получить всех пользователей, все города, города, где пользователь жил/работал

## Ограничения:
1. Любая удобная реляционная, база данных
2. Запрещено использовать любой фреймворк который облегчает работу с базой данной, можно юзать только нативный SQL и конекторы к БД

# Цели:
1. Изучить базовые SQL запросы 
2. Научиться работать с PostgreSQL
3. Изучить базовые понятия JDBS и их реализацию 
4. Узнать, как устроена слоистая архитектура
5. Построить такую архитектуру, используя три изолированных слоя:
   1. Контроллер - слой принимающий запрос, вызывает метод сервиса, совершая валидацию данных.
   2. Слой сервиса - бизнес логика программы, работа над данными
   3. Репозиторий/DAO - работа с DB (методы работающие с DB)
6. Научиться добавлять зависимости, создавать .properties файл

## Результаты:
Было успешно написано пользовательское приложение с простым консольным интерфейсом, выполняющее все поставленные задачи. В будущем будет написано аналогичное приложение с использованием Spring Framework, так же весь функционал будет перенесен в телеграм бота.