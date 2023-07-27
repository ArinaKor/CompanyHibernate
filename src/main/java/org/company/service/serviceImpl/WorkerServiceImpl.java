package org.company.service.serviceImpl;

import org.company.dao.daoImpl.WorkerDaoImpl;
import org.company.model.Worker;
import org.company.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl implements BaseService<Worker> {
    private final WorkerDaoImpl workerDao;

    @Autowired
    public WorkerServiceImpl(WorkerDaoImpl workerDao) {
        this.workerDao = workerDao;
    }

    @Override
    public List<Worker> findAll() {
        return workerDao.findAll();
    }

    @Override
    public Optional<Worker> findById(long id) {
        return workerDao.findById(id);
    }

    @Override
    public boolean delete(long id) {
        return workerDao.delete(id);
    }

    @Override
    public boolean update(long id, Worker worker) {
        return workerDao.update(id,worker);
    }

    @Override
    public boolean save(Worker worker) {
        return workerDao.save(worker);
    }

    public List<Worker> findWorkersByProject(String name){
        return workerDao.findWorkersByProject(name);
    }

    public List<Worker> findWorkersByDepartment(String department){
        return workerDao.findWorkersByDepartment(department);
    }
}
