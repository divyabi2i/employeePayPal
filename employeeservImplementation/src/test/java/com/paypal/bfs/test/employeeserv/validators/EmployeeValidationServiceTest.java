package com.paypal.bfs.test.employeeserv.validators;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exceptions.BadInputException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static com.paypal.bfs.test.employeeserv.exceptions.ErrorMessages.*;

/**
 * @author Abhishyam.c on 26/02/21
 */
@RunWith(SpringRunner.class)
public class EmployeeValidationServiceTest {

    @InjectMocks
    private EmployeeValidationService employeeValidationService;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidateWithNull() {
        exception.expect(BadInputException.class);
        exception.expectMessage(EMPTY_OR_NULL_EMPLOYEE_OBJECT);
            employeeValidationService.validate(null);
    }
    @Test
    public void testValidateWithAddressNull() {
        exception.expect(BadInputException.class);
        exception.expectMessage(EMPTY_OR_NULL_ADDRESS_OBJECT);
        Employee employee = new Employee();
        employee.setAddress(null);
        employeeValidationService.validate(employee);
    }

    @Test
    public void testValidateWithPositive() {
        Address address = new Address();
        address.setLine1("Line1");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setZipcode("560037");
        Employee employee = new Employee();
        employee.setFirstName("test-first-name");
        employee.setLastName("test-last-name");
        employee.setDateOfBirth("20-04-1994");
        employee.setAddress(address);
        employeeValidationService.validate(employee);
    }
}