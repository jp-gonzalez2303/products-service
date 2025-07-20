package co.com.linktic.products.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ErrorResponse {

    private String code;
    private String message;
}
