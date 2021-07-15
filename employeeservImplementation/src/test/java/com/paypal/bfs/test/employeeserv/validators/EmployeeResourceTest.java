package com.paypal.bfs.test.employeeserv.validators;

import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Abhishyam.c on 26/02/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmployeeservApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeResourceTest {
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    Employee employee;
    @Before
    public void init(){
        Address address = new Address();
        address.setLine1("Line1");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setZipcode("560037");
        employee = new Employee();
        employee.setFirstName("test-first-name");
        employee.setLastName("test-last-name");
        employee.setDateOfBirth("20-04-1994");
        employee.setAddress(address);
    }
    @Test
    public void testCreateEmployee(){
        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
        employee.setLastName("Test-last-name");
        ResponseEntity<Employee> response = restTemplate.exchange(
                createURLWithPort("v1/bfs/employees"),
                HttpMethod.POST, entity, Employee.class);

        Employee body = response.getBody();
        assertNotNull(body);
        assertEquals(body.getFirstName(), "test-first-name");
    }

    @Test
    public void testGetEmployee(){
        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

        //make a post call to insert data into db
        ResponseEntity<Employee> createResponse = restTemplate.exchange(
                createURLWithPort("v1/bfs/employees"),
                HttpMethod.POST, entity, Employee.class);
        assertNotNull(createResponse.getBody());

        //make a get call
        ResponseEntity<Employee> getResponse = restTemplate.exchange(
                createURLWithPort("v1/bfs/employees/"+createResponse.getBody().getId()),
                HttpMethod.GET, entity, Employee.class);

        assertNotNull(getResponse.getBody());
        assertEquals(getResponse.getBody().getId(), createResponse.getBody().getId());
        assertEquals(getResponse.getBody().getFirstName(), createResponse.getBody().getFirstName());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
