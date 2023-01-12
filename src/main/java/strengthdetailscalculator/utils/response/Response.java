package strengthdetailscalculator.utils.response;

import lombok.Getter;

@Getter
public class Response {
    private final ResponseStatus responseStatus;
    private final String description;

    public Response(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.description = "";
    }

    public Response(ResponseStatus responseStatus, String description) {
        this.responseStatus = responseStatus;
        this.description = description;
    }
}
