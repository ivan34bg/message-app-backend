package com.messaging.messagingapp.services;

import com.messaging.messagingapp.data.models.bindingModel.MessageBindingModel;
import com.messaging.messagingapp.data.models.viewModel.MessageViewModel;
import com.messaging.messagingapp.exceptions.ChatNotFoundException;
import com.messaging.messagingapp.exceptions.UserNotFoundException;

import java.io.FileNotFoundException;
import java.util.List;

public interface MessageService {
    void sendMessage(MessageBindingModel incomingMessage, String senderUsername)
            throws FileNotFoundException, IllegalAccessException, NoSuchFieldException, ChatNotFoundException, UserNotFoundException;
    List<MessageViewModel> loadPageableMessagesForChat(Long chatId, String usernameOfLoggedUser, int pageNum)
            throws IllegalAccessException, FileNotFoundException, ChatNotFoundException, UserNotFoundException;
    void deleteMessageById(Long messageId, String loggedUserUsername)
            throws NoSuchFieldException, IllegalAccessException;
}
