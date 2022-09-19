package com.example.empl;

import java.util.*;

import static java.util.stream.Collectors.*;

public class EmplController {

    public static void main(String[] argv) {

        Set<EmplDb> emplDbSet = EmplRepository.getEmplDb();

        System.out.println(emplDbSet);

        // [EmplDb[idEmpl=1, nameEmpl=Вася, idDep=1, nameDep=Бухгалтерия],
        //  EmplDb[idEmpl=1, nameEmpl=Вася, idDep=2, nameDep=Аналитический отдел],
        //  EmplDb[idEmpl=2, nameEmpl=Коля, idDep=1, nameDep=Бухгалтерия],
        //  EmplDb[idEmpl=3, nameEmpl=Петя, idDep=2, nameDep=Аналитический отдел],
        //  EmplDb[idEmpl=3, nameEmpl=Петя, idDep=3, nameDep=Отдел оптовой торговли]]

        Collection<Empl> employees = emplDbSet.stream().collect(
            collectingAndThen(toMap(EmplDb::idEmpl, Empl::new, Empl::addDeptsFromEmpl), Map::values));

        System.out.println(employees);

        // [Empl{idEmpl=1, nameEmpl='Вася', departments=[Department{idDep=1, nameDep='Бухгалтерия'},
        //                                               Department{idDep=2, nameDep='Аналитический отдел'}]},
        //  Empl{idEmpl=2, nameEmpl='Коля', departments=[Department{idDep=1, nameDep='Бухгалтерия'}]},
        //  Empl{idEmpl=3, nameEmpl='Петя', departments=[Department{idDep=2, nameDep='Аналитический отдел'},
        //                                               Department{idDep=3, nameDep='Отдел оптовой торговли'}]}]

    }
}
