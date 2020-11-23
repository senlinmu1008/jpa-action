package net.zhaoxiaobin.jpa;

import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.jpa.dao.primary.PrimaryRepository;
import net.zhaoxiaobin.jpa.dao.secondary.SecondaryRepository;
import net.zhaoxiaobin.jpa.domain.primary.Actor;
import net.zhaoxiaobin.jpa.domain.secondary.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/23 5:23 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MultiDataSourceTest {
    @Autowired
    private PrimaryRepository primaryRepository;

    @Autowired
    private SecondaryRepository secondaryRepository;

    @Test
    public void testPrimary() {
        List<Actor> actorList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Actor actor = new Actor();
            actor.setActorName("actor" + i);
            actor.setActorEmail("email" + i);
            actor.setActorAge(i + 20);
            actorList.add(actor);
        }
        primaryRepository.saveAll(actorList);

        // 验证
//        Assert.assertEquals(5, primaryRepository.findAll().size());
    }

    @Test
    public void testSecondary() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("userName" + i);
            user.setAge(i);
            userList.add(user);
        }
        secondaryRepository.saveAll(userList);

        // 验证
//        Assert.assertEquals(5, secondaryRepository.findAll().size());
    }
}