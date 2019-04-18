package net.ujacha.onmyojibot.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!log-dynamoDB")
@Slf4j
public class ConsoleLogServiceImpl implements LogService{
    @Override
    public void logNotFound(String userKey, String query) {
        log.info("USER_KEY:{}\tQUERY:{}", userKey, query);
    }
}
