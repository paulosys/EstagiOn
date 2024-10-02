package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Internship;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    public void save(Internship estagio) {
        internshipRepository.save(estagio);
    }

    public Optional<Internship> findById(Integer id) {
        return internshipRepository.findById(id);
    }

    public Page<Internship> listInternhipsInProgress(Pageable pageable){
        return internshipRepository.findAll(pageable);
    }

    public byte[] createInternshipTerm(Internship internship){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("INTERNSHIP COMMITMENT TERM")
                    .setBold().setFontSize(16));

            addCompanyInformation(document, internship);

            addInternInformation(document, internship);

            addInternshipConditions(document, internship);

            addObligations(document);

            addSignatures(document, internship);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    private void addCompanyInformation(Document document, Internship internship) {
        document.add(new Paragraph("Company:").setBold());
        document.add(new Paragraph("Name: " + internship.getInternshipOffer().getCompany().getUsername()));
        document.add(new Paragraph("CNPJ: " + internship.getInternshipOffer().getCompany().getCnpj()));
        document.add(new Paragraph("Address: " + internship.getInternshipOffer().getCompany().getAddress()));
        document.add(new Paragraph("Phone: " + internship.getInternshipOffer().getCompany().getContactPhone()));
        document.add(new Paragraph("Contact Person: " + internship.getInternshipOffer().getCompany().getContactPerson()));
    }

    private void addInternInformation(Document document, Internship internship) {
        document.add(new Paragraph("Intern:").setBold());
        document.add(new Paragraph("Name: " + internship.getStudent().getFirstName() + " " + internship.getStudent().getLastName()));
        document.add(new Paragraph("Educational Institution: " + internship.getStudent().getInstitution()));
    }

    private void addInternshipConditions(Document document, Internship internship) {
        document.add(new Paragraph("Internship Conditions:").setBold());
        document.add(new Paragraph("Main Activity of the Internship: " + internship.getInternshipOffer().getMainActivity()));
        document.add(new Paragraph("Start Date: " + internship.getStartDate()));
        document.add(new Paragraph("End Date: " + internship.getEndDate()));
        document.add(new Paragraph("Compensation: The intern will receive a scholarship of R$ " + internship.getInternshipValue() + " per month."));
    }

    private void addObligations(Document document) {
        document.add(new Paragraph("Obligations:").setBold());
        document.add(new Paragraph("Intern: Fulfill the defined workload and activities, maintain confidentiality of information, and respect the company's code of conduct."));
        document.add(new Paragraph("Company: Provide the necessary resources for the internship, monitor the intern's development, and provide periodic evaluations."));
    }

    private void addSignatures(Document document, Internship internship) {
        document.add(new Paragraph("Signatures:").setBold());
        document.add(new Paragraph(internship.getInternshipOffer().getCompany().getContactPerson() + "\nCompany"));
        document.add(new Paragraph(internship.getStudent().getFirstName() + " " + internship.getStudent().getLastName() + "\nIntern"));
    }
}
