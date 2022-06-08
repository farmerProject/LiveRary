package kr.hh.liverary.domain.content;

import kr.hh.liverary.common.content.ContentServiceAndRepoTest;
import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.domain.document.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class ContentRepoTestDocument extends ContentServiceAndRepoTest {

    @DisplayName("ContentRepo - 추가관련테스트")
    @Nested
    class Create implements CrudInterface.CreateTestInterface {

        @Override
        @DisplayName("1. 추가성공")
        @Test
        public void success() throws Exception {
            // given
            // when
            storeItem(loginWriter, defaultContent, defaultDocument);

            // then
            List<Content> list = repo.findAll();

            assertThat(list.size(), is(1));
            assertThat(list.get(0).getContent(), is(defaultContent));
            assertThat(list.get(0).getWriter(), is(loginWriter));
            assertThat(list.get(0).getDocument().getId(), is(defaultDocument.getId()));
        }
    }

    @DisplayName("ContentRepo - 수정관련테스트")
    @Nested
    class Modify implements CrudInterface.ModifyTestInterface {

        @Override
        @DisplayName("1. 수정성공")
        @Test
        public void success() throws Exception {
            // given
            Content prevContent = storeItem(loginWriter, defaultContent, defaultDocument);
            Long contentId = prevContent.getId();

            // when
            Content modifiedContent = repo.findById(contentId).map(
                    entity -> entity.update(nonLoginWriterIp, content2)
            ).get();
            storeItem(modifiedContent);

            // then
            List<Content> list = repo.findAll();
            assertThat(list.size(), is(1));
            assertThat(list.get(0).getContent(), is(content2));
            assertThat(list.get(0).getWriter(), is(nonLoginWriterIp));
        }
    }

    @DisplayName("ContentRepo - 검색관련테스트")
    @Nested
    class Inquiry implements CrudInterface.InquiryTestInterface {
        Document anotherDocument = null;
        String anotherDocumentTitle = "ㅋㅋㄹㅃㅃ";

        @Override
        @DisplayName("1. documentID로 전체검색")
        @Test
        public void findAll() throws Exception {
            // given
            anotherDocument = saveDocument(anotherDocumentTitle,nonLoginWriterIp);
            storeItem(loginWriter, defaultContent, defaultDocument);
            storeItem(loginWriter, content2, defaultDocument);

            // when
            List<Object> allItems = repo.findByDocument(defaultDocument);

            // then
            assertThat(allItems.size(), is(2));
        }

        @DisplayName("2. Primary key - id로 특정 항목 검색")
        @Test
        public void findById() throws Exception {
            // given
            Long id = storeItem(loginWriter, defaultContent, defaultDocument).getId();
            storeItem(loginWriter, content2, defaultDocument);

            // when
            Content foundItem = repo.findById(id).get();

            // then
            assertThat(foundItem.getContent(), is(defaultContent));
        }

    }
}
