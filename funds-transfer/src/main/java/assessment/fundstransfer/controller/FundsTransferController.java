package assessment.fundstransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import assessment.fundstransfer.utils.Utils;
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

    /*
     * Routes to the funds transfer landing page
     * - binds transferForm and list of accounts for thymeleaf to render
     */
    @GetMapping(path = { "/", "/index.html" })
    public String getLandingPage(
            Model model) {
        log.info(">>> Request for landing page...");

        model.addAttribute("transferRequest", Utils.createNewTransferForm());
        model.addAttribute("accounts", svc.getAccountList());

        return "funds-transfer";
    }

    /*
     * Process the submitted funds-transfer form
     * - first validates the form-bounded details (conditions c1,3,4)
     * - then further validates conditions c0,2,5
     * - if any validation failed, redirect to form again to display errors
     * - else, proceed with transaction that is protected by @transactional
     * - finally, log the transaction to redis
     */
    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postTransfer(
            // First validates the form-bounded details (conditions c1,3,4)
            @Valid TransferRequest transferRequest,
            BindingResult result,
            Model model) {

        log.info(">>> POST request for funds transfer...");
        log.info(">>> Transfer form received: " + transferRequest);

        // Further validates conditions c0,2,5
        svc.additionalTransferValidation(transferRequest, result);

        // If any validation failed, redirect to form again to display errors
        if (result.hasErrors()) {
            log.error("--- Submitted transfer request form is invalid!");
            model.addAttribute("accounts", svc.getAccountList());
            return "funds-transfer";
        }

        // Else, proceed with transaction that is protected by @transactional
        log.info("+++ Validation success. Proceeding to execute transfer...");
        Transaction response = null;
        try {
            response = svc.executeTransaction(transferRequest, result);
        } catch (DataAccessException e) {
            log.error("--- Error updating account balances");
            model.addAttribute("accounts", svc.getAccountList());
            return "funds-transfer";
        }

        // if (result.hasErrors()) {
        //     log.error("--- Error updating account balances");
        //     model.addAttribute("accounts", svc.getAccountList());
        //     return "funds-transfer";
        // }

        // Finally, log the transaction to redis
        logSvc.insertLog(response);
        model.addAttribute("transaction", response);

        return "success";
    }
}
