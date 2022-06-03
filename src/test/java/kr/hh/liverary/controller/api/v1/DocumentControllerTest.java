package kr.hh.liverary.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.dto.DocumentRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerTest extends kr.hh.liverary.common.document.DocumentControllerTestCommon {
    private String apiVersion = "/v1";

    @DisplayName("저장관련테스트")
    @Nested
    class Create implements CrudInterface.CreateTestInterface {
        @Override
        @DisplayName("저장성공")
        @Test
        public void success() throws Exception {
            // given
            String url = prefixUrl + apiVersion + "/documents";
            DocumentRequestDto requestDto = DocumentRequestDto.builder()
                    .title(slangTitle1)
                    .writer(loginWriter)
                    .build();

            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);
            // when
            mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":\"킹받다\"}"))
                    .andDo(print())
            ;

            // then
            List<Document> all = repo.findAll();
            assertThat(all.size()).isEqualTo(1);
            assertThat(all.get(0).getTitle()).isEqualTo(slangTitle1);
            assertThat(all.get(0).getWriter()).isEqualTo(loginWriter);
        }

        @DisplayName("저장실패 - 중복된 제목 예외 발생")
        @Test
        public void failDuplicatedTitle() throws Exception {
            // given
            String url = prefixUrl + apiVersion + "/documents";
            DocumentRequestDto requestDto = DocumentRequestDto.builder()
                    .title(slangTitle1)
                    .writer(loginWriter)
                    .build();

            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);
            mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(dtoToJson));

            // when
            mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isConflict())
                    .andDo(print())
            ;

            // then
            List<Document> all = repo.findAll();
            assertThat(all.size()).isEqualTo(1);
        }
    }

    @DisplayName("수정관련테스트")
    @Nested
    class Modify implements CrudInterface.ModifyTestInterface {
        @Override
        @DisplayName("수정성공")
        @WithMockUser(roles = "USER")
        @Test
        public void success() throws Exception {
            // given
            storeItem(nonLoginWriterIp, slangTitle1);
            String url = prefixUrl + apiVersion + "/documents/" + slangTitle1;
            DocumentRequestDto requestDto = DocumentRequestDto.builder()
                    .title(slangTitle2)
                    .writer(loginWriter)
                    .build();

            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);
            // when
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"code\":201,\"data\":\"ㅋㅋ루삥뽕\", \"message\":\"CREATED\"}"))
                    .andDo(print());

            // then
            List<Document> all = repo.findAll();
            assertThat(all.size()).isEqualTo(1);
            assertThat(all.get(0).getTitle()).isEqualTo(slangTitle2);
            assertThat(all.get(0).getWriter()).isEqualTo(loginWriter);
        }

        @DisplayName("수정실패 - 존재하지 않는 제목 수정 시도 예외 발생")
        @WithMockUser(roles = "USER")
        @Test
        public void failThereIsNoSuchTitle() throws Exception {
            // given
            storeItem(nonLoginWriterIp, slangTitle1);
            String url = prefixUrl + apiVersion + "/documents/" + slangTitle2;
            DocumentRequestDto requestDto = DocumentRequestDto.builder()
                    .title(slangTitle2)
                    .writer(loginWriter)
                    .build();

            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);
            // when
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isNotFound())
                    .andDo(print());

            // then
            List<Document> all = repo.findAll();
            assertThat(all.size()).isEqualTo(1);
            assertThat(all.get(0).getTitle()).isEqualTo(slangTitle1);
            assertThat(all.get(0).getWriter()).isEqualTo(nonLoginWriterIp);
        }

        @DisplayName("수정실패 - 중복된 제목 예외 발생")
        @WithMockUser(roles = "USER")
        @Test
        public void failDuplicatedtitle() throws Exception {
            // given
            storeItem(nonLoginWriterIp, slangTitle1);
            String url = prefixUrl + apiVersion + "/documents/" + slangTitle1;
            DocumentRequestDto requestDto = DocumentRequestDto.builder()
                    .title(slangTitle1)
                    .writer(loginWriter)
                    .build();

            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);
            // when
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isConflict())
                    .andDo(print());

            // then
            List<Document> all = repo.findAll();
            assertThat(all.size()).isEqualTo(1);
            assertThat(all.get(0).getTitle()).isEqualTo(slangTitle1);
            assertThat(all.get(0).getWriter()).isEqualTo(nonLoginWriterIp);
        }

        @DisplayName("수정실패 - 수정하려는 제목의 문서가 이미 존재함. 예외 발생")
        @WithMockUser(roles = "USER")
        @Test
        public void failThereIsAlreadySameDocumentInDB() throws Exception {
            // given
            storeItem(nonLoginWriterIp, slangTitle1);
            storeItem(nonLoginWriterIp, slangTitle2);
            String url = prefixUrl + apiVersion + "/documents/" + slangTitle1;
            DocumentRequestDto requestDto = DocumentRequestDto.builder()
                    .title(slangTitle2)
                    .writer(loginWriter)
                    .build();

            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);
            // when
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isConflict())
                    .andDo(print());

            // then
            List<Document> all = repo.findAll();
            assertThat(all.size()).isEqualTo(2);
            assertThat(all.get(0).getTitle()).isEqualTo(slangTitle1);
            assertThat(all.get(0).getWriter()).isEqualTo(nonLoginWriterIp);
        }
    }
}