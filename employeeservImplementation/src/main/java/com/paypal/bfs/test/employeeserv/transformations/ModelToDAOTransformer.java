package com.paypal.bfs.test.employeeserv.transformations;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.jpa.model.AddressEntity;
import com.paypal.bfs.test.employeeserv.jpa.model.EmployeesEntity;

import static com.paypal.bfs.test.employeeserv.validators.ValidationUtil.VALIDATE_DATE_FORMAT;

/**
 * @author Abhishyam.c on 26/02/21
 */
public final class ModelToDAOTransformer {

    private ModelToDAOTransformer() {
    }

    public static EmployeesEntity transformModelToEmployeeEntity(Employee employee){
        EmployeesEntity employeesEntity = new EmployeesEntity();
        employeesEntity.setFirstName(employee.getFirstName());
        employeesEntity.setLastName(employee.getLastName());
        employeesEntity.setDateOdBirth(VALIDATE_DATE_FORMAT.apply(employee.getDateOfBirth()));
        employeesEntity.setAddressEntity(transformModelToAddressEntity(employee.getAddress()));
        return employeesEntity;
    }
    public static AddressEntity transformModelToAddressEntity(Address address){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(address.getCity());
        addressEntity.setCountry(address.getCountry());
        addressEntity.setState(address.getState());
        addressEntity.setZipcode(address.getZipcode());
        addressEntity.setLine1(address.getLine1());
        addressEntity.setLine2(address.getLine2());
        return addressEntity;
    }
}
