package com.hexaware.dao;

import com.hexaware.entity.Employee;
import com.hexaware.exception.InvalidEmployeeIdException;

public class CourierAdminServiceCollectionImpl extends CourierUserServiceCollectionImpl implements ICourierAdminService {

    public CourierAdminServiceCollectionImpl() {
        super(); // calls parent constructor to initialize companyObj
    }

    @Override
    public int addCourierStaff(Employee employee) throws InvalidEmployeeIdException {
        if (employee == null || employee.getEmployeeID() == 0) {
            throw new InvalidEmployeeIdException("Invalid employee details provided");
        }

        companyObj.getEmployees().add(employee);
        return employee.getEmployeeID();
    }
}
