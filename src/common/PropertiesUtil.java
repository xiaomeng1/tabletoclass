package common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mengxiangli on 2018/6/24.
 */
public class PropertiesUtil {

    private static Log logger = LogFactory.getLog(PropertiesUtil.class);

    /**
     * 获取配置文件中的属性
     *
     * @param location
     * @return
     */
    public static Properties loadProperties(String location) {
        Properties properties = new Properties();
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(location));
        } catch (IOException e) {
            logger.error("获取属性失败:" + e);
            throw new RuntimeException("获取文件中的属性失败!");
        }

        return properties;
    }

}
