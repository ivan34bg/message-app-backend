package com.messaging.messagingapp.security.Interceptors;

import com.messaging.messagingapp.services.implementations.ParticipantServiceImplementation;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;

@Service
public class SubscriptionInterceptor implements ChannelInterceptor {
    private final ParticipantServiceImplementation participantServiceImplementation;

    public SubscriptionInterceptor(ParticipantServiceImplementation participantServiceImplementation) {
        this.participantServiceImplementation = participantServiceImplementation;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if(Objects.equals(headerAccessor.getCommand(), StompCommand.SUBSCRIBE)){
            String[] splitDestination = (String[]) Arrays
                    .stream(headerAccessor.getDestination().split("/"))
                    .toArray();
            Long chatId = Long.parseLong(splitDestination[splitDestination.length - 1]);
            Principal user = headerAccessor.getUser();
            try {
                participantServiceImplementation.returnParticipantByChatIdAndUsername(user.getName(), chatId);
            } catch (FileNotFoundException e) {
                throw new MessageDeliveryException("You cannot use this chat since you are not a participant in it.");
            }
        }
        return ChannelInterceptor.super.preSend(message, channel);
    }
}