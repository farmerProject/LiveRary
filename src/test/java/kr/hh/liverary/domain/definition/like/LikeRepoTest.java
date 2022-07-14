package kr.hh.liverary.domain.definition.like;

import kr.hh.liverary.common.definition.DefinitionAction;
import kr.hh.liverary.common.document.DocumentAction;
import kr.hh.liverary.common.interfaces.TempTestInterface;
import kr.hh.liverary.config.auth.CustomOAuth2UserService;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
public class LikeRepoTest implements TempTestInterface {

    @Autowired
    private LikeRepository repo;
    @Autowired
    private CustomOAuth2UserService userService;
    @Autowired
    private DocumentAction documentAction;
    @Autowired
    private DefinitionAction definitionAction;

    private User mockUser = null;
    private Document defaultDocument = null;
    private Definition defaultDefinition = null;

    @Override
    @BeforeEach
    public void prepareTest() {
        OAuthAttributes attr = new OAuthAttributes(null, null, userName, userMail, pictureUrl);
        mockUser = userService.saveOrUpdate(attr);
        defaultDocument = documentAction.storeItem(defaultDocumentTitle, loginWriter);
        defaultDefinition = definitionAction.storeItem(loginWriter, defaultContent, defaultDocument);
    }

    @Override
    @AfterEach
    public void tearDown() {
//        repo.deleteAll();
    }

    @Test
    public void chkIsPrepared() {
        List<Like> list = repo.findAll();

        assertThat(mockUser, is(notNullValue()));
        assertThat(defaultDocument, is(notNullValue()));
        assertThat(defaultDefinition, is(notNullValue()));
        assertThat(list.size(), is(0));
    }
}
