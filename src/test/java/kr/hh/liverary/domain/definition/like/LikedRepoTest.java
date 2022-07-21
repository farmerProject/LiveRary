package kr.hh.liverary.domain.definition.like;

import kr.hh.liverary.common.definition.DefinitionAction;
import kr.hh.liverary.common.document.DocumentAction;
import kr.hh.liverary.common.interfaces.TempTestInterface;
import kr.hh.liverary.config.auth.CustomOAuth2UserService;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
public class LikedRepoTest implements TempTestInterface {

    @Autowired
    private LikedRepository repo;
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
        repo.deleteAll();
    }

    @DisplayName("0. 테스트전 각 항목들이 정상적으로 준비되었는지 확인한다.")
    @Test
    public void chkIsPrepared() {
        List<Liked> list = repo.findAll();

        assertThat(mockUser, is(notNullValue()));
        assertThat(defaultDocument, is(notNullValue()));
        assertThat(defaultDefinition, is(notNullValue()));
        assertThat(list.size(), is(0));
    }

    private Liked storeItem(Definition definition, boolean isLiked) {
        Liked liked = repo.save(Liked.builder().writer(mockUser.getEmail()).definition(definition).isLiked(isLiked).build());
        return liked;
    }

    private Liked storeItem(Definition definition, boolean isLiked, String writerIp) {
        Liked liked = repo.save(Liked.builder().writer(writerIp).definition(definition).isLiked(isLiked).build());
        return liked;
    }

    @DisplayName("1. 추가 관련 테스트")
    @Nested
    class Create {
        @DisplayName("1.1.1. 좋아요를 정상적으로 추가한다.")
        @Test
        public void liked() throws Exception {
            Liked liked = storeItem(defaultDefinition, true);
            List<Liked> likedList = repo.findAll();

            assertThat(liked.getDefinition().getId(), is(defaultDefinition.getId()));
            assertThat(liked.isLiked(), is(true));
            assertThat(likedList.size(), is(1));
        }

        @DisplayName("1.1.2. 싫어요를 정상적으로 추가한다.")
        @Test
        public void disliked() throws Exception {
            Liked liked = storeItem(defaultDefinition, false);
            List<Liked> likedList = repo.findAll();

            assertThat(liked.getDefinition().getId(), is(defaultDefinition.getId()));
            assertThat(liked.isLiked(), is(false));
            assertThat(likedList.size(), is(1));

        }
    }

    @DisplayName("2. 삭제 관련 테스트")
    @Nested
    class Remove{
        @DisplayName("2.1. 좋아요/싫어요 삭제를 성공한다.")
        @Test
        public void removeLikeOrDislike() throws Exception {
            Definition tempDefinition = definitionAction.storeItem(loginWriter, "현실자각타임임!!", defaultDocument);
            Liked liked = storeItem(defaultDefinition, false);
            storeItem(tempDefinition, true);

            repo.delete(liked);

            List<Liked> likedList = repo.findAll();

            assertThat(likedList.size(), is(1));
        }
    }

    @DisplayName("3. 조회 관련 테스트")
    @Nested
    class Inquiry{

        @DisplayName("3.1. 특정 게시물의 좋아요/싫어요 컬럼의 전체 개수를 확인한다.")
        @Test
        public void getLikedCountByDefinitionId() throws Exception {
            OAuthAttributes attr = new OAuthAttributes(null, null, "zzz", "df@a.com", pictureUrl);
            Definition tempDefinition = definitionAction.storeItem(loginWriter, "현실자각타임임!!", defaultDocument);

            storeItem(defaultDefinition, true);
            storeItem(defaultDefinition, true, "192.168.0.1");
            storeItem(defaultDefinition, true);
            storeItem(defaultDefinition, false); //
            storeItem(tempDefinition, true);

            int count = repo.countByDefinitionIdAndIsLiked(defaultDefinition.getId(), true);

            assertThat(count, is(3));
        }

        @DisplayName("3.2. 특정 유저가 특정 게시물에 대한 좋아요/싫어요를 한 횟수를 확인한다.")
        @Test
        public void getLikedCountByDefinitionIdAndWriter() throws Exception {
            Definition tempDefinition = definitionAction.storeItem(loginWriter, "현실자각타임임!!", defaultDocument);

            storeItem(defaultDefinition, true);
            storeItem(defaultDefinition, true);
            storeItem(defaultDefinition, true, "192.168.0.1");
            storeItem(defaultDefinition, false); //
            storeItem(tempDefinition, true);

            int count = repo.countByDefinitionIdAndWriter(defaultDefinition.getId(), "192.168.0.1");

            assertThat(count, is(1));
        }

        @DisplayName("3.3. 작성자와 definition으로 좋아요를 조회한다.")
        @Test
        public void findByDefinitionAndWriter() throws Exception {
            Definition tempDefinition = definitionAction.storeItem(loginWriter, "현실자각타임임!!", defaultDocument);

            storeItem(defaultDefinition, true);
            storeItem(defaultDefinition, true, "192.168.0.1");
            storeItem(defaultDefinition, true);
            storeItem(defaultDefinition, false); //
            storeItem(tempDefinition, true);

            Liked liked = repo.findByDefinitionAndWriter(defaultDefinition, "192.168.0.1");

            assertThat(liked.getDefinition().getContent(), is(defaultDefinition.getContent()));
            assertThat(liked.getWriter(), is("192.168.0.1"));

        }

    }

}
