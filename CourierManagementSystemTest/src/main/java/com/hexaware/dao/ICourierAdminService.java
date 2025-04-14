package com.hexaware.dao;

import com.hexaware.entity.Employee;
import com.hexaware.exception.InvalidEmployeeIdException;

public interface ICourierAdminService {

    /**
     * Adds a new courier staff member to the system.
     * @param employee the employee object
     * @return generated employee ID
     * @throws InvalidEmployeeIdException if insertion fails due to constraint
     */
    int addCourierStaff(Employee employee) throws InvalidEmployeeIdException;
}

