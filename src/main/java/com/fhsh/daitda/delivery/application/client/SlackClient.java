package com.fhsh.daitda.delivery.application.client;

import java.util.UUID;

public interface SlackClient {
    void sendMessage(String receiverEmail, String format, UUID orderId, String hubTransitStart);
}
