package com.paypal.bfs.test.employeeserv.transformations;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.jpa.model.AddressEntity;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

import static com.paypal.bfs.test.employeeserv.validators.ValidationUtil.CONVERT_DATE_STRING;

/**
 * @author Abhishyam.c on 26/02/21
 */
public final class DAOToModelTransformer {

    private DAOToModelTransformer() {
    }

    public static Employee transformDAOToEmployee(EmployeesEntity employeesEntity){
        Employee employee = new Employee();
        employee.setId(employeesEntity.getId());
        employee.setFirstName(employeesEntity.getFirstName());
        employee.setLastName(employeesEntity.getLastName());
        employee.setDateOfBirth(CONVERT_DATE_STRING.apply(employeesEntity.getDateOdBirth()));
        employee.setAddress(transformDAOToAddress(employeesEntity.getAddressEntity()));
        return employee;
    }

    public static Address transformDAOToAddress(AddressEntity addressEntity){
        Address address = new Address();
        address.setLine1(addressEntity.getLine1());
        address.setLine2(addressEntity.getLine2());
        address.setCity(addressEntity.getCity());
        address.setState(addressEntity.getState());
        address.setCountry(addressEntity.getCountry());
        address.setZipcode(addressEntity.getZipcode());
        return address;
    }
}
