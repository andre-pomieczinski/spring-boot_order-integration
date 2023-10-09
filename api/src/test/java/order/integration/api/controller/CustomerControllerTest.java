package order.integration.api.controller;

import order.integration.api.dtos.address.AddressDto;
import order.integration.api.dtos.customer.CustomerDetailDto;
import order.integration.api.dtos.customer.CustomerDto;
import order.integration.api.model.CustomerModel;
import order.integration.api.model.embeddable.Address;
import order.integration.api.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.json.JacksonTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository repository;

    @Autowired
    private JacksonTester<CustomerDto> customerDtoJson;

    @Autowired
    private JacksonTester<CustomerDetailDto> customerDetailDtoJson;

    @Test
    @DisplayName("Return http code 400 when information is invalid")
    @WithMockUser
    void create_scene01() throws Exception {
        var response = mvc.perform(post("/customer")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Return http code 200 when information is valid")
    @WithMockUser
    void create_scene02() throws Exception {

        var customerDto = new CustomerDto(
                "Andre Pomieczinski",
                "andre.p.04@hotmail.com",
                "(54) 991683015",
                "03293671338",
                addressDto()
        );

        when(repository.save(any())).thenReturn(new CustomerModel(customerDto));

        var response = mvc
                .perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson.write(customerDto).getJson())
                ).andReturn().getResponse();

        var customerDetailDto = new CustomerDetailDto(
                null,
                customerDto.name(),
                customerDto.email(),
                customerDto.phone(),
                customerDto.cpf(),
                new Address(customerDto.address())
        );
        var jsonExpected = customerDetailDtoJson.write(customerDetailDto).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }

    private AddressDto addressDto() {
        return new AddressDto(
                "Erebango",
                "RS",
                "Centro",
                "Rua Geraldo Miguel",
                "345",
                "Casa",
                "99805654"
        );
    }

}