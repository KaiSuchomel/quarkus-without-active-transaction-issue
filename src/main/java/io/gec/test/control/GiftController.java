package io.gec.test.control;

import io.gec.test.entity.Gift;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecordMetadata;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

@ApplicationScoped
public class GiftController {

    @Inject
    EntityManager em;
    
    @Inject
    @Channel("giftsOut")
    @OnOverflow(OnOverflow.Strategy.BUFFER)
    @Broadcast
    Emitter<String> emitter;

    private final AtomicInteger i = new AtomicInteger();

    @Scheduled(cron = "* * * ? * * *")
    public void schedule(ScheduledExecution aScheduledExecution) {
        doTransactionalStuff(new Gift().setName("gift" + i.incrementAndGet()));
    }

    @Transactional
    public void doTransactionalStuff(Gift aProducedGift) {
        System.err.println("GiftController.doTransactionalStuff: " + aProducedGift);
        Message<String> m = Message.of(aProducedGift.toString(), Metadata.of(OutgoingKafkaRecordMetadata.<String>builder()
                    .withKey(UUID.randomUUID().toString()).build()));
        em.persist(aProducedGift);
        emitter.send(m);
    }
}
