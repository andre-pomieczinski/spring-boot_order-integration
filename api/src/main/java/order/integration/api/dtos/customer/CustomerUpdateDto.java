package order.integration.api.dtos.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import order.integration.api.dtos.address.AddressDto;

public record CustomerUpdateDto(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotNull
        @Valid
        AddressDto address) {
}
