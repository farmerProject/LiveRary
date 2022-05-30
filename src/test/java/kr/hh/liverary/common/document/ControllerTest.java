package kr.hh.liverary.common.document;

import kr.hh.liverary.common.interfaces.TestInterface;
import kr.hh.liverary.config.auth.CustomOAuth2UserService;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import kr.hh.liverary.domain.user.User;
import kr.hh.liverary.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class ControllerTest implements TestInterface {
    @LocalServerPort
    private int port;
    @Autowired
    public CustomOAuth2UserService userService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public DocumentRepository repo;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TestRestTemplate restTemplate;

    public User mockUser = null;
    public MockMvc mvc;
    public String prefixUrl = null;

    public String nonLoginWriterIp = "192.168.0.1";
    public String loginWriter = null;
    public String slangTitle1 = "킹받다";
    public String slangTitle2 = "ㅋㅋ루삥뽕";

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        deleteAllUsers();
    }

    @BeforeEach
    public void setUp() {
        prefixUrl = "http://localhost:" + port + "/api";
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        saveUser("John Doe", "mymail@mail.com", "http://picture.com/photo/1");
        loginWriter = mockUser.getEmail();
    }

    public Document storeItem(String writer, String title) throws  Exception {
        return repo.save(Document.builder()
                .title(title)
                .writer(writer)
        .build());
    }

    private void saveUser(String name, String mail, String picture) {
        OAuthAttributes attr = new OAuthAttributes(null, null, name, mail, picture);
        mockUser = userService.saveOrUpdate(attr);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
