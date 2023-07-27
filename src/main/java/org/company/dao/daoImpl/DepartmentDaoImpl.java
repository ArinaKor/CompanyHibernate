package org.company.dao.daoImpl;

import org.company.dao.BaseDao;
import org.company.model.Department;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDaoImpl implements BaseDao<Department> {

    private final SessionFactory sessionFactory;

    @Autowired
    public DepartmentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments;
        try(Session session = sessionFactory.openSession()){
            departments = session.createQuery("from Department", Department.class).getResultList();
        }
        return departments;
    }

    @Override
    public Optional<Department> findById(long id) {
        Department departmentById;
        try(Session session = sessionFactory.openSession()){
            departmentById = session.get(Department.class, id);
        }
        return Optional.ofNullable(departmentById);
    }

    @Override
    public boolean delete(long id) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Department departmentToDelete = session.find(Department.class, id);
                session.remove(departmentToDelete);
                session.getTransaction().commit();
                return true;
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean save(Department department) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Department departmentToSave = new Department();
                departmentToSave.setName(department.getName());
                session.persist(departmentToSave);
                session.getTransaction().commit();
                return true;
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean update(long id, Department department) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Department departmentToUpdate = session.find(Department.class, id);
                departmentToUpdate.setName(department.getName());
                session.merge(departmentToUpdate);
                session.getTransaction().commit();
                return true;
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }
        }
    }
}
