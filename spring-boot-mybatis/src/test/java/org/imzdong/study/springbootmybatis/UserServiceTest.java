package org.imzdong.study.springbootmybatis;

import org.imzdong.study.springbootmybatis.dao.HealthLevelMapper;
import org.imzdong.study.springbootmybatis.dao.HealthRecordMapper;
import org.imzdong.study.springbootmybatis.dao.HealthTaskMapper;
import org.imzdong.study.springbootmybatis.dao.UserMapper;
import org.imzdong.study.springbootmybatis.model.HealthLevel;
import org.imzdong.study.springbootmybatis.model.HealthRecord;
import org.imzdong.study.springbootmybatis.model.HealthTask;
import org.imzdong.study.springbootmybatis.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "org.imzdong.study.springbootmybatis.dao")
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HealthLevelMapper healthLevelMapper;
    @Autowired
    private HealthRecordMapper healthRecordMapper;
    @Autowired
    private HealthTaskMapper healthTaskMapper;

    @Test
    public void insertUser() {
        for(int id = 1;id<10;id++){
            User user = new User();
            user.setId(id);
            user.setUsername("Winter"+id);
            int result = userMapper.insert(user);
            System.out.println(result);
        }
    }

    @Test
    public void insertHealthLevel() {
        for(int id = 1;id<10;id++){
            HealthLevel model = new HealthLevel();
            model.setId(id);
            model.setLevel_name("等级:"+id);
            int result = healthLevelMapper.insert(model);
            System.out.println(result);
        }
    }

    @Test
    public void insertHealthRecord() {
        for(int id = 1;id<10;id++){
            HealthRecord model = new HealthRecord();
            model.setId(id);
            model.setLevel_id(id);
            model.setUser_id(id);
            model.setRemark("记录："+id);
            int result = healthRecordMapper.insert(model);
            System.out.println(result);
        }
    }

    @Test
    public void insertHealthTask() {
        for(int id = 1;id<10;id++){
            HealthTask model = new HealthTask();
            model.setId(id);
            model.setRecord_id(id);
            model.setUser_id(id);
            model.setTask_name("任务："+id);
            int result = healthTaskMapper.insert(model);
            System.out.println(result);
        }
    }

    @Test
    public void selectAll() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        List<HealthLevel> healthLevels = healthLevelMapper.selectAll();
        System.out.println(healthLevels);
        List<HealthRecord> healthRecords = healthRecordMapper.selectAll();
        System.out.println(healthRecords);
        List<HealthTask> healthTasks = healthTaskMapper.selectAll();
        System.out.println(healthTasks);
    }
}
