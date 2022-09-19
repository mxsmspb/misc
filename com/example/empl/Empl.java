package com.example.empl;

import java.util.LinkedHashSet;
import java.util.Set;

public class Empl {
    final private Integer idEmpl;
    final private String nameEmpl;

    final private Set<Department> departments;

    public Empl(EmplDb emplDb) {
        this.idEmpl = emplDb.idEmpl();
        this.nameEmpl = emplDb.nameEmpl();
        this.departments = new LinkedHashSet<>();
        this.departments.add(new Department(emplDb.idDep(), emplDb.nameDep()));
    }

    private boolean isKeyEquals(Empl empl) {
        return this.idEmpl.equals(empl.idEmpl);
    }

    public Empl addDeptsFromEmpl(Empl empl) {
        if (isKeyEquals(empl)) {
            this.departments.addAll(empl.departments);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Empl{" +
                "idEmpl=" + idEmpl +
                ", nameEmpl='" + nameEmpl + '\'' +
                ", departments=" + departments +
                '}';
    }
}
