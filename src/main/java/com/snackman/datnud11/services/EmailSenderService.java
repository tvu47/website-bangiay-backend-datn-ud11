package com.snackman.datnud11.services;

public interface EmailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
