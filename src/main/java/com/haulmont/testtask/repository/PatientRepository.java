package com.haulmont.testtask.repository;

import com.haulmont.testtask.dao.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    List<Patient> findAll();
//    @Query("select p from patients p where p.firstName like ?1 and p.middleName like ?2 and p.lastName like ?3")
//    List<Patient> findByParam(String firstName, String middleName, String lastName);

}