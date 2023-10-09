package order.integration.api.dtos.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDto(
        @NotBlank
        String city,
        @NotBlank
        String uf,
        @NotBlank
        String neighborhood,
        @NotBlank
        String street,
        String number,
        String complement,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String zipcode) {
}