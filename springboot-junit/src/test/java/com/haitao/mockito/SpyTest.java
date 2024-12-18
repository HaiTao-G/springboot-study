package com.haitao.mockito;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
class SpyTest {

    @Spy
    private StringUtil8 spyUtil;


    @Mock
    private Random mockRandom;


    @Test
    void testRealMethod() {

        StringUtil8 util = new StringUtil8();
        List<String> list = Arrays.asList("test", "test2");
        util.formatString(list);
        //assert the list
        List<String> expected = Arrays.asList("test: 20240101", "test2: 20240101");
        assertEquals(expected, list);
    }

    @Test
    void testSpyMethod() {

        List<String> list = Arrays.asList("test", "test2");
        given(spyUtil.formatString(Mockito.any(String.class)))
                .willAnswer((invocation) -> invocation.getArgument(0) + ": " + "20241111");
        spyUtil.formatString(list);
        //assert the list
        List<String> expected = Arrays.asList("test: 20241111", "test2: 20241111");
        assertEquals(expected, list);
    }
}

@Slf4j
class StringUtil8 {

    public void formatString(List<String> list) {
        list.replaceAll(this::formatString);

        log.info("list: {}", list);
    }

    public String formatString(String str) {
        return str + ": " + "20240101";
    }
}
