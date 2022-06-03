package kr.hh.liverary.common.document;

import kr.hh.liverary.common.interfaces.TestInterface;
import kr.hh.liverary.config.auth.CustomOAuth2UserService;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import kr.hh.liverary.domain.user.User;
import kr.hh.liverary.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DocumentServiceAndRepoTestCommon implements TestInterface {
    @Autowired
    public CustomOAuth2UserService userService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public DocumentRepository repo;

    public User mockUser = null;
    public String nonLoginWriterIp = "192.168.0.1";
    public String loginWriter = null;
    public String slangTitle1 = "킹받다";
    public String slangTitle2 = "ㅋㅋ루삥뽕";

    public List<Document> allItems = null;


    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        deleteAllUsers();
        allItems = null;
    }

    @BeforeEach
    public void setUp() {
        OAuthAttributes attr = new OAuthAttributes(null, null, userName, userMail, pictureUrl);
        mockUser = userService.saveOrUpdate(attr);
        loginWriter = mockUser.getEmail();
    }

    public Document storeItem(String writer, String title) throws  Exception {
        return repo.save(Document.builder()
                .title(title)
                .writer(writer)
        .build());
    }

    public Document storeItem(Document document) throws Exception {
        return repo.save(document);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<Document> findAll() {
        return repo.findAll();
    }
}
