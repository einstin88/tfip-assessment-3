package assessment.fundstransfer.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransferRequest(
        @NotBlank(message = "")
        @Size(min = 10, max = 10, message = "")
        String from,
        
        @NotBlank(message = "")
        @Size(min = 10, max = 10, message = "")
        String to,

        @NotNull(message = "")
        @Digits(integer = 18, fraction = 2, message = "")
        @DecimalMin(value = "10.00", message = "")
        Double amount,
        
        String comments) {

}
