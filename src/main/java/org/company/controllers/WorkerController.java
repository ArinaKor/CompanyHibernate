package org.company.controllers;

import org.company.model.Worker;
import org.company.service.serviceImpl.WorkerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerServiceImpl workerService;

    @Autowired
    public WorkerController(WorkerServiceImpl workerService) {
        this.workerService = workerService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Worker>> findAll(){
        List<Worker> workers = workerService.findAll();
        return (workers.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(workers, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Worker> findById(@PathVariable("id") long id){
        Optional<Worker> workerById = workerService.findById(id);
        return (workerById.isPresent())
                ? new ResponseEntity<>(workerById.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        return workerService.delete(id)
                ? new ResponseEntity<>("Delete Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody Worker worker){
        return workerService.save(worker)
                ? new ResponseEntity<>("Save Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Worker worker){
        return workerService.update(id, worker)
                ? new ResponseEntity<>("Update Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/project", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Worker>> findWorkersByProject(@RequestParam("name") String project){
       List<Worker> workers = workerService.findWorkersByProject(project);
       return (workers.isEmpty())
               ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
               : new ResponseEntity<>(workers, HttpStatus.OK);
    }

    @RequestMapping(value="/department", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Worker>> findWorkersByDepartment(@RequestParam("department") String department){
        List<Worker> workers = workerService.findWorkersByDepartment(department);
        return (workers.isEmpty())
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(workers, HttpStatus.OK);
    }
}
