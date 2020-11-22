package net.zhaoxiaobin.jpabasic;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.jpabasic.domain.Actor;
import net.zhaoxiaobin.jpabasic.service.ActorSaveAndDeleteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaoxb
 * @date 2020/11/21 10:34 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActorSaveAndDeleteTest {
    @Autowired
    private ActorSaveAndDeleteService actorSaveAndDeleteService;

    @Test
    public void testSave() {
        Actor actor = actorSaveAndDeleteService.save("高庸涵", 28, "13486627323@163.com");
        log.info(JSONUtil.toJsonPrettyStr(actor));
    }

    @Test
    public void testBatchSave() {
        actorSaveAndDeleteService.batchSave();
    }

    @Test
    public void testDelete() {
        actorSaveAndDeleteService.delete(10L);
    }
}