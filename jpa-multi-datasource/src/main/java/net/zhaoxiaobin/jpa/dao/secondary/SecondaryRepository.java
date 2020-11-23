package net.zhaoxiaobin.jpa.dao.secondary;

import net.zhaoxiaobin.jpa.domain.secondary.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhaoxb
 * @date 2020/11/23 4:34 下午
 */
public interface SecondaryRepository extends JpaRepository<User,Long> {
}