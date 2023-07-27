package org.company.dao.daoImpl;

import org.company.dao.BaseDao;
import org.company.model.Project;
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
public class ProjectDaoImpl implements BaseDao<Project> {
    private final SessionFactory sessionFactory;

    @Autowired
    public ProjectDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public List<Project> findAll() {
        List<Project> projects;
        try(Session session = sessionFactory.openSession()){
            projects = session.createQuery("from Project", Project.class).getResultList();
        }
        return projects;
    }

    @Override
    public Optional<Project> findById(long id) {
        Project projectById;
        try(Session session = sessionFactory.openSession()){
            projectById = session.get(Project.class, id);
        }
        return Optional.ofNullable(projectById);
    }

    @Override
    public boolean delete(long id) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Project projectToDelete = session.find(Project.class, id);
                session.remove(projectToDelete);
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
    public boolean save(Project project) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Project projectToSave = new Project();
                projectToSave.setName(project.getName());
                session.persist(projectToSave);
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
    public boolean update(long id, Project project) {
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Project projectToUpdate = session.find(Project.class, id);
                projectToUpdate.setName(project.getName());
                session.merge(projectToUpdate);
                session.getTransaction().commit();
                return true;
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            }

        }
    }

    public List<Project> findProjectsByWorker(String surname){
        List<Project> projects = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            try{
                session.beginTransaction();
                String sql = "select p from Project p inner join p.workers w " +
                             "where w.surname = :surname";
                Query query = session.createQuery(sql);
                query.setParameter("surname", surname);
                projects = query.getResultList();
                session.getTransaction().commit();
                System.out.println(projects);
            }catch(HibernateException e){
                session.getTransaction().rollback();
                e.printStackTrace();
            }

        }
        return projects;
    }
}
