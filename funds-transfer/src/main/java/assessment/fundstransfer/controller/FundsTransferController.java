package assessment.fundstransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import assessment.fundstransfer.model.Transaction;
import assessment.fundstransfer.model.TransferRequest;
import assessment.fundstransfer.service.FundsTransferService;
import assessment.fundstransfer.service.LogAuditService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
@Slf4j
public class FundsTransferController {
    @Autowired
    private FundsTransferService svc;

    @Autowired
    private LogAuditService logSvc;

    @GetMapping(path = { "/", "/index.html" })
    public String getLandingPage(
            Model model) {
        log.info(">>> Request for landing page...");

        model.addAttribute("transferRequest", new TransferRequest(null, null, null, null));
        model.addAttribute("accounts", svc.getAccountList());

        return "funds-transfer";
    }

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postTransfer(
            @Valid TransferRequest transferRequest,
            BindingResult result,
            Model model) {
        log.info(">>> POST request for funds transfer...");
        log.info(">>> Transfer form received: " + transferRequest);

        svc.additionalTransferValidation(transferRequest, result);

        if (result.hasErrors()) {
            log.error("--- Submitted transfer request form is invalid!");
            model.addAttribute("accounts", svc.getAccountList());
            return "funds-transfer";
        }

        log.info("+++ Validation success. Proceeding to execute transfer...");
        Transaction response = svc.executeTransaction(transferRequest, result);

        if (result.hasErrors()) {
            log.error("--- Error updating account balances");
            model.addAttribute("accounts", svc.getAccountList());
            return "funds-transfer";
        }

        logSvc.insertLog(response);
        model.addAttribute("transaction", response);
        
        return "success";
    }
}
