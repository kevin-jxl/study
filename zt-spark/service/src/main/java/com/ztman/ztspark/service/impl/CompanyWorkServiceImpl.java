package com.ztman.ztspark.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.CompanyPeople;
import com.ztman.ztspark.entity.company.CompanyWork;
import com.ztman.ztspark.mapper.CompanyWorkMapper;
import com.ztman.ztspark.service.CompanyWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@Service("companyWorkService")
public class CompanyWorkServiceImpl extends ServiceImpl<CompanyWorkMapper, CompanyWork> implements CompanyWorkService {

    @Autowired
    private CompanyWorkMapper companyWorkMapper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询考勤管理表信息
     *
     * @param workId 考勤管理表ID
     * @return 考勤管理表信息
     */
    @Override
    public CompanyWork selectCompanyWorkById(Integer workId)
    {
        return companyWorkMapper.selectCompanyWorkById(workId);
    }

    /**
     * 查询考勤管理表列表
     *
     * @param companyWork 考勤管理表信息
     * @return 考勤管理表集合
     */
    @Override
    public List<CompanyWork> selectCompanyWorkList(CompanyWork companyWork)
    {
        return companyWorkMapper.selectCompanyWorkList(companyWork);
    }


    /**
     * 分页模糊查询考勤管理表列表
     * @return 考勤管理表集合
     */
    @Override
    public Page selectCompanyWorkListByLike(Query query)
    {
        CompanyWork companyWork =  BeanUtil.mapToBean(query.getCondition(), CompanyWork.class,false);
        String startTime = companyWork.getStartTime();
        if (null != startTime && !startTime.equals("")) {
            companyWork.setStartTime(startTime + "-01");
        }
        query.setRecords(companyWorkMapper.selectCompanyWorkListByLike(query,companyWork));
        return query;
    }

    /**
     * 新增考勤管理表
     *
     * @param companyWork 考勤管理表信息
     * @return 结果
     */
    @Override
    public int insertCompanyWork(CompanyWork companyWork)
    {
        companyWork.setDelFlag("1");
        return companyWorkMapper.insertCompanyWork(companyWork);
    }

    /**
     * 修改考勤管理表
     *
     * @param companyWork 考勤管理表信息
     * @return 结果
     */
    @Override
    public int updateCompanyWork(CompanyWork companyWork)
    {
        return companyWorkMapper.updateCompanyWork(companyWork);
    }


    /**
     * 删除考勤管理表
     *
     * @param workId 考勤管理表ID
     * @return 结果
     */
    public int deleteCompanyWorkById(Integer workId)
    {
        return companyWorkMapper.deleteCompanyWorkById( workId);
    };


    /**
     * 批量删除考勤管理表对象
     *
     * @param workIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCompanyWorkByIds(String[] workIds)
    {
        return companyWorkMapper.deleteCompanyWorkByIds( workIds);
    }

    @Override
    public void generateDutyInfo() {

        companyWorkMapper.generateDutyInfo(getFirstDay(), getLastDay());
    }

    @Override
    public int addCompanyWorkByPeople(CompanyPeople companyPeople) {

        companyPeople.setFirstDay(getFirstDay());
        companyPeople.setLastDay(getLastDay());

        return companyWorkMapper.addCompanyWorkByPeople(companyPeople);
    }

    @Override
    public int deleteCompanyWorkByPeopleId(Integer id) {
        return companyWorkMapper.deleteCompanyWorkByPeopleId(id);
    }

    public String getFirstDay() {
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
//        System.out.println("===============first:"+first);
        return first;
    }

    public String getLastDay() {
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
//        System.out.println("===============last:"+last);
        return last;
    }
}
