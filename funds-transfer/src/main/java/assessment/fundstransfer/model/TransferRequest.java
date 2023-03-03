package assessment.fundstransfer.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransferRequest(
        @NotBlank(message = "From account cannot be blank")
        @Size(min = 10, max = 10, message = "Invalid account to transfer from")
        String from,
        
        @NotBlank(message = "To account cannot be blank")
        @Size(min = 10, max = 10, message = "Invalid account to transfer to")
        String to,

        @NotNull(message = "Amount cannot be empty")
        @Digits(integer = 18, fraction = 2, message = "Invalid amount entered")
        @DecimalMin(value = "10.00", message = "Transfers require a minimum of $10")
        Double amount,
        
        @Nullable
        String comments) {

}
