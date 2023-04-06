package ibf2022.paf.assessment.server.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserCreationException extends RuntimeException {

    public UserCreationException(String msg) {
        super(msg);
    }
}
