package net.zhaoxiaobin.dynamicquery.domain;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhaoxb
 * @date 2020/11/15 6:17 下午
 */
@Entity
@Table(name = "actor")
@Data
public class Actor {
    /**
     * 主键生成采用数据库自增方式，比如MySQL的AUTO_INCREMENT
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_name", nullable = false, length = 128, unique = true)
    private String actorName;

    @Column(name = "actor_age", nullable = false)
    private int actorAge;

    @Column(name = "actor_email", length = 64, unique = true)
    private String actorEmail;

    @Column(name = "create_time", nullable = false, length = 32)
    private String createTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
}