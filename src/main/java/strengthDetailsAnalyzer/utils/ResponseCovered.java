package strengthDetailsAnalyzer.utils;

import strengthDetailsAnalyzer.utils.response.Response;
import strengthDetailsAnalyzer.utils.response.ResponseStatus;

public interface ResponseCovered {

    default Response coverToResponse(String description){
        if (description.length() > 0) {
            return new Response(ResponseStatus.FAIL, description);
        } else {
            return new Response(ResponseStatus.SUCCESS);
        }

    }
}
