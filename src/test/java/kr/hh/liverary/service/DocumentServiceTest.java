package kr.hh.liverary.service;

import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.dto.DocumentRequestDto;
import kr.hh.liverary.common.document.ServiceAndRepoTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceTest extends ServiceAndRepoTest implements CrudInterface {

    @Autowired
    DocumentService service;

    @Override
    @Test
    public void test_create() throws Exception {
        // given
        DocumentRequestDto dto = DocumentRequestDto.builder()
                .title(slangTitle1)
                .writer(loginWriter)
        .build();

        // when
        String title = service.create(dto);

        // then
        assertThat(title, is(slangTitle1));
    }

    @Override
    @Test
    public void test_modify() throws Exception {
        // given
        Long prevDocumentId = storeItem(loginWriter, slangTitle1).getId();
        DocumentRequestDto dto = DocumentRequestDto.builder()
                .title(slangTitle2)
                .writer(nonLoginWriterIp)
        .build();

        // when
        String newDocumentName = service.modify(slangTitle1, dto);

        // then
        assertThat(newDocumentName, is(slangTitle2));
    }

    @Override
    public void test_findAll() throws Exception {
        assertThat(1, is(1));
    }

    @Override
    public void test_remove() throws Exception {
        assertThat(1, is(1));
    }

}
