package com.example.backend_app.controller;

import com.example.backend_app.model.Employee;
import com.example.backend_app.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @RequestMapping(path = {"/", "/search"})
    public String viewHomePage(Model model, String keyword) {
        return findPaginated(1, model, "firstName", "asc", keyword);
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";

    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormUpdate(@PathVariable(value = "id") long id, Model model) {

        // Get employee from the server
        Employee employee = employeeService.getEmployeeById(id);

        // set employee as a model attribute to prepopulate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,String keyword){
        int pageSize = 5;


        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir,keyword);
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("keyword",keyword);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);
        return "index";
    }


}

