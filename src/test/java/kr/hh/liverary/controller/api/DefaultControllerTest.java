package kr.hh.liverary.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultControllerTest {

    private String prefixUrl = null;
    private MockMvc mvc;
    @LocalServerPort
    private int port;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        prefixUrl = "http://localhost:" + port + "/api";
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("1. api service health check")
    @Test
    public void healthCheckTest() throws Exception {
        // given
        String url = prefixUrl +  "/health";
        String expectedContent = "{\"code\":200,\"data\":{\"status\":\"OK\"},\"message\":\"OK\"}";

        // then
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedContent))
                .andDo(print());
    }
}
