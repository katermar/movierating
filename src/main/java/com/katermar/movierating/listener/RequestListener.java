package com.katermar.movierating.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.io.UnsupportedEncodingException;

public class RequestListener implements ServletRequestListener {
    private static final Logger logger = LogManager.getLogger(ServletRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest request = servletRequestEvent.getServletRequest();
        try {
            if (request.getCharacterEncoding() == null) {
                request.setCharacterEncoding("UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage());
            logger.info("using default request encoding (not utf-8)");
        }
    }
}
