package no.libak.caseservice.service;

import no.libak.caseservice.api.EventDto;
import no.libak.caseservice.exception.CaseServiceSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

@Service
@EnableJms
public class EventService {

    private final JmsTemplate jmsTemplate;
    private final JsonMapper jsonMapper;

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private static final String EVENT_QUEUE = "case.event";

    public EventService(JmsTemplate jmsTemplate, JsonMapper jsonMapper) {
        this.jmsTemplate = jmsTemplate;
        this.jsonMapper = jsonMapper;
    }

    public void sendEvent(EventDto eventDto) {
        log.debug("Sending event {}", eventDto);

        try {
            jmsTemplate.convertAndSend(EVENT_QUEUE, jsonMapper.writeValueAsString(eventDto));
        } catch (Exception e) {
            throw new CaseServiceSystemException(e.getMessage());
        }
    }

}
