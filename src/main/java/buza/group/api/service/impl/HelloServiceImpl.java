package buza.group.api.service.impl;

import buza.group.api.service.HelloService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Service
public class HelloServiceImpl implements HelloService {

    private SqlSession sqlSession;

    @Override
    public List<Map<String, Object>> getAllSysUser() {
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> getAllSysUser = sqlSession.selectList("Buza.Common.getAllSysUser", params);
        return getAllSysUser;
    }
}
