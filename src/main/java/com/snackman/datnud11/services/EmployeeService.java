package com.snackman.datnud11.services;

import com.snackman.datnud11.entity.Employee;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;

public interface EmployeeService {
    Employee checkEmployeeExist(Long id) throws CustomNotFoundException;
}
