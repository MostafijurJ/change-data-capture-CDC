package com.lean.cdc.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lean.cdc.sink.service.SinkDebeziumTopicService;
import com.lean.cdc.source.entity.DebeziumTopic;
import com.lean.cdc.source.service.SourceDebeziumTopicService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CDCController.class})
@ExtendWith(SpringExtension.class)
class CDCControllerTest {
    @Autowired
    private CDCController cDCController;

    @MockBean
    private SinkDebeziumTopicService sinkDebeziumTopicService;

    @MockBean
    private SourceDebeziumTopicService sourceDebeziumTopicService;

    /**
     * Method under test: {@link CDCController#getAllTopics()}
     */
    @Test
    void testGetAllTopics() throws Exception {
        when(sinkDebeziumTopicService.getAllSinks()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/debezium/getAllSinkTopics");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cDCController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link CDCController#saveData(DebeziumTopic)}
     */
    @Test
    void testSaveData() throws Exception {
        when(sourceDebeziumTopicService.saveTopic(Mockito.<DebeziumTopic>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        DebeziumTopic debeziumTopic = new DebeziumTopic();
        debeziumTopic.setAge(1);
        debeziumTopic.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        debeziumTopic.setEmail("jane.doe@example.org");
        debeziumTopic.setId(1);
        debeziumTopic.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(debeziumTopic);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/debezium/saveTopic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cDCController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

