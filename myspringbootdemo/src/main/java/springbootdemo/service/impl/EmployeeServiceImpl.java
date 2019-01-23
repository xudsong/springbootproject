package springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springbootdemo.entity.Employee;
import springbootdemo.mapper.EmployeeMapper;
import springbootdemo.service.EmployeeService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xudasong
 * @since 2018-12-12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
