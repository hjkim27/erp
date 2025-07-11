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
 *     mybatis Integer array typeHandler
 * </pre>
 */
@MappedTypes(ArrayList.class)
@MappedJdbcTypes(JdbcType.ARRAY)
public class IntegerListTypeHandler extends BaseTypeHandler<List<Integer>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int colIdx, List<Integer> list, JdbcType jdbcType) throws SQLException {
        if (list == null || list.isEmpty()) {
            ps.setArray(colIdx, null);
            return;
        }
        Integer[] datas = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            datas[i] = list.get(i);
        }
        ps.setArray(colIdx, ps.getConnection().createArrayOf("integer", datas));
    }

    @Override
    public List<Integer> getNullableResult(ResultSet rs, String colName) throws SQLException {
        return getArrayListFromSqlArray(rs.getArray(colName));
    }

    @Override
    public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArrayListFromSqlArray(rs.getArray(columnIndex));
    }

    @Override
    public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArrayListFromSqlArray(cs.getArray(columnIndex));
    }

    private List<Integer> getArrayListFromSqlArray(java.sql.Array sqlArray) throws SQLException {
        if (sqlArray == null) {
            return null;
        }

        Integer[] datas = (Integer[]) sqlArray.getArray();
        if (datas == null) {
            return null;
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            list.add(datas[i]);
        }
        return list;
    }
}
