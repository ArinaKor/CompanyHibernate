package org.company.service.serviceImpl;

import org.company.dao.daoImpl.ProjectDaoImpl;
import org.company.model.Project;
import org.company.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements BaseService<Project> {
    private final ProjectDaoImpl projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDaoImpl projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Optional<Project> findById(long id) {
        return projectDao.findById(id);
    }

    @Override
    public boolean delete(long id) {
        return projectDao.delete(id);
    }

    @Override
    public boolean update(long id, Project project) {
        return projectDao.update(id, project);
    }

    @Override
    public boolean save(Project project) {
        return projectDao.save(project);
    }

    public List<Project> findProjectsByWorker(String surname){
        return projectDao.findProjectsByWorker(surname);
    }
}
