package net.zhaoxiaobin.jpabasic;

import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.jpabasic.service.ActorUpdateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaoxb
 * @date 2020/11/21 11:22 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActorUpdateTest {
    @Autowired
    private ActorUpdateService actorUpdateService;

    @Test
    public void testUpdateActorEmailById() {
        int row = actorUpdateService.updateActorEmailById("123456789@qq.com", 1L);
        log.info("更新数量:{}", row);
    }

    @Test
    public void testUpdateCreateTimeById() {
        int row = actorUpdateService.updateCreateTimeById(2L);
        log.info("更新数量:{}", row);
    }
}