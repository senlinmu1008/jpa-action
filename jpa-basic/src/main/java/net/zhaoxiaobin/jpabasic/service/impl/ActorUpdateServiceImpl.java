package net.zhaoxiaobin.jpabasic.service.impl;

import cn.hutool.core.date.DateUtil;
import net.zhaoxiaobin.jpabasic.dao.ActorUpdateRepository;
import net.zhaoxiaobin.jpabasic.service.ActorUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhaoxb
 * @date 2020/11/17 7:50 下午
 */
@Service
@Transactional
public class ActorUpdateServiceImpl implements ActorUpdateService {
    @Autowired
    private ActorUpdateRepository actorUpdateRepository;

    /**
     * 使用JPQL语句更新数据
     *
     * @param email
     * @param id
     * @return
     */
    @Override
    public int updateActorEmailById(String email, Long id) {
        return actorUpdateRepository.updateActorEmailById(email, id);
    }

    /**
     * 使用原生sql批量更新数据
     *
     * @param id
     * @return
     */
    @Override
    public int updateCreateTimeById(Long id) {
        String createTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
        return actorUpdateRepository.updateCreateTimeById(createTime, id);
    }
}