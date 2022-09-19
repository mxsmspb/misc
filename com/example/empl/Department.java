package com.example.empl;

public class Department {
    final private Integer idDep;
    final private String nameDep;

    public Department(Integer idDep, String nameDep) {
        this.idDep = idDep;
        this.nameDep = nameDep;
    }

    @Override
    public String toString() {
        return "Department{" +
                "idDep=" + idDep +
                ", nameDep='" + nameDep + '\'' +
                '}';
    }
}
