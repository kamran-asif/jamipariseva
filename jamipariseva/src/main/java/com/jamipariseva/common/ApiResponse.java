package com.jamipariseva.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Indicates whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "Response status or error message detail", example = "OK")
    private String message;

    @Schema(description = "Response payload data")
    private T data;

    @Schema(
        description = "Dictionary of relevant HTTP status codes and descriptions",
        example = "{\"400 Bad Request\": \"Invalid request/body format\", \"500 Internal Server Error\": \"Generic server error\"}"
    )
    private java.util.Map<String, String> httpCodes;


    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder().success(true).message("OK").data(data).build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponse<T> fail(String message) {
        return ApiResponse.<T>builder().success(false).message(message).build();
    }

    public static <T> ApiResponse<T> fail(String message, java.util.Map<String, String> httpCodes) {
        return ApiResponse.<T>builder().success(false).message(message).httpCodes(httpCodes).build();
    }
}
