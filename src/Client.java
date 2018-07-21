import totable.MySqlSwitchTable;

import java.io.File;
import java.util.List;

/**
 * Created by mengxiangli on 2018/6/24.
 */
public class Client {

    public static void main(String[] args) throws Exception{
        MySqlSwitchTable mySqlSwitchTable = new MySqlSwitchTable();
        List<String> strings = mySqlSwitchTable.needSwTables();
        mySqlSwitchTable.toClassFile(strings);
    }
}
