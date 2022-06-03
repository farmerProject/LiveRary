package kr.hh.liverary.service;

import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.TitleDuplicatedException;
import kr.hh.liverary.dto.DocumentRequestDto;
import kr.hh.liverary.common.document.DocumentServiceAndRepoTestCommon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class DocumentServiceTest extends DocumentServiceAndRepoTestCommon {

    @Autowired
    DocumentService service;

    @DisplayName("저장관련테스트")
    @Nested
    class Create implements CrudInterface.CreateTestInterface {

        @Override
        @DisplayName("저장성공")
        @Test
        public void success() throws Exception {
            // given
            DocumentRequestDto dto = DocumentRequestDto.builder()
                    .title(slangTitle1)
                    .writer(loginWriter)
                    .build();

            // when
            service.create(dto);

            // then
            allItems = repo.findAll();
            assertThat(allItems.size(), is(1));
            assertThat(allItems.get(0).getTitle(), is(slangTitle1));
        }

        @DisplayName("저장실패 - 중복된 제목 예외 발생")
        @Test
        public void failDuplicatedTitle() throws Exception {
            // given
            storeItem(loginWriter, slangTitle1);
            DocumentRequestDto dto = DocumentRequestDto.builder()
                    .title(slangTitle1)
                    .writer(nonLoginWriterIp)
                    .build();

            // when
            Assertions.assertThrows(TitleDuplicatedException.class, () -> {
                service.create(dto);
            });
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
            storeItem(loginWriter, slangTitle1);
            DocumentRequestDto dto = DocumentRequestDto.builder()
                    .title(slangTitle2)
                    .writer(nonLoginWriterIp)
                    .build();

            // when
            service.modify(slangTitle1, dto);

            // then
            allItems = repo.findAll();
            assertThat(allItems.size(), is(1));
            assertThat(allItems.get(0).getTitle(), is(slangTitle2));
        }

        @DisplayName("수정실패 - 중복된 제목 예외 발생")
        @Test
        public void failDuplicatedTitle() throws Exception {
            // given
            storeItem(loginWriter, slangTitle1);
            DocumentRequestDto dto = DocumentRequestDto.builder()
                    .title(slangTitle1)
                    .writer(nonLoginWriterIp)
                    .build();

            // when
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                service.modify(slangTitle1, dto);
            });
        }

        @DisplayName("수정실패 - 존재하지 않는 제목 수정 시도 예외 발생")
        @Test
        public void failThereIsNoSuchTitle() throws Exception {
            // given
            storeItem(loginWriter, slangTitle1);
            DocumentRequestDto dto = DocumentRequestDto.builder()
                    .title(slangTitle2)
                    .writer(nonLoginWriterIp)
                    .build();

            // when
            Assertions.assertThrows(RequestedItemIsNotFoundException.class, () -> {
                service.modify("존재하지않는제목", dto);
            });
        }

        @DisplayName("수정실패 - 수정하려는 제목의 문서가 이미 존재함. 예외 발생")
        @Test
        public void failThereIsAlreadySameDocumentInDB() throws Exception {
            // given
            storeItem(loginWriter, slangTitle1);
            storeItem(loginWriter, slangTitle2);
            DocumentRequestDto dto = DocumentRequestDto.builder()
                    .title(slangTitle2)
                    .writer(nonLoginWriterIp)
                    .build();

            // when
            Assertions.assertThrows(TitleDuplicatedException.class, () -> {
                service.modify(slangTitle1, dto);
            });

        }
    }
}
