package kr.hh.liverary.common.content;

import kr.hh.liverary.common.interfaces.TestInterface;
import kr.hh.liverary.config.auth.CustomOAuth2UserService;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.definition.DefinitionRepository;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import kr.hh.liverary.domain.user.User;
import kr.hh.liverary.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class DefinitionServiceAndRepoTest implements TestInterface {
    @Autowired
    public CustomOAuth2UserService userService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public DefinitionRepository repo;
    @Autowired
    public DocumentRepository documentRepo;

    public User mockUser = null;
    public String nonLoginWriterIp = "192.168.0.1";
    public String loginWriter = "user@mail.com";
    public String defaultDocumentTitle = "현자타임";
    public String defaultContent = "열성적으로 무언가에 몰입하며 행동하다가 급 현실을 깨닫고 조금 전까지 계속됐던 자신의 모습을 후회하거나 수치스러워하며 힘이 쭉 빠지는 순간. 현타라는 줄임말로 널리 쓰이고 있다.<br />" +
            "원래 뜻은 자위 (또는 성관계) 직후 급격한 무기력함과 동시에 허탈감, 허무함을 느끼는 상태를 말한다. 이후 제정신이 들면서 마치 현자(현인)와 같은 상태가 된다고 해서 지은 말이다. 그러다 이후 어느 순간부터 '현실 자각 타임'의 준말이라는 의미가 추가되어 널리 쓰이고 있다. 현타는 2번 줄인 단어가 됐다<br />" +
            "전자의 용례가 지상파방송, 일상생활 등에서 말하기 난감한 단어인지라 방송이나 미디어 등에서는 전자의 뜻을 모르는 척하거나, 혹은 의도적으로 후자의 뜻으로 순화시켜서 사용되고 있다. 후자가 전자와 다르게 분화되면서 해당 문서에서는 따로 설명한다. 국립국어원에서 만든 개방형 사전인 우리말샘에는 본래 뜻인 전자의 의미로 등재되어 있다.";
    public String content2 = "현실 자각 타임임!!";

    public Document defaultDocument  = null;

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        documentRepo.deleteAll();
        deleteAllUsers();
    }

    @BeforeEach
    public void setUp() {
        OAuthAttributes attr = new OAuthAttributes(null, null, userName, userMail, pictureUrl);
        mockUser = userService.saveOrUpdate(attr);
        loginWriter = mockUser.getEmail();
        defaultDocument = saveDocument(defaultDocumentTitle, loginWriter);
    }

    public Document saveDocument(String title, String writer) {
        return documentRepo.save(Document.builder()
                        .title(title)
                        .writer(writer)
            .build()
        );
    }

    public Definition storeItem(String writer, String content, Document document) throws  Exception {
        return repo.save(Definition.builder()
                .writer(writer)
                .content(content)
                .document(document)
            .build()
        );
    }

    public Definition storeItem(Definition content) throws Exception {
        return repo.save(content);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
