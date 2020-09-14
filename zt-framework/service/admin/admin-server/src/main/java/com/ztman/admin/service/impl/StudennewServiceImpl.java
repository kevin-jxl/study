package com.ztman.admin.service.impl;

import com.ztman.admin.api.entity.Studennew;
import com.ztman.admin.mapper.StudennewMapper;
import com.ztman.admin.service.StudennewService;
import com.ztman.common.data.datascope.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;



@Service("studennewService")
public class StudennewServiceImpl extends ServiceImpl<StudennewMapper, Studennew> implements StudennewService {

      @Autowired
      private  StudennewMapper studennewMapper;



    /**
     * 查询学生表测试信息
     *
     * @param id 学生表测试ID
     * @return 学生表测试信息
     */
    @Override
    public Studennew selectStudennewById(Integer id)
    {
        return studennewMapper.selectStudennewById(id);
    }

    /**
     * 查询学生表测试列表--带数据权限
     *
     * @param studennew 学生表测试信息
     * @return 学生表测试集合
     */
    @Override
    public List<Studennew> selectStudennewList(Studennew studennew,DataScope dataScope )
    {
        return studennewMapper.selectStudennewList(studennew,dataScope);
    }

    /**
     * 查询学生表测试列表--不带数据权限
     *
     * @param studennew 学生表测试信息
     * @return 学生表测试集合
     */
    @Override
    public List<Studennew> selectStudennewList(Studennew studennew )
    {
        return studennewMapper.selectStudennewList(studennew);
    }


    /**
     * 新增学生表测试
     *
     * @param studennew 学生表测试信息
     * @return 结果
     */
    @Override
    public int insertStudennew(Studennew studennew)
    {
        return studennewMapper.insertStudennew(studennew);
    }

    /**
     * 修改学生表测试
     *
     * @param studennew 学生表测试信息
     * @return 结果
     */
    @Override
    public int updateStudennew(Studennew studennew)
    {
        return studennewMapper.updateStudennew(studennew);
    }


    /**
     * 删除学生表测试
     *
     * @param id 学生表测试ID
     * @return 结果
     */
    @Override
    public int deleteStudennewById(Integer id)
    {
        return studennewMapper.deleteStudennewById( id);
    };


    /**
     * 批量删除学生表测试对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStudennewByIds(String[] ids)
    {
        return studennewMapper.deleteStudennewByIds( ids);
    }

}
