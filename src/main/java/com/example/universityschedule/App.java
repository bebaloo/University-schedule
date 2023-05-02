package com.example.universityschedule;

import com.example.universityschedule.service.DataGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class App implements ApplicationRunner {
    private final DataGeneratorService dataGeneratorService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataGeneratorService.fillDB();
    }
}
