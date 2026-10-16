package com.lbrce.hostel.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        int count = activeSessions.incrementAndGet();
        System.out.println("Hostel Management: Session created. Active sessions: " + count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        int count = activeSessions.updateAndGet(value -> Math.max(0, value - 1));
        System.out.println("Hostel Management: Session destroyed. Active sessions: " + count);
    }

    public static int getActiveSessionCount() {
        return activeSessions.get();
    }
}

