package kr.hh.liverary.service;

import kr.hh.liverary.common.content.DefinitionServiceAndRepoTest;
import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.NoDocumentParameterException;
import kr.hh.liverary.domain.exception.document.NoSuchDocumentException;
import kr.hh.liverary.dto.ContentRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DefinitionServiceTest extends DefinitionServiceAndRepoTest {

    @Autowired
    private DefinitionService service;

    @DisplayName("ContentService - 추가관련테스트")
    @Nested
    class Create implements CrudInterface.CreateTestInterface {

        @Override
        @Test
        @DisplayName("1.1. Document가 존재하는 상태에서 추가-성공")
        public void success() throws Exception {
            // given
            ContentRequestDto dto = ContentRequestDto.builder()
                    .content(defaultContent)
                    .writer(loginWriter)
                    .document(defaultDocument)
            .build();

            // when
            Long returnedId = service.create(dto);

            // then
            List<Definition> allItems = repo.findAll();
            assertThat(allItems.size()).isEqualTo(1);
            Definition savedItem = allItems.get(0);
            assertThat(savedItem.getContent()).isEqualTo(defaultContent);
            assertThat(savedItem.getWriter()).isEqualTo(loginWriter);
            assertThat(savedItem.getDocument().getId()).isEqualTo(defaultDocument.getId());
            assertThat(savedItem.getId()).isEqualTo(returnedId);
        }

//        @Test
//        @DisplayName("1.2. Document 추가 후 Content 추가-성공")
//        public void saveDocumentThenSaveContent() throws Exception {
//            // given
//            Document newDocument = Document.builder()
//                    .title("new Document")
//                    .writer(nonLoginWriterIp)
//            .build();
//            ContentRequestDto dto = ContentRequestDto.builder()
//                    .content(defaultContent)
//                    .writer(loginWriter)
//                    .document(newDocument)
//            .build();
//
//            // when
//            Long returnedId = service.createContentWithDocument(dto);
//
//
//            // then
//            List<Content> allItems = repo.findAll();
//            assertThat(allItems.size()).isEqualTo(1);
//            Content savedItem = allItems.get(0);
//            assertThat(savedItem.getContent()).isEqualTo(defaultContent);
//            assertThat(savedItem.getWriter()).isEqualTo(loginWriter);
//            assertThat(savedItem.getDocument().getTitle()).isEqualTo(newDocument.getTitle());
//
//            assertThat(savedItem.getId()).isEqualTo(returnedId);
//        }

        @Test
        @DisplayName("2.1. Document를 지정하지 않아 저장실패-예외발생")
        public void failNoDocument() throws Exception {
            // given
            ContentRequestDto dto = ContentRequestDto.builder()
                    .writer(loginWriter)
                    .content(defaultContent)
            .build();

            // when
            Assertions.assertThrows(NoDocumentParameterException.class, () -> {
                service.create(dto);
            });

            // then
            List<Definition> allItems = repo.findAll();
            assertThat(allItems.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("2.2. 지정한 Document가 DB내에 존재하지 않아 저장실패-예외발생")
        public void failNoSuchDocument() throws Exception {
            // given
            Document nonExistDocument = Document.builder()
                    .title("non-exist")
                    .writer("non user")
            .build();

            ContentRequestDto dto = ContentRequestDto.builder()
                    .writer(loginWriter)
                    .content(defaultContent)
                    .document(nonExistDocument)
            .build();

            // when
            Assertions.assertThrows(NoSuchDocumentException.class, () -> {
                service.create(dto);
            });

            // then
            List<Definition> allItems = repo.findAll();
            assertThat(allItems.size()).isEqualTo(0);
        }
    }

    @DisplayName("ContentService - 수정관련테스트")
    @Nested
    class Modify implements CrudInterface.ModifyTestInterface {

        @Override
        @DisplayName("1.1. 수정성공")
        @Test
        public void success() throws Exception {
            // given
            Definition defaultItem = storeItem(loginWriter, defaultContent, defaultDocument);
            ContentRequestDto dto = ContentRequestDto.builder()
                    .content(content2)
                    .writer(nonLoginWriterIp)
                    .document(defaultDocument)
                    .build();

            // when
            service.modify(defaultItem.getId(), dto);

            // then
            List<Definition> allItems = repo.findAll();
            assertThat(allItems.size()).isEqualTo(1);
            Definition foundItem = allItems.get(0);
            assertThat(foundItem.getContent()).isEqualTo(content2);
            assertThat(foundItem.getWriter()).isEqualTo(nonLoginWriterIp);
        }

        @DisplayName("2.1. 존재하지 않는 항목에 대한 수정 시도-예외발생")
        @Test
        public void failNoSuchDefinition() throws Exception {
            // given
            Definition defaultItem = storeItem(loginWriter, defaultContent, defaultDocument);
            ContentRequestDto dto = ContentRequestDto.builder()
                    .content(content2)
                    .writer(nonLoginWriterIp)
                    .document(defaultDocument)
                    .build();

            // when
            Assertions.assertThrows(RequestedItemIsNotFoundException.class, () ->{
                service.modify(defaultItem.getId() + 1, dto);
            });

            // then
            List<Definition> allItems = repo.findAll();
            assertThat(allItems.size()).isEqualTo(1);
            Definition foundItem = allItems.get(0);
            assertThat(foundItem.getContent()).isEqualTo(defaultContent);
            assertThat(foundItem.getWriter()).isEqualTo(loginWriter);
        }
    }

    @DisplayName("ContentService - 조회관련테스트")
    @Nested
    class Inquiry implements CrudInterface.InquiryTestInterface {
        @Override
        @DisplayName("1.1. document 제목으로 문서 검색")
        @Test
        public void findAll() throws Exception {
            // given
            storeItem(loginWriter, defaultContent, defaultDocument);
            storeItem(loginWriter, content2, defaultDocument);

            // when
            List<Object> list = service.findByDocumentTitle(defaultDocumentTitle);

            // then
            assertThat(list.size()).isEqualTo(2);
        }

        @DisplayName("1.2. document 제목으로 문서 검색-결과없음")
        @Test
        public void findAllNoResult() throws Exception {
            // given

            // when
            List<Object> list = service.findByDocumentTitle(defaultDocumentTitle);

            // then
            assertThat(list.size()).isEqualTo(0);
        }

        @DisplayName("1.3.  50개의 최신 definition-collection 검색")
        @Test
        public void getRandomItems() throws Exception {
            // given
            Document tempDocument = saveDocument("temp", "tempuser");
            storeItem(loginWriter, defaultContent, defaultDocument);
            storeItem(loginWriter, content2, defaultDocument);
            storeItem("tempuser", "꼴받다", tempDocument);

            List<Definition> list = service.findTop50ByOrderByIdDesc();

            // then
            assertThat(list.size()).isEqualTo(3);
        }


        @DisplayName("2.1. 존재하지 않는 document 제목으로 문서 검색-예외발생")
        @Test
        public void failFindAllNoSuchDocument() throws Exception {
            // given
            storeItem(loginWriter, defaultContent, defaultDocument);
            storeItem(loginWriter, content2, defaultDocument);

            Assertions.assertThrows(NoSuchDocumentException.class, () ->{
                service.findByDocumentTitle("non exist title");
            });

        }


    }
}
