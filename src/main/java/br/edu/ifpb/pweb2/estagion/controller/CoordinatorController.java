package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.*;
import br.edu.ifpb.pweb2.estagion.service.*;
import br.edu.ifpb.pweb2.estagion.model.Coordinator;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import br.edu.ifpb.pweb2.estagion.ui.NavPage;
import br.edu.ifpb.pweb2.estagion.ui.NavePageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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
    private InternshipOfferService _internshipOfferService;

    @Autowired
    private StatusInternshipOfferService _statusInternshipOfferService;

    @Autowired
    private InternshipService internshipService;

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
        modelAndView.addObject("applications", applicationService.findAllByStauts(EApplicationStatus.APPLIED));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/list-internships-in-progress")
    public ModelAndView listInternshipsInProgress(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size){
        Page<Internship> internships = internshipService.listInternhipsInProgress(PageRequest.of(page, size));
        ModelAndView mav = new ModelAndView("coordinator/list-internships-in-progress");

        mav.addObject("internships", internships.getContent());
        mav.addObject("page", internships);
        return mav;
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

    @GetMapping("/view-offer")
    public ModelAndView showoffer(
            @RequestParam("offerId") Integer offerId,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("coordinator/view-offer");

        modelAndView.addObject("offer", _internshipOfferService.findById(offerId));
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");
        return modelAndView;
    }

    @GetMapping("/get-all-internship-offers")
    public ModelAndView GetAllInternshipOffers(ModelAndView modelAndView, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        StatusInternshipOffer statusInternshipOffer = _statusInternshipOfferService.findById(1);
        Pageable paging = PageRequest.of(page - 1, size);

        Page<InternshipOffer> internshipOffers = _internshipOfferService.findByStatus(statusInternshipOffer, paging);

        if (internshipOffers == null || internshipOffers.isEmpty()) {
            System.out.println("Nenhuma oferta de estágio encontrada!");
        } else {
            System.out.println("Ofertas de estágio encontradas: " + internshipOffers.getTotalElements());
        }

        NavPage navPage = NavePageBuilder.newNavPage(internshipOffers.getNumber() + 1,
                internshipOffers.getTotalElements(), internshipOffers.getTotalPages(), size);
        modelAndView.addObject("navPage", navPage);

        modelAndView.setViewName("coordinator/get-all-internships-offers");
        modelAndView.addObject("internshipOffers", internshipOffers);
        modelAndView.addObject("logoutUrl", "/auth/coordinator/login");

        return modelAndView;
    }

    @GetMapping("/download-internships-term/{internshipId}")
    public ResponseEntity<byte[]> downloadInternshipTerm(@PathVariable Integer internshipId) {
        Optional<Internship> internship = internshipService.findById(internshipId);

        if (internship.isEmpty() || internship.get().getInternshipTerm() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] internshipTerm = internship.get().getInternshipTerm();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("internship_term_" + internshipId + ".pdf").build());

        return new ResponseEntity<>(internshipTerm, headers, HttpStatus.OK);
    }
}
