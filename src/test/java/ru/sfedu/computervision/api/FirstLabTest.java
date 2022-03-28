package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class FirstLabTest extends LabTest {
    @Test
    public void lab1() {
        OsService.load();
    }
}
