package kr.hh.liverary.domain.document;

import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.common.document.DocumentServiceAndRepoTestCommon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class DocumentRepoTest extends DocumentServiceAndRepoTestCommon {

    @DisplayName("저장관련테스트")
    @Nested
    class Create implements CrudInterface.CreateTestInterface {
        @Override
        @DisplayName("저장성공")
        @Test
        public void success()  throws Exception{
            // given
            String loginWriter = mockUser.getEmail();

            // when
            storeItem(loginWriter, slangTitle1);
            storeItem(nonLoginWriterIp, slangTitle2);

            // then
            List<Document> list = repo.findAll();
            assertThat(list.get(0).getTitle(), is(slangTitle1));
            assertThat(list.get(1).getTitle(), is(slangTitle2));
            assertThat(list.get(0).getWriter(), is(mockUser.getEmail()));
            assertThat(list.get(1).getWriter(), is(nonLoginWriterIp));
        }
    }

    @DisplayName("수정관련테스트")
    @Nested
    class Modify implements CrudInterface.ModifyTestInterface {
        @Override
        @DisplayName("수정성공")
        @Test
        public void success() throws Exception {
            // given
            Long documentId = storeItem(loginWriter, slangTitle1).getId();

            // when
            Document document1 = repo.findByTitle(slangTitle1).update(slangTitle2, nonLoginWriterIp);
            storeItem(document1);

            // then
            allItems = findAll();
            assertThat(allItems.size(), is(1));
            assertThat(allItems.get(0).getTitle(), is(slangTitle2));
            assertThat(allItems.get(0).getWriter(), is(nonLoginWriterIp));
        }
    }
}
