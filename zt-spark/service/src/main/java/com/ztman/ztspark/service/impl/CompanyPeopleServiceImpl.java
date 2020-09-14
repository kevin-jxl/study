package com.ztman.ztspark.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.tools.other.PinYinUtil;
import com.ztman.ztspark.entity.company.CompanyPeople;
import com.ztman.ztspark.mapper.CompanyPeopleMapper;
import com.ztman.ztspark.service.CompanyPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Service("companyPeopleService")
public class CompanyPeopleServiceImpl extends ServiceImpl<CompanyPeopleMapper, CompanyPeople> implements CompanyPeopleService {

    @Autowired
    private CompanyPeopleMapper companyPeopleMapper;

    /**
     * 查询人员管理表信息
     *
     * @param id 人员管理表ID
     * @return 人员管理表信息
     */
    @Override
    public CompanyPeople selectCompanyPeopleById(Integer id)
    {
        return companyPeopleMapper.selectCompanyPeopleById(id);
    }

    /**
     * 查询人员管理表列表
     *
     * @param companyPeople 人员管理表信息
     * @return 人员管理表集合
     */
    @Override
    public List<CompanyPeople> selectCompanyPeopleList(CompanyPeople companyPeople)
    {
        return companyPeopleMapper.selectCompanyPeopleList(companyPeople);
    }


    /**
     * 分页模糊查询人员管理表列表
     * @return 人员管理表集合
     */
    @Override
    public Page selectCompanyPeopleListByLike(Query query)
    {
        CompanyPeople companyPeople =  BeanUtil.mapToBean(query.getCondition(), CompanyPeople.class,false);
        query.setRecords(companyPeopleMapper.selectCompanyPeopleListByLike(query,companyPeople));
        return query;
    }

    /**
     * 新增人员管理表
     *
     * @param companyPeople 人员管理表信息
     * @return 结果
     */
    @Override
    public int insertCompanyPeople(CompanyPeople companyPeople)
    {
        SecruityUser user = SecurityUtils.getUserInfo();
        Integer comId = companyPeople.getComId();

        companyPeople.setCreateTime(new Date());
        companyPeople.setDelFlag("1");
        boolean flag = true;
        String username = PinYinUtil.toPinyin(companyPeople.getName());
        while (flag) {
            SysUser sysUser = companyPeopleMapper.selectCompanyPeopleByUserName(username);
            if (null == sysUser) {
                flag = false;
            } else {
                username += PinYinUtil.randomChar();
            }
        }

        companyPeople.setUserName(username);

        SysUser sysUser = new SysUser();
        sysUser.setUsername(companyPeople.getUserName());
        sysUser.setUserRealname(companyPeople.getName());
        sysUser.setPhone(companyPeople.getTel());
//        sysUser.setOrganId(Integer.valueOf(companyPeople.getParkingLot()));
//        sysUser.setPostName(companyPeople.getDepartment() + "");
//        sysUser.setServiceOrganId(Integer.valueOf(companyPeople.getCompany()));
        if (null != comId) {
            sysUser.setCompanyId(companyPeople.getComId());
        }
        sysUser.setPassword(SecureUtil.md5("123456"));
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);

        sysUser.setCreateBy(String.valueOf(user.getId()));
        sysUser.setTenantFlag("0");

        companyPeopleMapper.insertSysConsumer(sysUser);
        return companyPeopleMapper.insertCompanyPeople(companyPeople);
    }

    /**
     * 修改人员管理表
     *
     * @param companyPeople 人员管理表信息
     * @return 结果
     */
    @Override
    public int updateCompanyPeople(CompanyPeople companyPeople)
    {
        companyPeople.setUpdateTime(new Date());
        SecruityUser user = SecurityUtils.getUserInfo();
        Integer comId = companyPeople.getComId();
        SysUser sysUser = new SysUser();
        sysUser.setUserRealname(companyPeople.getName());
        sysUser.setPhone(companyPeople.getTel());
//        sysUser.setOrganId(Integer.valueOf(companyPeople.getParkingLot()));
//        sysUser.setPostName(companyPeople.getDepartment() + "");
//        sysUser.setServiceOrganId(Integer.valueOf(companyPeople.getCompany()));
        if (null != comId) {
            sysUser.setCompanyId(comId);
        }
        sysUser.setUserId(companyPeople.getId());

        companyPeopleMapper.updateSysUser(sysUser);
        return companyPeopleMapper.updateCompanyPeople(companyPeople);
    }


    /**
     * 删除人员管理表
     *
     * @param id 人员管理表ID
     * @return 结果
     */
    public int deleteCompanyPeopleById(Integer id)
    {
        return companyPeopleMapper.deleteCompanyPeopleById( id);
    };


    /**
     * 批量删除人员管理表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCompanyPeopleByIds(String[] ids)
    {
        return companyPeopleMapper.deleteCompanyPeopleByIds( ids);
    }

    @Override
    public Page selectCompanyPeopleListByComId(Query query) {
        CompanyPeople companyPeople =  BeanUtil.mapToBean(query.getCondition(), CompanyPeople.class,false);
        query.setRecords(companyPeopleMapper.selectCompanyPeopleListByComId(query,companyPeople));
        return query;
    }

    @Override
    public String selectCompanyIdsByUserId(int userId) {
        List<Integer> ids = companyPeopleMapper.selectCompanyIdsByUserId(userId);
        String companyIds = "";
        for (int i = 0; i < ids.size(); i ++) {
            companyIds += "," + ids.get(i);
        }
        if (companyIds.length() > 0) {
            return companyIds.substring(1);
        }
        return null;
    }

    @Override
    public List<CompanyPeople> inputList(Query query) {
        CompanyPeople companyPeople =  BeanUtil.mapToBean(query.getCondition(), CompanyPeople.class,false);

        List<CompanyPeople> list = companyPeopleMapper.inputList(companyPeople);

        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, new Comparator<CompanyPeople>() {
            @Override
            public int compare(CompanyPeople o1, CompanyPeople o2) {
                return com.compare(o1.getName(), o2.getName());
            }
        });
        return list;
    }

    @Override
    public CompanyPeople selectCompanyPeopleByTel(CompanyPeople companyPeople) {

        return companyPeopleMapper.selectCompanyPeopleByTel(companyPeople);
    }

}
