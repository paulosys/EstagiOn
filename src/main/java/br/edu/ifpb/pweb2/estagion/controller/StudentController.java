package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.ApplicationService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import br.edu.ifpb.pweb2.estagion.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private InternshipOfferService internshipOfferService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public ModelAndView showHome(ModelAndView modelAndView, Student student) {
        modelAndView.setViewName("students/index");
        modelAndView.addObject("student", student);

        return modelAndView;
    }

    @GetMapping("/list-internship-offers")
    public ModelAndView listIntershipOffers(
            @RequestParam("studentId") Integer studentId,
            @RequestParam(value = "weeklyWorkload", required = false) String weeklyWorkload,
            ModelAndView modelAndView
    ) {
        List<InternshipOffer> internshipOffers;
        if (weeklyWorkload != null && !weeklyWorkload.isEmpty()) {
            internshipOffers = internshipOfferService.findByWeeklyWorkload(weeklyWorkload);
        } else {
            internshipOffers =  internshipOfferService.findAll();
        }

        modelAndView.setViewName("students/list-internship-offers");
        modelAndView.addObject("internshipOffers", internshipOffers);
        modelAndView.addObject("studentId", studentId);
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
        return modelAndView;
    }

}
