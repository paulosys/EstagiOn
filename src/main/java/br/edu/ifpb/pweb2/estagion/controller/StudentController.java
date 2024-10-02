package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StatusInternshipOfferService statusInternshipOfferService;

    @Autowired
    private InternshipOfferService internshipOfferService;

    @Autowired
    private StudentInternshipsService studentInternshipsService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public ModelAndView showHome(ModelAndView modelAndView, Student student) {
        modelAndView.setViewName("students/index");
        modelAndView.addObject("student", student);
        modelAndView.addObject("logoutUrl", "/auth/student/login");
        return modelAndView;
    }

    @GetMapping("/list-internship-offers")
    public ModelAndView listIntershipOffers(
            @RequestParam(value = "weeklyWorkload", required = false) String weeklyWorkload,
            ModelAndView modelAndView
    ) {
        List<InternshipOffer> internshipOffers;
        StatusInternshipOffer statusInternshipOffer = statusInternshipOfferService.findById(1);

        if (weeklyWorkload != null && !weeklyWorkload.isEmpty()) {
            internshipOffers = internshipOfferService.findByWeeklyWorkload(weeklyWorkload);
        } else {
            internshipOffers = internshipOfferService.findByStatus(statusInternshipOffer);
            internshipOffers.forEach(i -> System.out.println(i.getStatus().getName()));
        }

        modelAndView.setViewName("students/list-internship-offers");
        modelAndView.addObject("internshipOffers", internshipOffers);
        return modelAndView;
    }

    @GetMapping("/list-applied-internships")
    public ModelAndView listAppliedInternships(
            @RequestParam("studentId") Integer studentId,
            ModelAndView modelAndView
    ) {
        List<Application> appliedInternships = studentInternshipsService.findByStudentId(studentId);

        modelAndView.setViewName("students/list-applied-internships");
        modelAndView.addObject("appliedInternships", appliedInternships);
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("logoutUrl", "/auth/student/login");
        return modelAndView;
    }

    @GetMapping("/view-internship-offer")
    public ModelAndView showOffers(
            @RequestParam("internshipOfferId") Integer internshipOfferId,
            @RequestParam("studentId") Integer studentId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("students/view-internship-offer");

        modelAndView.addObject("internshipOffer", internshipOfferService.findById(internshipOfferId));
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("logoutUrl", "/auth/student/login");
        return modelAndView;
    }

    @GetMapping("/offers/filter")
    public ModelAndView filterIntershipOffers(
            @RequestParam("criteria") String Criteria,
            ModelAndView modelAndView
    ) {
        List<InternshipOffer> intershiOffers = internshipOfferService.findAll();
        modelAndView.setViewName("students/offers");
        modelAndView.addObject("offers", intershiOffers);
        modelAndView.addObject("logoutUrl", "/auth/student/login");
        return modelAndView;
    }

    @PostMapping("/apply")
    public ModelAndView applyForInternship(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("internshipOfferId") Integer internshipOfferId,
            ModelAndView modelAndView) {
        applicationService.applyForInternship(studentId, internshipOfferId);

        modelAndView.setViewName("students/application-success");
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("logoutUrl", "/auth/student/login");
        return modelAndView;
    }

}
