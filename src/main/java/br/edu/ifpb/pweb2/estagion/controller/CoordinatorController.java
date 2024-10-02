package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.*;
import br.edu.ifpb.pweb2.estagion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/coordinator")
public class CoordinatorController {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InternshipOfferService internshipOfferService;

    @Autowired
    private StatusInternshipOfferService statusInternshipOfferService;

    @GetMapping
    public ModelAndView showHome(
            ModelAndView modelAndView,
            Coordinator coordinator
    ) {
        modelAndView.setViewName("coordinator/index");
        modelAndView.addObject("coordinator", coordinator);
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");

        return modelAndView;
    }

    @GetMapping("/list-application")
    public ModelAndView listApplications(
            ModelAndView modelAndView
    ) {
        List<Application> applications = applicationService.findAllByStauts(EApplicationStatus.APPLIED);

        modelAndView.setViewName("coordinator/list-application");
        modelAndView.addObject("applications", applications);
        return modelAndView;
    }

    @GetMapping("/view-company")
    public ModelAndView showCompany(
            @RequestParam("companyId") Integer companyId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-company");

        modelAndView.addObject("company", companyService.findById(companyId));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/view-student")
    public ModelAndView showStudent(
            @RequestParam("studentId") Integer studentId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-student");

        modelAndView.addObject("student", studentService.findById(studentId));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/view-offer/{id}")
    public ModelAndView showoffer(
            @PathVariable("id") Integer id,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-offer");

        modelAndView.addObject("offer", internshipOfferService.findById(id));
        return modelAndView;
    }

    @GetMapping("/get-all-internship-offers")
    public ModelAndView GetAllInternshipOffers(ModelAndView modelAndView) {
        StatusInternshipOffer statusInternshipOffer = statusInternshipOfferService.findById(1);

        List<InternshipOffer> internshipOffers = internshipOfferService.findByStatus(statusInternshipOffer);

        if (internshipOffers == null || internshipOffers.isEmpty()) {
            System.out.println("Nenhuma oferta de estágio encontrada!");
        } else {
            System.out.println("Ofertas de estágio encontradas: " + internshipOffers.size());
        }

        modelAndView.setViewName("coordinator/get-all-internships-offers");
        modelAndView.addObject("internshipOffers", internshipOffers);
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");

        return modelAndView;
    }
}
