package hjkim27.dev.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     mybatis String array typeHandler
 * </pre>
 */
@MappedTypes(ArrayList.class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int colIdx, List<String> list, JdbcType jdbcType) throws SQLException {
        if (list == null || list.isEmpty()) {
            ps.setArray(colIdx, null);
            return;
        }
        String[] datas = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            datas[i] = list.get(i);
        }
        ps.setArray(colIdx, ps.getConnection().createArrayOf("integer", datas));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String colName) throws SQLException {
        return getArrayListFromSqlArray(rs.getArray(colName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArrayListFromSqlArray(rs.getArray(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArrayListFromSqlArray(cs.getArray(columnIndex));
    }

    private List<String> getArrayListFromSqlArray(java.sql.Array sqlArray) throws SQLException {
        if (sqlArray == null) {
            return null;
        }

        String[] datas = (String[]) sqlArray.getArray();
        if (datas == null) {
            return null;
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            list.add(datas[i]);
        }
        return list;
    }
}
