package kr.hh.liverary.service;

import kr.hh.liverary.common.interfaces.TempTestInterface;
import kr.hh.liverary.config.auth.CustomOAuth2UserService;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.definition.DefinitionRepository;
import kr.hh.liverary.domain.definition.like.Liked;
import kr.hh.liverary.domain.definition.like.LikedRepository;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.InvalidParameterException;
import kr.hh.liverary.domain.user.User;
import kr.hh.liverary.dto.LikedRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class LikedServiceTest implements TempTestInterface {

    @Autowired
    private LikedService service;
    @Autowired
    private CustomOAuth2UserService userService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DefinitionRepository definitionRepository;
    @Autowired
    private LikedRepository repository;

    private User mockUser;
    private Document defaultDocument;
    private Definition defaultDefinition;

    @Override
    @BeforeEach
    public void prepareTest() {
        OAuthAttributes attr = new OAuthAttributes(null, null, userName, userMail, pictureUrl);
        mockUser = userService.saveOrUpdate(attr);
        defaultDocument = saveDocument(defaultDocumentTitle, userMail);
        defaultDefinition = saveDefinition(defaultDocument, defaultContent, userMail);
    }

    private Document saveDocument(String title, String writer) {
        return documentRepository.save(Document.builder().title(title).writer(writer).build());
    }

    private Definition saveDefinition(Document document, String content, String writer) {
        return definitionRepository.save(Definition.builder().document(document).content(content).writer(writer).build());
    }

    private Liked storeItem() {
        LikedRequestDto dto1 = LikedRequestDto.builder()
                .writer(mockUser.getEmail())
                .definition(defaultDefinition)
                .isLiked(true)
                .build();
        return repository.save(dto1.toEntity());
    }

    private Liked storeItem(boolean isLiked) {
        LikedRequestDto dto1 = LikedRequestDto.builder()
                .writer(mockUser.getEmail())
                .definition(defaultDefinition)
                .isLiked(isLiked)
                .build();
        return repository.save(dto1.toEntity());
    }

    private Liked storeItem(LikedRequestDto dto) {
        return repository.save(dto.toEntity());
    }

    @Override
    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("1. 추가관련테스트")
    @Nested
    class Create  {

        @Test
        @DisplayName("1.1.1. like 추가-성공")
        public void like() throws Exception {
            // given
            LikedRequestDto dto1 = LikedRequestDto.builder()
                    .writer(mockUser.getEmail())
                    .definition(defaultDefinition)
                    .isLiked(true)
            .build();

            // when
            service.create(dto1);

            // then
            List<Liked> allItems = repository.findAll();

            assertThat(allItems.size(), is(1));
            Liked savedItem = allItems.get(0);

            assertThat(savedItem.isLiked(), is(true));
        }

        @Test
        @DisplayName("1.1.2. 싫어요 추가-성공")
        public void dislike() throws Exception {
            // given
            LikedRequestDto dto1 = LikedRequestDto.builder()
                    .writer(mockUser.getEmail())
                    .definition(defaultDefinition)
                    .isLiked(false)
                    .build();

            // when
            service.create(dto1);

            // then
            List<Liked> allItems = repository.findAll();

            assertThat(allItems.size(), is(1));
            Liked savedItem = allItems.get(0);

            assertThat(savedItem.isLiked(), is(false));
        }

        @Test
        @DisplayName("1.2.1. 지정한 definition의 id가 존재하지 않아 저장실패-예외발생")
        public void failNoSuchUser() throws Exception {
            // given
            Definition tempDefinition = Definition.builder().writer("me").content("123").document(defaultDocument).build();
            LikedRequestDto dto1 = LikedRequestDto.builder()
                    .writer(mockUser.getEmail())
                    .definition(tempDefinition)
                    .isLiked(false)
                    .build();

            // when
            Assertions.assertThrows(InvalidParameterException.class, () -> {
                service.create(dto1);
            });


            // then
            List<Liked> allItems = repository.findAll();
            assertThat(allItems.size(), is(0));
        }
    }

    @DisplayName("2. 삭제관련 테스트")
    @Nested
    class Delete {

        @DisplayName("2.1. 삭제성공")
        @Test
        public void delete() throws Exception{
            // given
            LikedRequestDto dto1 = LikedRequestDto.builder()
                    .writer(mockUser.getEmail())
                    .definition(defaultDefinition)
                    .isLiked(true)
                    .build();
            Liked liked = storeItem(dto1);
            // when
            service.cancel(dto1);

            // then
            List<Liked> allItems = repository.findAll();

            assertThat(allItems.size(), is(0));
        }

        @DisplayName("2.2.1. 삭제실패")
        @Test
        public void deleteFailedBecauseThereIsNoSuchWriterWithDefinition() throws Exception{
            // given
            LikedRequestDto targetDTO = LikedRequestDto.builder()
                    .writer("notPresenceUser")
                    .definition(defaultDefinition)
                    .isLiked(true)
                    .build();
            storeItem();

            // when
            Assertions.assertThrows(RequestedItemIsNotFoundException.class, () -> {
                service.cancel(targetDTO);
            });

        }
    }
    @DisplayName("3. 조회관련테스트")
    @Nested
    class Inquiry{
        @DisplayName("3.1.1. 해당 게시물에 반응을 누른적이 있는지 확인 - 있음")
        @Test
        public void userHaveReaction() throws Exception {
            // given
            LikedRequestDto targetDTO = LikedRequestDto.builder()
                    .writer("192.168.0.1")
                    .definition(defaultDefinition)
                    .isLiked(true)
                    .build();
            storeItem();
            storeItem(targetDTO);

            // when
            boolean haveReaction = service.haveUserReactionOnDefinition(targetDTO);

            // then
            assertThat(haveReaction, is(true));
        }

        @DisplayName("3.1.2. 해당 게시물에 반응을 누른적이 있는지 확인 - 없음")
        @Test
        public void userDoesntHaveReaction() throws Exception {
            // given
            LikedRequestDto targetDTO = LikedRequestDto.builder()
                    .writer("192.168.0.1")
                    .definition(defaultDefinition)
                    .isLiked(true)
                    .build();
            storeItem();

            // when
            boolean haveReaction = service.haveUserReactionOnDefinition(targetDTO);

            // then
            assertThat(haveReaction, is(false));
        }

        @DisplayName("3.2. 해당 게시물의 총 반응 수 (좋아요-싫어요)")
        @Test
        public void getCountReactionsByDefinition() throws Exception {
            // given
            LikedRequestDto targetDTO = LikedRequestDto.builder()
                    .writer("192.168.0.1")
                    .definition(defaultDefinition)
                    .isLiked(false)
                    .build();
            storeItem(targetDTO);
            storeItem(targetDTO);
            storeItem(targetDTO);

            // when
            int likes = service.getReactionsByDefinition(targetDTO.getDefinition().getId());

            // then
            assertThat(likes, is(-3));
        }
    }
}
