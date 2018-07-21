package totable;

import common.DBTools;
import common.DataTypeEnum;
import common.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mengxiangli on 2018/6/24.
 */
public class MySqlSwitchTable implements SwitchTable {
    private static DatabaseMetaData metaData = null;
    private static String catalog;
    private static String schema;


    static {
        Properties properties = PropertiesUtil.loadProperties("table.properties");
        catalog = (String) properties.get("catalog");
        schema = properties.getProperty("schema");
    }

    private static Log logger = LogFactory.getLog(MySqlSwitchTable.class);

    {
        try {
            getetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public DatabaseMetaData getetaData() throws Exception {
        if (metaData == null) {
            synchronized (MySqlSwitchTable.class) {
                if (metaData == null) {
                    metaData = DBTools.getConnection().getMetaData();
                }
            }
        }
        return metaData;
    }

    @Override
    public List<String> needSwTables() throws Exception {
        List<String> tabList = new ArrayList<>();
        ResultSet tableRes = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
        while (tableRes.next()) {
            tabList.add(tableRes.getString("TABLE_NAME"));
        }
        return tabList;
    }

    @Override
    public void toClassFile(List<String> tablesName) throws Exception {
        List<String> tabList = needSwTables();
        File file = new File("entity");
        String tarPath = "F:\\workspace\\javaIdeaWorkspace\\tabletoclass\\src\\entity";
        tabList.stream().forEach(tab -> {
            String tabName = strNomal(tab, true);
            File javaFile = new File(tarPath + "\\" + tabName + ".java");
            try {
                javaFile.createNewFile();
                ResultSet columnRet = metaData.getColumns(catalog, schema, tab, "%");
                while (columnRet.next()) {
                    writeFile(tabName, columnRet, javaFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void writeFile(String tableName, ResultSet res, File file) throws Exception {
        //创建输出流
        BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
        StringBuilder sb = new StringBuilder();
        bfw.write(sb.append("public class ").append(strNomal(tableName, true)).append("{").toString());
        while (res.next()) {
            bfw.newLine();
            bfw.newLine();
            String columnName = res.getString("COLUMN_NAME");
            String typeName = res.getString("TYPE_NAME");
            String javaName = strNomal(columnName, false);
            String javaType = DataTypeEnum.get(typeName);
            bfw.write("private " + javaType + " " + javaName + ";");
        }
        bfw.newLine();
        bfw.write("}");
        bfw.flush();
        bfw.close();
    }


    private static String strNomal(String sour, boolean isClass) {
        if (sour == null) {
            return "";
        }

        char[] charArr = sour.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (i == 0 && isClass) {
                char c = charArr[0];
                charArr[0] = new String(new char[]{c}).toUpperCase().charAt(0);
            } else if (i > 0) {
                char c = charArr[i - 1];
                if (c == '_') {
                    charArr[i] = new String(new char[]{charArr[i]}).toUpperCase().charAt(0);
                }
            }
        }

        String newStr = new String(charArr);
        return newStr.replace("_", "");
    }


    public static void main(String[] args) {
        System.out.println(strNomal("user_id", false));
    }
}
