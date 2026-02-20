package nl.novi.pizzeria_webAPI.controller;

import com.jayway.jsonpath.JsonPath;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import nl.novi.pizzeria_webAPI.dto.DetailInputDto;
import nl.novi.pizzeria_webAPI.dto.OrderInputDto;
import nl.novi.pizzeria_webAPI.model.*;
import nl.novi.pizzeria_webAPI.repository.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class OrderControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateCorrectOrder() throws Exception {
        String requestJson = """
                {
                    "customerNum": 1,
                    "employeeNum": 2,
                    "detailInputDtos": [{ "itemId": 1, "itemQuantity": 9 }, { "itemId": 3, "itemQuantity": 11 }],
                    "paymentStatus": "TOPAY",
                    "orderStatus": "CREATED"
                }
                """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/orders")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // haal alleen de id veld van de json body er uit
        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        // Check de location veld waarde van de header )
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("http://localhost/orders/" + id));
    }

    @Test
    void shouldGetOrderById() throws Exception {

        //arrange
        //maak een order aan met post
        String requestJson = """
                {
                    "customerNum": 1,
                    "employeeNum": 2,
                    "detailInputDtos": [{ "itemId": 1, "itemQuantity": 9 }, { "itemId": 3, "itemQuantity": 11 }],
                    "paymentStatus": "TOPAY",
                    "orderStatus": "CREATED"
                }
                """;

        //voer de post uit
        MvcResult postResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn();

        //haal de id op van de body
        Integer createdId = JsonPath.read(postResult.getResponse().getContentAsString(), "$.id");

        //act
        //voer de get request uit
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + createdId)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdId))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Lorens Zhou"))
                        .andReturn();

        //assert locatie veld de waarde ophalen
        String actualLocation = result.getResponse().getHeader("Location");

        // Check de location veld waarde van de header
        assertThat(actualLocation, org.hamcrest.Matchers.is("http://localhost/orders/" + createdId));
    }

}
