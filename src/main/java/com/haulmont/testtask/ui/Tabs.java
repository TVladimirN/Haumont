package com.haulmont.testtask.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

@UIScope
@SpringView
public class Tabs extends TabSheet implements View {

    @Autowired
    private PatientsTab patientsTab;
    @Autowired
    private DoctorsTab doctorsTab;
    @Autowired
    private RecipesTab recipesTab;

    @Autowired
    @Qualifier("patientsTable")
    private Component patientsTable;

    @Autowired
    @Qualifier("doctorsTable")
    private Component doctorsTable;

    @Autowired
    @Qualifier("recipesTable")
    private Component recipesTable;

    @Autowired
    private CrudService crudService;


    @PostConstruct
    void init() {

        addTab(patientsTable);
        addTab(doctorsTable);
        addTab(recipesTable);

//        addTab(patientsTab);
//        addTab(doctorsTab);
//        addTab(recipesTab);
    }

}
