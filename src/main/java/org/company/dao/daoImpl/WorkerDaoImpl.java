package org.company.dao.daoImpl;

import org.company.dao.BaseDao;
import org.company.model.Department;
import org.company.model.Worker;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository

public class WorkerDaoImpl implements BaseDao<Worker> {
    private final SessionFactory sessionFactory;
    private final DepartmentDaoImpl departmentDao;

    @Autowired
    public WorkerDaoImpl(SessionFactory sessionFactory, DepartmentDaoImpl departmentDao) {
        this.sessionFactory = sessionFactory;
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Worker> findAll() {
        List<Worker> workers;
        try(Session session = sessionFactory.openSession()){
            workers = session.createQuery("from Worker", Worker.class).getResultList();;
        }
        return workers;
    }

    @Override
    public Optional<Worker> findById(long id) {
        Worker workerById;
        try(Session session = sessionFactory.openSession()){
            workerById = session.get(Worker.class, id);
        }
        return Optional.ofNullable(workerById);
    }

    @Override
    public boolean delete(long id) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Worker workerToDelete = session.find(Worker.class, id);
                session.remove(workerToDelete);
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
    public boolean save(Worker worker) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Worker workerToSave = new Worker();
                workerToSave.setName(worker.getName());
                workerToSave.setSurname(worker.getSurname());
                workerToSave.setSalary(worker.getSalary());
                Optional<Department> department = departmentDao.findById(worker.getDepartment().getId());
                department.ifPresent(workerToSave::setDepartment);
                session.persist(workerToSave);
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
    public boolean update(long id, Worker worker) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Worker workerToUpdate = session.find(Worker.class, id);
                workerToUpdate.setName(worker.getName());
                workerToUpdate.setSurname(worker.getSurname());
                workerToUpdate.setSalary(worker.getSalary());
                Optional<Department> department = departmentDao.findById(worker.getDepartment().getId());
                department.ifPresent(workerToUpdate::setDepartment);
                session.merge(workerToUpdate);
                session.getTransaction().commit();
                return true;
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }

        }
    }

    public List<Worker> findWorkersByProject(String projectName){
        List<Worker> workers = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                String sql = "select w from Worker w inner join w.projects " +
                        "p where p.name = :projectName";
                Query query = session.createQuery(sql);
                query.setParameter("projectName", projectName);
                workers = query.getResultList();
                session.getTransaction().commit();
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return workers;
    }

    public List<Worker> findWorkersByDepartment(String department){
        List<Worker> workers = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                String sql = "select w from Worker w inner join w.department d " +
                             "where d.name = :department";
                Query query = session.createQuery(sql);
                query.setParameter("department", department);
                workers = query.getResultList();
                session.getTransaction().commit();
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return workers;
    }
}

