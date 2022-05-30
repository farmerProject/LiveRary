package kr.hh.liverary.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hh.liverary.common.document.ControllerTest;
import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.dto.DocumentRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerTest extends ControllerTest implements CrudInterface {
    private String apiVersion = "/v1";

    @Override
    @Test
    @WithMockUser(roles = "USER")
    public void test_create() throws Exception {
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
        assertThat(all.get(0).getTitle()).isEqualTo(slangTitle1);
        assertThat(all.get(0).getWriter()).isEqualTo(loginWriter);
    }

    @Override
    @Test
    @WithMockUser(roles = "USER")
    public void test_modify() throws Exception {
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
                    .andExpect(content().json("{\"message\":\"CREATED\",\"code\":201,\"data\":\"ㅋㅋ루삥뽕\"}"))
        .andDo(print());

        // then
        List<Document> all = repo.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(slangTitle2);
        assertThat(all.get(0).getWriter()).isEqualTo(loginWriter);
    }

    @Override
    @Test
    @WithMockUser(roles = "USER")
    public void test_findAll() throws Exception {
        assertThat(1).isEqualTo(1);
    }

    @Override
    @Test
    @WithMockUser(roles = "USER")
    public void test_remove() throws Exception {
        assertThat(1).isEqualTo(1);
    }
}
