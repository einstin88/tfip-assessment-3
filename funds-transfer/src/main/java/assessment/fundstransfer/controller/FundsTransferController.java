package assessment.fundstransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import assessment.fundstransfer.service.FundsTransferService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
@Slf4j
public class FundsTransferController {
    @Autowired
    private FundsTransferService svc;

    @GetMapping(path = { "/", "/index.html" })
    public String getLandingPage() {
        log.info(">>> Request for landing page...");
        return "funds-transfer";
    }

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postTransfer() {
        log.info(">>> POST request for funds transfer...");
        return "funds-transfer";
    }
}
