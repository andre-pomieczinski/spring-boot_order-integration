package order.integration.api.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import order.integration.api.dtos.address.AddressDto;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private String neighborhood;
    private String street;
    private String zipcode;
    private String complement;
    private String number;
    private String uf;
    private String city;

    public Address(AddressDto addressDto) {
        this.neighborhood = addressDto.neighborhood();
        this.street = addressDto.street();
        this.zipcode = addressDto.zipcode();
        this.complement = addressDto.complement();
        this.number = addressDto.number();
        this.uf = addressDto.uf();
        this.city = addressDto.city();
    }

    public void updateInfos(AddressDto addressDto) {
        if (addressDto.neighborhood() != null)
            this.neighborhood = addressDto.neighborhood();

        if (addressDto.street() != null)
            this.street = addressDto.street();

        if (addressDto.zipcode() != null)
            this.zipcode = addressDto.zipcode();

        if (addressDto.complement() != null)
            this.complement = addressDto.complement();

        if (addressDto.number() != null)
            this.number = addressDto.number();

        if (addressDto.uf() != null)
            this.uf = addressDto.uf();

        if (addressDto.city() != null)
            this.city = addressDto.city();
    }
}
