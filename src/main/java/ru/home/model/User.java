package ru.home.model;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class User {
    public static final int MIN_USER_NAME_LENGTH = 1;
    public static final int MAX_USER_NAME_LENGTH = 64;
    public static final int MIN_CITY_NAME_LENGTH = 1;
    public static final int MAX_CITY_NAME_LENGTH = 64;
    private final Long id;
    private final String name;
    private final Set<String> citiesLived;
    private final Set<String> citiesWork;

//  Конструктор для реквестов в БД (мы отправляем запрос в БД)
    public User(String name, Set<String> citiesLived, Set<String> citiesWork) {
        this(null, name, citiesLived, citiesWork);
    }
//  Конструктор для респонзов(ответы). Из БД через сервис в контроллер
    public User(Long id, String name, Set<String> citiesLived, Set<String> citiesWork) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.citiesLived = Objects.requireNonNull(citiesLived);
        this.citiesWork = Objects.requireNonNull(citiesWork);
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public String getName() {
        return name;
    }

    public Set<String> getCitiesLived() {
        return citiesLived;
    }

    public Set<String> getCitiesWork() {
        return citiesWork;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", citiesLived=" + citiesLived +
                ", citiesWork=" + citiesWork +
                '}';
    }
}