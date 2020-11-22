package net.zhaoxiaobin.dynamicquery;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.zhaoxiaobin.dynamicquery.domain.Actor;
import net.zhaoxiaobin.dynamicquery.service.SpecificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhaoxb
 * @date 2020/11/17 10:05 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpecificationTest {
    @Autowired
    private SpecificationService specificationService;

    @Test
    public void testSpecification() {
        List<Actor> actorList = specificationService.findBySpecification(1L, null, 0, 10);
        log.info(JSONUtil.toJsonPrettyStr(actorList));
    }
}