package springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springbootdemo.entity.Dept;
import springbootdemo.mapper.DeptMapper;
import springbootdemo.service.DeptService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xudasong
 * @since 2018-12-12
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}
