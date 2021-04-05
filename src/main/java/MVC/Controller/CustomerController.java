package MVC.Controller;

import MVC.Model.Entities.Customer;
import MVC.Model.Entities.MyCounter;
import MVC.Model.Entities.Province;
import MVC.Model.Service.DuplicateEmailException;
import MVC.Model.Service.ICustomerService;
import MVC.Model.Service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("mycounter")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public List<Province> provinces(){
        return provinceService.findAll();
    }

    @ModelAttribute("mycounter")
    public MyCounter setUpCounter() {
        return new MyCounter();
    }


    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create.html");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult) throws DuplicateEmailException {
        ModelAndView modelAndView;
        if (bindingResult.hasFieldErrors()) {
            modelAndView = new ModelAndView("/customer/create");
            return modelAndView;
        } else {
            this.customerService.save(customer);
            modelAndView = new ModelAndView("/customer/create.html");
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("message", "New customer created successfully");
            return modelAndView;
        }
    }


    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit.html");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) throws DuplicateEmailException {
           customerService.save(customer);
           ModelAndView modelAndView = new ModelAndView("/customer/edit.html");
           modelAndView.addObject("customer", customer);
           modelAndView.addObject("message", "Customer updated successfully");
           return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/delete.html");
            modelAndView.addObject("customer", customer);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:customers";
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers(@RequestParam("s") Optional<String> s, Pageable pageable,@ModelAttribute("mycounter") MyCounter myCounter){
        Page<Customer> customers;
        if(s.isPresent()){
            customers = customerService.findAllByFirstNameContaining(s.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        myCounter.increment();
        return modelAndView;
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/customer/Exception");
    }

}

