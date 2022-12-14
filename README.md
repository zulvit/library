# library
<h3>VKCourse</h3>
<b>Общие требования</b>

Нужно создать проект library состоящий из модулей models и controller.
Проект должен в каждом модуле установить зависимость от
com.intellij:annotations:12.0.
Для всех модулей должны быть определены и заданы переменные group и version.

<b>Модуль models</b>

Должен содержать модели сущностей “Автор” и “Книга”. Состав полей и методов сущностей на усмотрение студента курса. Модели “Автор” и “Книга” должны использовать библиотеку Lombok и не содержать никакого кода, кроме описания полей.
Для корректной работы необходимо установить и настроить в IDE плагин Lombok.


<b>Модуль controller</b>

Должен состоять из классов: “Приложение”, являющегося main классом консольного приложения, ”Библиотека” и  “Фабрика библиотеки”, создающего экземпляр библиотеки. “Библиотека” содержит книги, добавляемые в нее. Фабрика может возвращать библиотеку, статически определяемую в коде, вычитываемую из файла или из БД - на усмотрение студента.
Консольное приложение принимает входной параметр - имя автора книги и выводит список книг, написанных им и доступных в библиотеке. Вывод книг осуществляется в консоль, информация о книге выводится в виде json-сериализованного класса. Для сериализации необходимо использовать библиотеку Gson.

<b>Результат</b>

Результатом является готовый проект выложенный в общий доступ Github. Проект должен собираться в готовый к использованию в ОС Windows или Unix дистрибутив.
