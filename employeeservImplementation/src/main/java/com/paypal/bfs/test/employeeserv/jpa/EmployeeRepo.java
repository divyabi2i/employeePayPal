package com.paypal.bfs.test.employeeserv.jpa;

import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abhishyam.c on 26/02/21
 */
@Repository
public interface EmployeeRepo extends JpaRepository<EmployeesEntity,Integer> {
    Optional<EmployeesEntity> findById(Integer id);
    Optional<EmployeesEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
