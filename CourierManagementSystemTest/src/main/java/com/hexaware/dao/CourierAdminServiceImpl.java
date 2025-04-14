package com.hexaware.dao;

import com.hexaware.entity.CourierCompany;
import com.hexaware.entity.Employee;
import com.hexaware.exception.InvalidEmployeeIdException;

public class CourierAdminServiceImpl extends CourierUserServiceImpl implements ICourierAdminService {

    public CourierAdminServiceImpl() {
        this.companyObj = new CourierCompany(); // inherits and reuses from parent
    }

    @Override
    public int addCourierStaff(Employee employee) throws InvalidEmployeeIdException {
        if (employee == null || employee.getEmployeeID() == 0) {
            throw new InvalidEmployeeIdException("Invalid employee details provided");
        }

        companyObj.addEmployee(employee);
        return employee.getEmployeeID();
    }
}
