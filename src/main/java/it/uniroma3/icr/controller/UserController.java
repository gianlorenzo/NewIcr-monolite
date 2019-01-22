package it.uniroma3.icr.controller;

import java.util.HashMap;
import java.util.Map;

import it.uniroma3.icr.supportControllerMethod.SetSchools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.AdminService;
import it.uniroma3.icr.service.impl.StudentService;
import it.uniroma3.icr.service.impl.StudentServiceSocial;
import it.uniroma3.icr.validator.studentValidator;
import it.uniroma3.icr.validator.StudentValidator2;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private SetSchools setSchools = new SetSchools();

    @Autowired
    private Facebook facebook;

    @Autowired
    private Google google;

    @Autowired
    private StudentService userFacade;

    @Autowired
    private StudentServiceSocial userFacadesocial;

    @Autowired
    private AdminService adminService;

    @Qualifier("userValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrazione(@ModelAttribute Student student, Model model) {

        Map<String, String> schoolGroups = new HashMap<String, String>();
        schoolGroups.put("3", "3");
        schoolGroups.put("4", "4");
        schoolGroups.put("5", "5");
        model.addAttribute("schoolGroups", schoolGroups);
        model.addAttribute("schools", setSchools.setSchools());
        return "registration";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String confirmUser(@ModelAttribute Student student, Model model, HttpSession session) {

        model.addAttribute("schools", setSchools.setSchools());
        Map<String, String> schoolGroups = new HashMap<String, String>();
        schoolGroups.put("3", "3");
        schoolGroups.put("4", "4");
        schoolGroups.put("5", "5");
        model.addAttribute("schoolGroups", schoolGroups);

        Student u = userFacade.findUser(student.getUsername());
        Administrator a = adminService.findAdmin(student.getUsername());

        if (studentValidator.validate(student, model, u, a,session)) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passwordEncode = passwordEncoder.encode(student.getPassword());
            student.setPassword(passwordEncode);
            model.addAttribute("student", student);
            userFacade.saveUser(student);
            return "registrationRecap";
        } else {
            return "registration";
        }

    }

    @RequestMapping(value = "/addUserFromFB", method = RequestMethod.POST)
    public String confirmUserFB(@ModelAttribute StudentSocial student, Model model, @Validated Student p,
                                BindingResult bindingResult,HttpSession session) {
        model.addAttribute("schools", setSchools.setSchools());

        Map<String, String> schoolGroups = new HashMap<String, String>();
        schoolGroups.put("3", "3");
        schoolGroups.put("4", "4");
        schoolGroups.put("5", "5");
        model.addAttribute("schoolGroups", schoolGroups);

        StudentSocial u = userFacadesocial.findUser(student.getUsername());
        LOGGER.info("FB authorized (student) for " + student.toString());

        Administrator a = adminService.findAdmin(student.getUsername());
        if (u != null) {
            LOGGER.info("FB authorized (u) for " + u.toString());
            LOGGER.info("FB validation (u) for " + u.toString() + " is " + StudentValidator2.validate(student, model,session));
        }

        if (StudentValidator2.validate(student, model, session )) {
            model.addAttribute("student", student);
            userFacadesocial.saveUser(student);
            model.addAttribute("social", "fb");
            return "registrationRecap";
        } else {
            return "registrationFacebook";
        }

    }

    @RequestMapping(value = "/addUserFromGoogle", method = RequestMethod.POST)
    public String confirmUserGoogle(@ModelAttribute StudentSocial student, Model model, @Validated Student p,
                                    BindingResult bindingResult,HttpSession session) {
        model.addAttribute("schools", setSchools.setSchools());

        Map<String, String> schoolGroups = new HashMap<String, String>();
        schoolGroups.put("3", "3");
        schoolGroups.put("4", "4");
        schoolGroups.put("5", "5");
        model.addAttribute("schoolGroups", schoolGroups);

        StudentSocial u = userFacadesocial.findUser(student.getUsername());
        LOGGER.info("Goo authorized (student) for " + student.toString());

        Administrator a = adminService.findAdmin(student.getUsername());
        if (u != null) {
            LOGGER.info("Goo authorized (u) for " + u.toString());
            LOGGER.info("Goo validation (u) for " + u.toString() + " is " + StudentValidator2.validate(student, model,session ));
        }

        if (StudentValidator2.validate(student, model, session)) {
            model.addAttribute("student", student);
            userFacadesocial.saveUser(student);
            model.addAttribute("social", "goo");
            return "registrationRecap";

        } else {
            return "registrationGoogle";
        }

    }

    @RequestMapping(value = "/addUserFromInstagram", method = RequestMethod.POST)
    public String confirmUserInstagram(@ModelAttribute StudentSocial student, Model model, @Validated Student p,
                                       BindingResult bindingResult,HttpSession session) {
        model.addAttribute("schools", setSchools.setSchools());

        Map<String, String> schoolGroups = new HashMap<String, String>();
        schoolGroups.put("3", "3");
        schoolGroups.put("4", "4");
        schoolGroups.put("5", "5");
        model.addAttribute("schoolGroups", schoolGroups);

        StudentSocial u = userFacadesocial.findUser(student.getUsername());
        LOGGER.info("Goo authorized (student) for " + student.toString());

        Administrator a = adminService.findAdmin(student.getUsername());
        if (u != null) {
            LOGGER.info("Goo authorized (u) for " + u.toString());
            LOGGER.info("Goo validation (u) for " + u.toString() + " is " + StudentValidator2.validate(student, model, session));
        }

        if (StudentValidator2.validate(student, model,session)) {
            model.addAttribute("student", student);
            userFacadesocial.saveUser(student);
            return "registrationRecap";

        } else {
            return "registrationInstagram";
        }

    }

    @RequestMapping(value = "user/toChangeStudentPassword")
    public String toChangePassword(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Student student = userFacade.findUser(username);
        student.setPassword("");
        model.addAttribute("student", student);
        return "users/changeStudentPassword";

    }

    @RequestMapping(value = "user/changeStudentPassword", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute Student student, Model model) {
        if (student.getPassword().equals("") || student.getPassword() == null) {
            model.addAttribute("errPassword", "*Campo Obbligatorio");
            model.addAttribute("student", student);
            return "users/changeStudentPassword";
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = passwordEncoder.encode(student.getPassword());
        student.setPassword(passwordEncode);
        userFacade.saveUser(student);
        return "users/homeStudent";
    }

    @RequestMapping(value = "/user/homeStudent")
    public String toHomeStudent(@ModelAttribute Student student, Model model, @ModelAttribute("social") String social) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (social.isEmpty())
            student = userFacade.findUser(username);
        else
            student = userFacadesocial.findUser(username);
        model.addAttribute("student", student);
        model.addAttribute("social", social);
        return "users/homeStudent";
    }

}
