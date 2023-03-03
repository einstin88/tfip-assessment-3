package assessment.fundstransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import assessment.fundstransfer.model.TransferRequest;
import assessment.fundstransfer.service.FundsTransferService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
@Slf4j
public class FundsTransferController {
    @Autowired
    private FundsTransferService svc;

    @GetMapping(path = { "/", "/index.html" })
    public String getLandingPage(
            Model model) {
        log.info(">>> Request for landing page...");

        model.addAttribute("form", new TransferRequest(null, null, null, null));
        model.addAttribute("accounts", svc.getAccountList());

        return "funds-transfer";
    }

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postTransfer(
            @Valid TransferRequest form,
            BindingResult result,
            Model model) {
        log.info(">>> POST request for funds transfer...");
        log.info(">>> Form received: " + form);

        if (result.hasErrors()) {
            log.error("--- submitted transfer request form is invalid!");
            return "funds-transfer";
        }

        return "success";
    }
}
