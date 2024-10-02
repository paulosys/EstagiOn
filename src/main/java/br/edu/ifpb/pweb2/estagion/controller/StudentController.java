package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.service.ApplicationService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import br.edu.ifpb.pweb2.estagion.service.StatusInternshipOfferService;
import br.edu.ifpb.pweb2.estagion.service.StudentInternshipsService;
import br.edu.ifpb.pweb2.estagion.service.StudentService;
import br.edu.ifpb.pweb2.estagion.ui.NavPage;
import br.edu.ifpb.pweb2.estagion.ui.NavePageBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            HttpSession session,
            @RequestParam(value = "weeklyWorkload", required = false) String weeklyWorkload,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            ModelAndView modelAndView
    ) {
        Integer studentId = (Integer) session.getAttribute("loggedInStudent");

        Page<InternshipOffer> internshipOffers;
        StatusInternshipOffer statusInternshipOffer = statusInternshipOfferService.findById(1);

        Pageable paging = PageRequest.of(page - 1, size);

        if (weeklyWorkload != null && !weeklyWorkload.isEmpty()) {
            internshipOffers = internshipOfferService.findByWeeklyWorkload(weeklyWorkload, paging);
        } else {
            internshipOffers =  internshipOfferService.findByStatus(statusInternshipOffer, paging);
        }

        modelAndView.setViewName("students/list-internship-offers");
        modelAndView.addObject("internshipOffers", internshipOffers);
        modelAndView.addObject("studentId", studentId);
        modelAndView.addObject("logoutUrl", "/auth/student/login");

        NavPage navPage = NavePageBuilder.newNavPage(internshipOffers.getNumber() + 1,
                internshipOffers.getTotalElements(), internshipOffers.getTotalPages(), size);
        modelAndView.addObject("navPage", navPage);

        return modelAndView;
    }

    @GetMapping("/list-applied-internships")
    public ModelAndView listAppliedInternships(
            @RequestParam("studentId") Integer studentId,
            ModelAndView modelAndView
    ) {
        List<Application> appliedInternships =  studentInternshipsService.findByStudentId(studentId);

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
