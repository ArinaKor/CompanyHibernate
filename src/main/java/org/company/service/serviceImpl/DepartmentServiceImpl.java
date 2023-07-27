package org.company.service.serviceImpl;

import org.company.dao.daoImpl.DepartmentDaoImpl;
import org.company.model.Department;
import org.company.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements BaseService<Department> {
    private final DepartmentDaoImpl departmentDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDaoImpl departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> findById(long id) {
        return departmentDao.findById(id);
    }

    @Override
    public boolean delete(long id) {
       return departmentDao.delete(id);
    }

    @Override
    public boolean update(long id, Department department) {
        return departmentDao.update(id, department);
    }

    @Override
    public boolean save(Department department) {
        return departmentDao.save(department);
    }
}
