package codemark.testservice.common;

import codemark.testservice.model.dto.mapping.RoleMapper;
import codemark.testservice.model.dto.mapping.UserMapper;
import codemark.testservice.repository.RoleRepository;
import codemark.testservice.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public abstract class BaseControllerTests {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected RoleMapper roleMapper;
}
