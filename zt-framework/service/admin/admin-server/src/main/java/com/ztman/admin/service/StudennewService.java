package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.Studennew;
import com.ztman.common.data.datascope.DataScope;

import java.util.List;


/**
 * 学生表测试 服务接口层
 *
 * @author ztman code generator
 * @date 2019-01-22 15:53:05
 */
public interface StudennewService extends IService<Studennew> {
    /**
     * 查询学生表测试信息
     *
     * @param id 学生表测试ID
     * @return 学生表测试信息
     */
    public Studennew selectStudennewById(Integer id);

    /**
     * 查询学生表测试列表--带数据权限
     *
     * @param studennew 学生表测试信息
     * @return 学生表测试集合
     */
    public List<Studennew> selectStudennewList(Studennew studennew, DataScope dataScope);


    /**
     * 查询学生表测试列表--不带数据权限
     *
     * @param studennew 学生表测试信息
     * @return 学生表测试集合
     */
    public List<Studennew> selectStudennewList(Studennew studennew);

    /**
     * 新增学生表测试
     *
     * @param studennew 学生表测试信息
     * @return 结果
     */
    public int insertStudennew(Studennew studennew);

    /**
     * 删除学生表测试
     *
     * @param id 学生表测试ID
     * @return 结果
     */
    public int deleteStudennewById(Integer id);

    /**
     * 修改学生表测试
     *
     * @param studennew 学生表测试信息
     * @return 结果
     */
    public int updateStudennew(Studennew studennew);

    /**
     * 批量删除学生表测试
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStudennewByIds(String[] ids);

}

