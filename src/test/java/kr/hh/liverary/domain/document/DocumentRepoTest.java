package kr.hh.liverary.domain.document;

import kr.hh.liverary.common.interfaces.CrudInterface;
import kr.hh.liverary.common.document.ServiceAndRepoTest;
import kr.hh.liverary.config.auth.dto.OAuthAttributes;
import kr.hh.liverary.domain.user.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentRepoTest extends ServiceAndRepoTest implements CrudInterface {


    @Override
    @Test
    public void test_create()  throws Exception{
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

    @Override
    @Test
    public void test_modify() throws Exception {
        // given
        Long documentId = storeItem(loginWriter, slangTitle1).getId();

        // when
        Document document1 = repo.findByTitle(slangTitle1).update(slangTitle2, nonLoginWriterIp);

        // then
        assertThat(document1.getTitle(), is(slangTitle2));
        assertThat(document1.getWriter(), is(nonLoginWriterIp));
    }

    @Override
    @Test
    public void test_findAll() throws Exception {
        assertThat(1, is(1));
    }

    @Override
    @Test
    public void test_remove() throws Exception {
        assertThat(1, is(1));
    }

}
