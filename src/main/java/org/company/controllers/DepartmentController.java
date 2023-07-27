package org.company.controllers;

import org.company.model.Department;
import org.company.service.serviceImpl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Department>> findAll(){
        List<Department> departments = departmentService.findAll();
        return departments.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Department> findById(@PathVariable("id") long id){
        Optional<Department> departmentById = departmentService.findById(id);
        return (departmentById.isPresent())
                ? new ResponseEntity<>(departmentById.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id){
        return departmentService.delete(id)
                ? new ResponseEntity<>("Delete Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody Department department){
        return departmentService.save(department)
                ? new ResponseEntity<>("Save Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Department department){
        return departmentService.update(id, department)
                ? new ResponseEntity<>("Update Successfully",HttpStatus.OK)
                : new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
    }
}
