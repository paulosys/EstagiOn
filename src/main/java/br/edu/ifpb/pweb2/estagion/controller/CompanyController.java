package br.edu.ifpb.pweb2.estagion.controller;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.service.CompanyService;
import br.edu.ifpb.pweb2.estagion.service.InternshipOfferService;
import jakarta.validation.Valid;
import br.edu.ifpb.pweb2.estagion.model.*;
import br.edu.ifpb.pweb2.estagion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private InternshipOfferService internshipOfferService;

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public ModelAndView showHome(ModelAndView modelAndView, Company company) {
        modelAndView.setViewName("companies/home");
        modelAndView.addObject("company", company);
        modelAndView.addObject("logoutUrl", "/auth/company/login");
        return modelAndView;
    }

    @GetMapping("/internship-offers")
    public ModelAndView listOffers(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        Company company = companyService.findByUsername(username);

        modelAndView.setViewName("companies/list-internship-offers");
        modelAndView.addObject("internshipOffers", internshipOfferService.findByCompanyId(company.getId()));

        return modelAndView;
    }

    @GetMapping("/create-internship-offer")
    public ModelAndView showRegisterForm(ModelAndView modelAndView) {
        modelAndView.setViewName("companies/create-internship-offer");
        modelAndView.addObject("internshipOffer", new InternshipOffer());
        return modelAndView;
    }

    @PostMapping("/create-internship-offer")
    public String createInternshipOffer(
            @Valid InternshipOffer internshipOffer,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "companies/create-internship-offer";
        }

        String username = principal.getName();
        Company company = companyService.findByUsername(username);

        internshipOfferService.save(internshipOffer, company);

        return "redirect:/companies/internship-offers";
    }

    @PostMapping("/delete-internship-offers/{id}")
    public String delete(@PathVariable int id) {
        internshipOfferService.delete(id);
        return "redirect:/companies/internship-offers";
    }


    @GetMapping("/internshipoffers/{offerId}/applications")
    public ModelAndView listApplicants(@PathVariable("offerId") Integer offerId, ModelAndView modelAndView) {
        try {
            Optional<InternshipOffer> optionalOffer = internshipOfferService.getOfferWithApplications(offerId);

            if (optionalOffer.isEmpty()) {
                modelAndView.setViewName("error/not-found");
                modelAndView.addObject("message", "Oferta de estágio não encontrada.");
                return modelAndView;
            }

            InternshipOffer offer = optionalOffer.get();
            List<Application> applications = companyService.ListApplicatioByOffer(offerId);

            modelAndView.setViewName("companies/list-applicants");
            modelAndView.addObject("internshipOffer", offer);
            modelAndView.addObject("applications", applications);

        } catch (Exception e) {
            e.printStackTrace();

            modelAndView.setViewName("error/generic-error");
            modelAndView.addObject("message", "Ocorreu um erro ao listar os candidatos.");
        }

        return modelAndView;
    }

    @PostMapping("/{offerId}/selectStudent")
    public ModelAndView selectStudent(
            @PathVariable("offerId") Integer offerId,
            @RequestParam("studentId") Integer studentId,
            @RequestParam("dataInicio") LocalDate dataInicio,
            @RequestParam("dataTermino") LocalDate dataTermino,
            ModelAndView modelAndView) {

        try {
            InternshipOffer offer = internshipOfferService.findById(offerId);
            Application selectedApplication = applicationService.getApplicationByOfferAndStudent(offerId, studentId);

            if (selectedApplication != null) {
                Internship internship = new Internship();
                internship.setStudent(selectedApplication.getStudent());
                internship.setInternshipOffer(selectedApplication.getInternshipOffer());
                internship.setStartDate(dataInicio);
                internship.setEndDate(dataTermino);
                internship.setInternshipValue(Double.parseDouble(offer.getStipendAmount()));

                byte[] internshipTerm = internshipService.createInternshipTerm(internship);
                internship.setInternshipTerm(internshipTerm);
                internshipService.save(internship);

                selectedApplication.setStauts(EApplicationStatus.ACCEPTED);
                applicationService.updateApplication(selectedApplication);

                offer.setIsFilled(true);
                internshipOfferService.updateOffer(offer);

                modelAndView.setViewName("internships/selection-success");
                modelAndView.addObject("studentId", studentId);
            } else {
                modelAndView.setViewName("error/not-found");
                modelAndView.addObject("message", "Candidatura não encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error/generic-error");
            modelAndView.addObject("message", "Ocorreu um erro ao selecionar o aluno.");
        }

        return modelAndView;
    }

    @GetMapping("/students/{id}")
    public ModelAndView viewStudentProfile(@PathVariable("id") Integer studentId) {
        ModelAndView modelAndView = new ModelAndView("companies/student-record");
        Student student = studentService.findById(studentId);
        modelAndView.addObject("student", student);
        return modelAndView;
    }
}
