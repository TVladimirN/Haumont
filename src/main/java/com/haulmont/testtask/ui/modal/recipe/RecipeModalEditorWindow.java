package com.haulmont.testtask.ui.modal.recipe;

import com.haulmont.testtask.payload.Fio;
import com.haulmont.testtask.payload.RecipePriority;
import com.haulmont.testtask.payload.dao.DoctorDAO;
import com.haulmont.testtask.payload.dao.PatientDAO;
import com.haulmont.testtask.payload.dao.RecipeDAO;
import com.haulmont.testtask.spring.RepositoryService;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeModalEditorWindow extends ModalEditorWindow<RecipeDAO> {

    private RepositoryService repositoryService;

    public RecipeModalEditorWindow(String label, RecipeDAO defaultItem, RepositoryService repositoryService) {
        super(label, RecipeDAO.class, defaultItem);
        this.repositoryService = repositoryService;
    }

    @Override
    protected Component[] buildComponents() {
        return new Component[]{
                descriptionField(),
                patientBox(),
                doctorBox(),
                createDate(),
                durationDate(),
                priorityBox()
        };
    }

    private TextArea descriptionField() {
        TextArea descriptionArea = new TextArea("Описание");
        descriptionArea.setMaxLength(500);
        descriptionArea.setRows(4);
        descriptionArea.setWidth("350");

        binder.forField(descriptionArea)
                .asRequired("Описание не может быть пустым")
                .bind(RecipeDAO::getDescription, RecipeDAO::setDescription);

        return descriptionArea;
    }

    private Component patientBox() {
        ComboBox<Fio> patientBox = new ComboBox<>("Пациент");
        patientBox.setWidth("350");
        patientBox.setEmptySelectionAllowed(false);
        Map<Fio, PatientDAO> map = repositoryService.getRepositoryByClassDao(PatientDAO.class).findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                d -> new Fio(d.getFirstName(), d.getMiddleName(), d.getLastName()),
                                d -> d
                        )
                );
        binder.forField(patientBox)
                .asRequired("Выберите пациента")
                .bind(
                        recipeDAO -> {
                            PatientDAO doctor = recipeDAO.getPatient();
                            if (null == doctor) {
                                return Fio.empty();
                            }
                            return new Fio(
                                    doctor.getFirstName(),
                                    doctor.getMiddleName(),
                                    doctor.getLastName()
                            );
                        },
                        (recipeDAO, s) -> recipeDAO.setPatient(map.get(s))
                );
        patientBox.setItems(map.keySet());
        return patientBox;
    }

    private Component doctorBox() {
        ComboBox<Fio> doctorBox = new ComboBox<>("Врач");
        doctorBox.setWidth("350");
        doctorBox.setEmptySelectionAllowed(false);
        Map<Fio, DoctorDAO> map = repositoryService.getRepositoryByClassDao(DoctorDAO.class).findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                d -> new Fio(d.getFirstName(), d.getMiddleName(), d.getLastName()),
                                d -> d
                        )
                );
        binder.forField(doctorBox)
                .asRequired("Выберите врача")
                .bind(
                        recipeDAO -> {
                            DoctorDAO doctor = recipeDAO.getDoctor();
                            if (null == doctor) {
                                return Fio.empty();
                            }
                            return new Fio(
                                    doctor.getFirstName(),
                                    doctor.getMiddleName(),
                                    doctor.getLastName()
                            );
                        },
                        (recipeDAO, s) -> recipeDAO.setDoctor(map.get(s))
                );
        doctorBox.setItems(map.keySet());
        return doctorBox;
    }

    private Component createDate() {
        DateField dateField = new DateField("Время создания");
        dateField.setTextFieldEnabled(false);

        binder.forField(dateField)
                .asRequired()
                .bind(recipeDAO -> {
                    if (null == recipeDAO.getDateCreate()) {
                        return LocalDate.now();
                    }
                    return recipeDAO.getDateCreate();
                }, RecipeDAO::setDateCreate);

        return dateField;
    }

    private Component durationDate() {
        DateField dateField = new DateField("Срок действия");
        dateField.setTextFieldEnabled(false);

        binder.forField(dateField)
                .asRequired("Срок действия рецепта не может быть пустым")
                .bind(RecipeDAO::getDuration, RecipeDAO::setDuration);

        return dateField;
    }

    private Component priorityBox() {
        ComboBox<RecipePriority> priorityBox = new ComboBox<>("Приоритет");
        priorityBox.setWidth("350");
        priorityBox.setEmptySelectionAllowed(false);
        priorityBox.setItems(RecipePriority.values());

        binder.forField(priorityBox)
                .asRequired("Выберите приоритет")
                .bind(RecipeDAO::getPriority, RecipeDAO::setPriority);

        return priorityBox;
    }

}
