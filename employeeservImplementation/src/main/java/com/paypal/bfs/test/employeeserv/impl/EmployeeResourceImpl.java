package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.BadInputException;
import com.paypal.bfs.test.employeeserv.jpa.EmployeeRepo;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;
import com.paypal.bfs.test.employeeserv.transformations.DAOToModelTransformer;
import com.paypal.bfs.test.employeeserv.validators.EmployeeValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.exceptions.ErrorMessages.INVALID_EMPLOYEE_ID;
import static com.paypal.bfs.test.employeeserv.exceptions.ErrorMessages.NO_EMPLOYEE_OBJECT;
import static com.paypal.bfs.test.employeeserv.transformations.ModelToDAOTransformer.transformModelToEmployeeEntity;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private final EmployeeValidationService employeeValidationService;
    private final EmployeeRepo employeeRepo;

    public EmployeeResourceImpl(EmployeeValidationService employeeValidationService,
                                EmployeeRepo employeeRepo) {
        this.employeeValidationService = employeeValidationService;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        Optional<EmployeesEntity> employeesEntityOptional = employeeRepo.findById(Integer.valueOf(id));
        if(employeesEntityOptional.isPresent()){
            Employee employee = DAOToModelTransformer.transformDAOToEmployee(employeesEntityOptional.get());
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }else {
            throw new BadInputException(NO_EMPLOYEE_OBJECT+ id);
        }
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) {
        employeeValidationService.validate(employee);
        employeeValidationService.checkIfuserAlreadyExist(employee);
        EmployeesEntity employeesEntity = transformModelToEmployeeEntity(employee);
         employeesEntity = employeeRepo.save(employeesEntity);
         return employeeGetById(String.valueOf(employeesEntity.getId()));
    }

    /**
     * Handle the exception with 400 bad request as response code
     */
    @ExceptionHandler({ BadInputException.class})
    public ResponseEntity<Object> handleException(BadInputException exception) {
        Map<Object, Object> headers = new HashMap<>();
        headers.put("error", exception.getMessage());
        headers.put("status", BAD_REQUEST);
        return new ResponseEntity<>(headers, BAD_REQUEST);
    }

    @ExceptionHandler({ NumberFormatException.class})
    public ResponseEntity<Object> handleException() {
        Map<Object, Object> headers = new HashMap<>();
        headers.put("error", INVALID_EMPLOYEE_ID);
        headers.put("status", BAD_REQUEST);
        return new ResponseEntity<>(headers, BAD_REQUEST);
    }
}
