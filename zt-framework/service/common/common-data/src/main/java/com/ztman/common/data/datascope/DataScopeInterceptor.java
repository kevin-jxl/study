package com.ztman.common.data.datascope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.ztman.common.core.exception.CheckedException;
import com.ztman.common.data.enums.DataScopeTypeEnum;
import com.ztman.common.data.enums.PowerLabelTypeEnum;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author joy
 * <p>
 * mybatis 数据权限拦截器
 * 用户信息，时间，原始sql（originalSql） sql类型(!SqlCommandType.SELECT)
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor implements Interceptor {
    private final DataSource dataSource;

    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
//        return invocation.proceed();
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (!SqlCommandType.SELECT.equals(sqlCommandType)) {
              return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
//        return invocation.proceed();

        //查找参数中包含DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameterObject);
        String user = SecurityUtils.getUser();
        if (user == null) {
            return invocation.proceed();
//            throw new CheckedException("auto datascope, set up security details true");
        }

        //生成网关拦截日志
        SecruityUser secruityUser = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.parse(new EsLog(String.valueOf(secruityUser.getId()), secruityUser.getUserName(), secruityUser.getEmpNo(), secruityUser.getDeptId(), "", "", "", "", "", "", DateUtil.now(), "", LogType.ZTMAN_INTERCEPTOR.toString(), originalSql, sqlCommandType.toString())).toString() + "es_end!");

//        //封装个性化权限SQL：根据登陆名获取权限列表
//        String powerLabelSql = "select power_table ,power_filed ,power_value ,power_control  from sys_power_label  pl where pl.del_flag=1 and power_flag=1  and  pl.id in \n" +
//                "(\n" +
//                " select distinct substring_index(substring_index(a.power_ids,',',b.help_topic_id+1),',',-1)   \n" +
//                "from (\n" +
//                "select pd.power_ids from sys_user u\n" +
//                "left join sys_user_postdummy up \n" +
//                "on u.user_id=up.user_id\n" +
//                "left join sys_post_dummy pd\t\n" +
//                "on up.posdummy_id=pd.id\n" +
//                "where u.username= ? \n" +
//                ")  a join  mysql.help_topic b  on b.help_topic_id < (length(a.power_ids) - length(replace(a.power_ids,',',''))+1)\n" +
//                ")";
//        List<Entity> powerList = Db.use(dataSource).query(powerLabelSql, user);
//        boolean isControl = false;
//        if (powerList != null && powerList.size() > 0) {
//            for (Entity e : powerList) {
//                if (originalSql.contains(e.getStr("power_table"))) {
//                    isControl = true;
//                    break;
//                }
//
//            }
//        }

//        if (dataScope == null && !isControl) {
        if (dataScope == null) {
            return invocation.proceed();
        }
//        控制权限到部门
        if (dataScope != null) {
            String centersql = controlSqlByDeptId(originalSql, dataScope, user);
            if (!"".equals(centersql)) {
                originalSql = centersql;
            }
        }
//        //控制权限到数据标签
//        if (powerList != null && powerList.size() > 0) {
//            for (Entity e : powerList) {
//                originalSql = controlSqlByLabel(originalSql, e);
//            }
//        }
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        return invocation.proceed();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    private DataScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                }
            }
        }
        return null;
    }


    /**
     * 将List<String>集合 转化为String
     */
    public String convertListToString(List<String> strlist) {
        StringBuffer sb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(strlist)) {
            for (int i = 0; i < strlist.size(); i++) {
                if (i == 0) {
                    sb.append("'").append(strlist.get(i)).append("'");
                } else {
                    sb.append(",").append("'").append(strlist.get(i)).append("'");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 控制权限到部门
     *
     * @param originalSql
     * @param dataScope
     * @param user
     * @return
     */
    public String controlSqlByDeptId(String originalSql, DataScope dataScope, String user) throws SQLException {
        String scopeName = dataScope.getScopeName();
        List<Integer> deptIds = dataScope.getDeptIds();

        // 优先获取赋值数据
        if (CollUtil.isEmpty(deptIds)) {

            //查询用户详细信息
            Entity userpo = Db.use(dataSource).queryOne("select * from sys_user where username = ?", user);
            List<String> roleIdList = SecurityUtils.getRoles();
            String rolecodes = convertListToString(roleIdList);
            String roleQuery = "SELECT * FROM sys_role where role_code IN (" + rolecodes + ")";
            //将权限信息压入缓存 待处理
            Entity query = Db.use(dataSource)
                    .query(roleQuery)
                    .stream().min(Comparator.comparingInt(o -> o.getInt("ds_type"))).get();

            Integer dsType = query.getInt("ds_type");
            // 查询全部
            if (DataScopeTypeEnum.ALL.getType() == dsType) {
                return "";
            }
            // 自定义
            if (DataScopeTypeEnum.CUSTOM.getType() == dsType) {
                String dsScope = query.getStr("ds_scope");
                deptIds.addAll(Arrays.stream(dsScope.split(","))
                        .map(Integer::parseInt).collect(Collectors.toList()));
            }
            // 查询本级及其下级
            if (DataScopeTypeEnum.OWN_CHILD_LEVEL.getType() == dsType) {
                String ids = Db.use(dataSource).queryString("select getDeptChildList( ? );", userpo.getInt("dept_id")).replace("^,", "");
                String[] idsarray = ids.split(",");
                Integer[] inIds = (Integer[]) ConvertUtils.convert(idsarray, Integer.class);
                List<Integer> deptIdList = Arrays.asList(inIds);
                deptIds.addAll(deptIdList);
            }
            // 只查询本级
            if (DataScopeTypeEnum.OWN_LEVEL.getType() == dsType) {
                deptIds.add(userpo.getInt("dept_id"));
            }
        }
        String join = CollectionUtil.join(deptIds, ",");
        //封装部门权限SQL
        originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
        return originalSql;
    }

    /**
     * 控制权限到数据标签：根据原始SQL 是否存在 权限表名称
     *
     * @param originalSql
     * @param entity
     * @return
     */
    public String controlSqlByLabel(String originalSql, Entity entity) {
        String etableName = entity.getStr("power_table");
        int epowerControl = entity.getInt("power_control");
        String epowerFiled = entity.getStr("power_filed");
        String epowerValue = entity.getStr("power_value");

        if (originalSql.contains(etableName)) {
            if (epowerControl == PowerLabelTypeEnum.EQ.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " = " + epowerValue + " ";
            } else if (epowerControl == PowerLabelTypeEnum.UN_EQ.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " != " + epowerValue + " ";
            } else if (epowerControl == PowerLabelTypeEnum.CONTAINS.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " in (" + epowerValue + " ) ";
            } else if (epowerControl == PowerLabelTypeEnum.UN_CONTAINS.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " not in (" + epowerValue + " ) ";
            } else if (epowerControl == PowerLabelTypeEnum.GREATER_THAN.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " > " + epowerValue + " ";
            } else if (epowerControl == PowerLabelTypeEnum.GREATER_THAN_OR_EQ.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " >= " + epowerValue + " ";
            } else if (epowerControl == PowerLabelTypeEnum.LESS_THAN.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " < " + epowerValue + " ";
            } else if (epowerControl == PowerLabelTypeEnum.LESS_THAN_OR_EQ.getType()) {
                originalSql = "select * from (" + originalSql + ") power_control where power_control." + epowerFiled + " <= " + epowerValue + " ";
            }

        }
        return originalSql;
    }


}
