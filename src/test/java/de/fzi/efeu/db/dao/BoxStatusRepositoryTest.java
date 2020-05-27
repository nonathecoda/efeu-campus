package de.fzi.efeu.db.dao;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.fzi.efeu.db.model.BoxLoad;
import de.fzi.efeu.db.model.BoxState;
import de.fzi.efeu.db.model.BoxStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoxStatusRepositoryTest {
    @Autowired
    private BoxStatusRepository boxStatusRepository;

    @Test
    public void whenFindById_thenShouldBeFound() {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 5, 27, 17, 17);
        BoxStatus boxStatus = new BoxStatus(1L, BoxState.Free, 42.0, 13.0, BoxLoad.Empty, localDateTime);
        boxStatusRepository.save(boxStatus);

        boxStatus = boxStatusRepository.findByBoxId(1L);
        assertThat(boxStatus, notNullValue());
        assertThat(boxStatus.getTimestamp(), is(localDateTime));
    }

    @Test
    public void whenFindByInvalidId_thenShouldBeNull() {
        BoxStatus boxStatus = new BoxStatus(1L, BoxState.Free, 42.0, 13.0, BoxLoad.Empty, LocalDateTime.now());
        boxStatusRepository.save(boxStatus);

        boxStatus = boxStatusRepository.findByBoxId(2L);
        assertThat(boxStatus, nullValue());
    }
}
