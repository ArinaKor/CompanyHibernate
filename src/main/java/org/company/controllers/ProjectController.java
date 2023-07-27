package org.company.controllers;

import org.company.model.Project;
import org.company.model.Worker;
import org.company.service.serviceImpl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    
    private final ProjectServiceImpl projectService;

    @Autowired
    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Project>> findAll(){
        List<Project> projects = projectService.findAll();
        return (projects.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Project> findById(@PathVariable("id") long id){
        Optional<Project> projectById = projectService.findById(id);
        return (projectById.isPresent())
                ? new ResponseEntity<>(projectById.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        return projectService.delete(id)
                ? new ResponseEntity<>("Delete Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody Project project){
        return projectService.save(project)
                ? new ResponseEntity<>("Save Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Project project){
        return projectService.update(id, project)
                ? new ResponseEntity<>("Update Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/workers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Project>> findWorkersByProject(@RequestParam("surname") String surname){
        List<Project> projects = projectService.findProjectsByWorker(surname);
        return (projects.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
