package totable;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * Created by mengxiangli on 2018/6/24.
 */
public interface SwitchTable {


    /**
     * 获取连接
     * @return
     */
    DatabaseMetaData getetaData() throws Exception;

    /**
     * 从配置中获取需要转换的表名
     *
     * @return
     */
    List<String> needSwTables() throws Exception;

    /**
     * 创建表对应的类文件
     *
     * @param tablesName
     */
    void toClassFile(List<String> tablesName) throws Exception;
}
