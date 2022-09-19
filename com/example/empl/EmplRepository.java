package com.example.empl;

import java.util.LinkedHashSet;
import java.util.Set;

public class EmplRepository {

    public static Set<EmplDb> getEmplDb() {

        // LinkedHashSet to preserve order, just for convenience
        Set<EmplDb> result = new LinkedHashSet<>();

        result.add(new EmplDb(1, "Вася", 1, "Бухгалтерия"));
        result.add(new EmplDb(1, "Вася", 2, "Аналитический отдел"));
        result.add(new EmplDb(2, "Коля", 1, "Бухгалтерия"));
        result.add(new EmplDb(3, "Петя", 2, "Аналитический отдел"));
        result.add(new EmplDb(3, "Петя", 3, "Отдел оптовой торговли"));

        return result;

    }
}