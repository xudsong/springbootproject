package springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springbootdemo.entity.UserDb;
import springbootdemo.mapper.UserDbMapper;
import springbootdemo.service.UserDbService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xudasong
 * @since 2018-12-12
 */
@Service
public class UserDbServiceImpl extends ServiceImpl<UserDbMapper, UserDb> implements UserDbService {

}
