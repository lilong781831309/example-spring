import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xinhua.example.spring.cache.redis.ExampleApplication;
import org.xinhua.example.spring.cache.redis.config.CacheProperties;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class CachePropertiesTests {

    @Autowired
    private CacheProperties cacheProperties;

    @Test
    public void test() throws Exception {
        String prefix = cacheProperties.getPrefix();
        Long defaultTtl = cacheProperties.getDefaultTtl();
        Map<String, Long> customTtls = cacheProperties.getCustomTtls();

        System.out.println(prefix);
        System.out.println(defaultTtl);
        System.out.println(customTtls);
    }

}
