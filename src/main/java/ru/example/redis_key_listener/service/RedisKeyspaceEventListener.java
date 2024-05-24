package ru.example.redis_key_listener.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisKeyspaceEventListener implements MessageListener, Runnable {

    private static final int RATE_LIMIT = 10;
    private static final int DELAY_SECONDS = 5;
    private final BlockingQueue<String> eventQueue = new LinkedBlockingQueue<>();
    private Thread workerThread;

    public RedisKeyspaceEventListener() {
        workerThread = new Thread(this);
        workerThread.start();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            eventQueue.put(expiredKey);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (true) {
            List<String> expiredKeys = new LinkedList<>();
            eventQueue.drainTo(expiredKeys, RATE_LIMIT);
            if (!expiredKeys.isEmpty()) {
                try {
                    handleExpiredKey(String.valueOf(expiredKeys));
                    TimeUnit.SECONDS.sleep(DELAY_SECONDS);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private void handleExpiredKey(String expiredKey) {
        log.info("Key expired: {}", expiredKey);

    }
}