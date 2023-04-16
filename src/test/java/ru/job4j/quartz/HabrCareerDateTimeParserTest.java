package ru.job4j.quartz;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HabrCareerDateTimeParserTest {


    @Test
    void whenDateRight() {
        String result = "2023-04-16T15:27:03+03:00";
        DateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
        assertThat(dateTimeParser.parse(result)).isEqualTo("2023-04-16T15:27:03");
    }

}