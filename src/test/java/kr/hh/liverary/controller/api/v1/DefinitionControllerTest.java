package kr.hh.liverary.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hh.liverary.common.content.DefinitionControllerTestCommon;
import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.dto.ContentRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefinitionControllerTest extends DefinitionControllerTestCommon {
    private String apiVersion = "/v1";

    @DisplayName("ContentController - 저장관련테스트")
    @Nested
    class Create implements CrudInterface.CreateTestInterface {

        @Override
        @DisplayName("1.1. 추가성공")
        @Test
        public void success() throws Exception {
            // given
            String url = prefixUrl + apiVersion + "/definitions";
            ContentRequestDto requestDto = ContentRequestDto.builder()
                    .content(defaultContent)
                    .writer(loginWriter)
                    .document(defaultDocument)
            .build();
            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);

            // when
            mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isCreated())
                    .andDo(print());
            // then
            List<Definition> all = contentRepo.findAll();
            assertThat(all.size()).isEqualTo(1);
            Definition content = all.get(0);
            assertThat(content.getContent()).isEqualTo(defaultContent);
            assertThat(content.getWriter()).isEqualTo(loginWriter);
            assertThat(content.getDocument().getTitle()).isEqualTo(defaultDocument.getTitle());
        }

        @Test
        @DisplayName("2.1. Document를 지정하지 않아 저장실패-예외발생")
        public void failNoDocument() throws Exception {
            // given
            String url = prefixUrl + apiVersion + "/definitions";
            ContentRequestDto requestDto = ContentRequestDto.builder()
                    .content(defaultContent)
                    .writer(loginWriter)
                    .document(null)
                    .build();
            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);

            // when
            mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isBadRequest())
//                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":1}"))
                    .andDo(print());
            // then
            List<Definition> all = contentRepo.findAll();
            assertThat(all.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("2.2. 지정한 Document가 DB내에 존재하지 않아 저장실패-예외발생")
        public void failNoSuchDocument() throws Exception {
            // given
            String url = prefixUrl + apiVersion + "/definitions";
            String notExistsDocumentTitle = "킹받다";
            Document notExistsDocument = Document.builder()
                    .title(notExistsDocumentTitle)
                    .writer(null)
            .build();
            ContentRequestDto requestDto = ContentRequestDto.builder()
                    .content(defaultContent)
                    .writer(loginWriter)
                    .document(notExistsDocument)
                    .build();
            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);

            // when
            mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isNotFound())
//                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":1}"))
                    .andDo(print());
            // then
            List<Definition> all = contentRepo.findAll();
            assertThat(all.size()).isEqualTo(0);
        }

//        @Test
//        @DisplayName("1.2. Document 추가 후 Content 추가-성공")
//        public void saveDocumentThenSaveContent() throws Exception {
//            // given
//            String url = prefixUrl + apiVersion + "/documents-collection";
//            String newDocumentTitle = "ㅋㅋ루삥뽕";
//            Document documentThatWillBeCreated = Document.builder()
//                    .title(newDocumentTitle)
//                    .writer(nonLoginWriterIp)
//            .build();
//            ContentRequestDto requestDto = ContentRequestDto.builder()
//                    .content(defaultContent)
//                    .writer(loginWriter)
//                    .document(documentThatWillBeCreated)
//            .build();
//
//            String contentDtoToJson = new ObjectMapper().writeValueAsString(requestDto);
//
//            // when
//            mvc.perform(post(url)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
////                            .content(documentDtoToJson)
//                            .content(contentDtoToJson))
//                    .andExpect(status().isCreated())
////                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":1}"))
//                    .andDo(print());
//            // then
//            List<Content> all = contentRepo.findAll();
//            assertThat(all.size()).isEqualTo(-1);
//            Content content = all.get(0);
//            assertThat(content.getContent()).isEqualTo(defaultContent);
//            assertThat(content.getWriter()).isEqualTo(loginWriter);
//            assertThat(content.getDocument().getTitle()).isEqualTo(defaultDocument.getTitle());
//        }
    }

    @Nested
    @DisplayName("ContentController - 수정관련테스트")
    class Modify implements CrudInterface.ModifyTestInterface {

        @Override
        @DisplayName("1.1. 수정성공")
        @Test
        public void success() throws Exception {
            // given
            Definition savedItem = storeItem(nonLoginWriterIp, content2, defaultDocument);
            String url = prefixUrl + apiVersion + "/definitions/" + savedItem.getId();
            ContentRequestDto requestDto = ContentRequestDto.builder()
                    .content(defaultContent)
                    .writer(loginWriter)
                    .document(defaultDocument)
                    .build();
            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);

            // when
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isCreated())
                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":{\"id\":" + savedItem.getId() + "}}"))
                    .andDo(print());
            // then
            List<Definition> all = contentRepo.findAll();
            assertThat(all.size()).isEqualTo(1);
            Definition content = all.get(0);
            assertThat(content.getContent()).isEqualTo(defaultContent);
            assertThat(content.getWriter()).isEqualTo(loginWriter);
            assertThat(content.getDocument().getTitle()).isEqualTo(defaultDocument.getTitle());
        }

        @DisplayName("2.1. 존재하지 않는 항목에 대한 수정 시도-404")
        @Test
        public void failNoSuchDefinition() throws Exception {
            // given
            Definition savedItem = storeItem(nonLoginWriterIp, content2, defaultDocument);
            String url = prefixUrl + apiVersion + "/definitions/" + savedItem.getId()+2;
            ContentRequestDto requestDto = ContentRequestDto.builder()
                    .content(defaultContent)
                    .writer(loginWriter)
                    .document(defaultDocument)
                    .build();
            String dtoToJson = new ObjectMapper().writeValueAsString(requestDto);

            // when
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(dtoToJson))
                    .andExpect(status().isNotFound())
//                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":1}"))
                    .andDo(print());
            // then
            List<Definition> all = contentRepo.findAll();
            assertThat(all.size()).isEqualTo(1);
            Definition content = all.get(0);
            assertThat(content.getContent()).isEqualTo(content2);
            assertThat(content.getWriter()).isEqualTo(nonLoginWriterIp);
            assertThat(content.getDocument().getTitle()).isEqualTo(defaultDocument.getTitle());
        }
    }

    @DisplayName("ContentService - 조회관련테스트")
    @Nested
    class Inquiry implements CrudInterface.InquiryTestInterface {

        @Override
        @DisplayName("1.1. 문서제목으로 모든 정의 검색 - 성공")
        @Test
        public void findAll() throws Exception {
            // given
            Definition content2Item = storeItem(nonLoginWriterIp, content2, defaultDocument);
            Definition defaultContentItem = storeItem(nonLoginWriterIp, defaultContent, defaultDocument);
            String url = prefixUrl + apiVersion + "/documents/" + defaultDocument.getTitle() + "/definitions";
            String documentJsonStr = "{\"id\":" + defaultDocument.getId() + "," +
                            "\"title\":" + defaultDocument.getTitle() + "," +
                            "\"writer\":" + defaultDocument.getWriter() + "}";
            String expectedContent = "{" +
                    "\"code\":200," +
                    "\"data\":{" +
                        "\"size\":2," +
                        "\"items\":[" +
                            "{\"id\":" + content2Item.getId() + "," +
                            "\"writer\":" + nonLoginWriterIp + "," +
                            "\"content\":\"" + content2 + "\"," +
                            "\"document\":" + documentJsonStr + "}," +
                            "{" +
                                "\"id\":" + defaultContentItem.getId() + "," +
                                "\"writer\":" + nonLoginWriterIp + "," +
                                "\"content\":\"" + defaultContent +  "\"," +
                                "\"document\":" + documentJsonStr + "}]" +
                    "}," +
                    "\"message\":\"OK\"}";

            // then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedContent))
                    .andDo(print());
        }

        @DisplayName("1.2. document 제목으로 문서 검색-결과없음")
        @Test
        public void findAllNoResult() throws Exception {
            // given
            String url = prefixUrl + apiVersion + "/documents/" + defaultDocument.getTitle() + "/definitions";
            String expectedContent = "{\"code\":200,\"data\":{\"size\":0,\"items\":[]},\"message\":\"OK\"}";

                    // then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedContent))
                    .andDo(print());
        }

        @DisplayName("1.3. 50개의 최신 definition-collection 검색")
        @Test
        public void getRandomItems() throws Exception {
            // given
            Document tempDocument = saveDocument("temp", "tempuser");
            Definition content2Item = storeItem(nonLoginWriterIp, content2, defaultDocument);
            Definition defaultContentItem = storeItem(nonLoginWriterIp, defaultContent, defaultDocument);
            Definition tempDefinition =  storeItem("tempuser", "꼴받다", tempDocument);
            String url = prefixUrl + apiVersion + "/definitions/latest/50";

            //////// expected //////////
            String defaultDocumentJsonStr =
                    "{\"id\":" + defaultDocument.getId() + "," +
                    "\"title\":\"" + defaultDocument.getTitle() + "\"," +
                    "\"writer\":\"" + defaultDocument.getWriter() + "\"}";

            String tempDocumentJsonStr =
                    "{\"id\":" + tempDocument.getId() + "," +
                    "\"title\":\"" + tempDocument.getTitle() + "\"," +
                    "\"writer\":\"" + tempDocument.getWriter() + "\"}"
            ;


            String content2ItemJsonStr =
                    "{\"id\":" + content2Item.getId() + "," +
                    "\"writer\":\"" + nonLoginWriterIp + "\"," +
                    "\"content\":\"" + content2 + "\"," +
                    "\"document\":" + defaultDocumentJsonStr + "}"
            ;
            String defaultContentItemJsonStr =
                    "{\"id\":" + defaultContentItem.getId() + "," +
                    "\"writer\":\"" + nonLoginWriterIp + "\"," +
                    "\"content\":\"" + defaultContent +  "\"," +
                    "\"document\":" + defaultDocumentJsonStr + "}"
                    ;
            String tempDefinitionJsonStr =
                    "{\"id\":" + tempDefinition.getId() + "," +
                    "\"writer\":\"" + tempDefinition.getWriter() + "\"," +
                    "\"content\":\"" + tempDefinition.getContent() +  "\"," +
                    "\"document\":" + tempDocumentJsonStr + "}"
                    ;

            String expectedContent = "{" +
                    "\"code\":200," +
                    "\"data\":{" +
                        "\"size\":3," +
                        "\"items\":["
                    + content2ItemJsonStr + ","
                    + defaultContentItemJsonStr + ","
                    + tempDefinitionJsonStr +
                    "]" +
                        "}," +
                    "\"message\":\"OK\"}";

            // then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedContent))
                    .andDo(print());
        }

        @DisplayName("2.1. 존재하지 않는 document 제목으로 문서 검색-예외발생")
        @Test
        public void failFindAllNoSuchDocument() throws Exception {
            // given
            String nonExistDocumentTitle = "어쩔티비";
            storeItem(nonLoginWriterIp, content2, defaultDocument);
            storeItem(nonLoginWriterIp, defaultContent, defaultDocument);
            String url = prefixUrl + apiVersion + "/documents/" + nonExistDocumentTitle + "/definitions";

            // then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}
