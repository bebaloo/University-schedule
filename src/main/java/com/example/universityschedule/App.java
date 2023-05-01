package com.example.universityschedule;

import com.example.universityschedule.entity.Group;
import com.example.universityschedule.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class App implements ApplicationRunner {
    private final GroupService groupService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //System.out.println(groupService.create(new Group("aa-11")).getName());

        Group group = new Group("22");
        group.setId(1L);
        System.out.println(groupService.update(group));

        //System.out.println(groupService.getById(1L).getName());
    }
}
