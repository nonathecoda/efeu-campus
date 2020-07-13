package de.fzi.efeu.db.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

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
        ZoneId zone = ZoneId.of("Europe/Berlin");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, zoneOffSet);
        BoxStatus boxStatus = new BoxStatus("1", BoxState.FREE, 42.0, 13.0, BoxLoad.EMPTY, offsetDateTime);
        boxStatusRepository.save(boxStatus);

        boxStatus = boxStatusRepository.findByBoxId("1");
        assertThat(boxStatus, notNullValue());
//        assertThat(boxStatus.getTimestamp(), is(offsetDateTime));
    }

    @Test
    public void whenFindByInvalidId_thenShouldBeNull() {
        BoxStatus boxStatus = new BoxStatus("1", BoxState.FREE, 42.0, 13.0, BoxLoad.EMPTY, OffsetDateTime.now());
        boxStatusRepository.save(boxStatus);

        boxStatus = boxStatusRepository.findByBoxId("2");
        assertThat(boxStatus, nullValue());
    }
}
