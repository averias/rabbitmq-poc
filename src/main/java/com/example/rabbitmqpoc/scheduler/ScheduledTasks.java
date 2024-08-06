package com.example.rabbitmqpoc.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@AllArgsConstructor
@Slf4j
public class ScheduledTasks {
  private static final String EMAIL_VERIFICATION_JSON_STRING =
      "{\"type\" : \"emailVerification\",\"email\" : \"user%d@hello.com\"}";
  private static final String ALERT_MATCH_JSON_STRING =
      "{\"type\" : \"alertMatch\",\"match\" : \"match %d\"}";

  private final RabbitTemplate jsonRabbitTemplateWithClassMapping;
  private final Queue jsonMappedQueue;
  private final AtomicInteger counter = new AtomicInteger();

  @Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
  public void sendJsonMessageOnMappedQueue() {
    int step = counter.incrementAndGet();
    String jsonMessageString =
        (step % 2 == 0)
            ? String.format(EMAIL_VERIFICATION_JSON_STRING, step)
            : String.format(ALERT_MATCH_JSON_STRING, step);
    jsonRabbitTemplateWithClassMapping.convertAndSend(
        jsonMappedQueue.getActualName(),
        generateMessage(jsonMessageString, step)
    );
  }

  private Message generateMessage(String jsonMessageString, int step) {
    Message message =
        MessageBuilder.withBody(jsonMessageString.getBytes())
            .andProperties(
                MessagePropertiesBuilder.newInstance().setContentType("application/json").build())
            .build();

    if (step == 0) {
      return message;
    }

    String tagHeader = (step % 2 == 0) ? "emailVerification" : "alertMatch";
    message.getMessageProperties().setHeader("__TypeId__", tagHeader);

    return message;
  }
}
