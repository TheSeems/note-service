package me.theseems.noteservice.handler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T body;

    @JsonCreator
    public ApiResponse(@JsonProperty("body") T body,
                       @JsonProperty("success") boolean success,
                       @JsonProperty("message") String message) {
        this.body = body;
        this.success = success;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<>(body, true, null);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(null, false, message);
    }
}
