package com.ztman.ztspark.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.ztspark.dto.CompanyDutyDTO;
import com.ztman.ztspark.dto.PointDutyJobDTO;
import com.ztman.ztspark.dto.ZdutyidPeopleidDTO;
import com.ztman.ztspark.entity.company.CompanyDuty;
import com.ztman.ztspark.entity.company.PointDutyJob;
import com.ztman.ztspark.entity.company.ZdutyidPeopleid;
import com.ztman.ztspark.mapper.CompanyDutyMapper;
import com.ztman.ztspark.mapper.PointDutyJobMapper;
import com.ztman.ztspark.mapper.ZdutyidPeopleidMapper;
import com.ztman.ztspark.service.CompanyDutyService;
import com.ztman.ztspark.util.SecurityTools;
import com.ztman.ztspark.vo.CompanyDutyVO;
import com.ztman.ztspark.vo.ZtDutyidPeopleidVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service("companyDutyService")
public class CompanyDutyServiceImpl extends ServiceImpl<CompanyDutyMapper, CompanyDuty> implements CompanyDutyService {

    @Autowired
    private CompanyDutyMapper companyDutyMapper;
    @Autowired
    private ZdutyidPeopleidMapper zdutyidPeopleidMapper;
    @Autowired
    private PointDutyJobMapper pointDutyJobMapper;
//    private  pointDutyJobMapper;

    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 查询值班信息管理表信息
     *
     * @param id 值班信息管理表ID
     * @return 值班信息管理表信息
     */
    @Override
    public CompanyDutyDTO selectCompanyDutyById(Integer id) {
        CompanyDutyDTO companyDutyDTO = companyDutyMapper.selectCompanyDutyById(id);
        if (companyDutyDTO != null) {
            companyDutyDTO.setTaskBool(companyDutyDTO.getTaskFlag().equals(CommonConstant.STATUS_NORMAL));
            companyDutyDTO.setTime(new String[]{date.format(companyDutyDTO.getStartTime()),date.format(companyDutyDTO.getEndTime())});
            companyDutyDTO.getPeople().forEach(people -> {
                people.setTime(new String[]{people.getDutyStartTime(),people.getDutyEndTime()});
                people.getTask().forEach(task ->{
                    task.setNumber(task.getEquipmentNum().split(","));
                });
            });
        }
        return companyDutyDTO;
    }

    /**
     * 查询值班信息管理表列表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 值班信息管理表集合
     */
    @Override
    public List<CompanyDuty> selectCompanyDutyList(CompanyDuty companyDuty) {
        return companyDutyMapper.selectCompanyDutyList(companyDuty);
    }


    /**
     * 分页模糊查询值班信息管理表列表
     *
     * @return 值班信息管理表集合
     */
    @Override
    public Page selectCompanyDutyListByLike(Query query) {
        CompanyDutyVO companyDutyVO = BeanUtil.mapToBean(query.getCondition(), CompanyDutyVO.class, false);
        List add = new ArrayList<>();
        SecruityUser userInfo = SecurityUtils.getUserInfo();
        if(!SecurityTools.checkROLE_ADMIN()){
            if("1".equals(userInfo.getTenantFlag())){
                List comids = companyDutyMapper.getTenantConpanyId(SecurityUtils.getUserInfo().getTenantId());
                add.addAll(comids);
            }else{
                add.add(userInfo.getCompanyId());
                companyDutyVO.setComIds(add);
            }
        }
        companyDutyVO.setDutyName(companyDutyVO.getDutyName().trim());
        List<CompanyDutyDTO> companyDutyDTOList = companyDutyMapper.selectCompanyDutyListByLike(query, companyDutyVO);
        List<CompanyDutyVO> VOList = new ArrayList<>();
        companyDutyDTOList.forEach(companyDutyDTO -> {
            CompanyDutyVO vo = new CompanyDutyVO();
            StringBuffer peopleName = new StringBuffer();
            vo.setId(companyDutyDTO.getId());
            String dutuStatus = ("0".equals(companyDutyDTO.getDutyStatus()) ? "未开始" : ("1".equals(companyDutyDTO.getDutyStatus()) ? "执行中" : "已结束"));
            vo.setDutyStatus(dutuStatus);
            vo.setTime(new String[]{date.format(companyDutyDTO.getStartTime()),date.format(companyDutyDTO.getEndTime())});
            vo.setCreateName(companyDutyDTO.getCreateName());
            vo.setCreatePhone(companyDutyDTO.getCreatePhone());
            companyDutyDTO.getPeople().forEach(zdutyidPeopleidDTO -> {
                peopleName.append(zdutyidPeopleidDTO.getPeopleName());
                if (companyDutyDTO.getPeople().size() > 1) {
                    peopleName.append(",");
                }
                String[] time = {zdutyidPeopleidDTO.getDutyStartTime(), zdutyidPeopleidDTO.getDutyEndTime()};
                zdutyidPeopleidDTO.setTime(time);
                zdutyidPeopleidDTO.getTask().forEach(pointDutyJobDTO -> {
                    pointDutyJobDTO.setNumber(pointDutyJobDTO.getEquipmentNum()==null?new String[]{}:pointDutyJobDTO.getEquipmentNum().split(","));
                });
            });
            vo.setDutyName(peopleName.toString());
            vo.setStartTime(companyDutyDTO.getStartTime());
            vo.setEndTime(companyDutyDTO.getEndTime());
            vo.setOrganName(companyDutyDTO.getOrganName());
            VOList.add(vo);
        });
        query.setRecords(VOList);
        return query;
    }
    private String checkDutyStatus(Date startDate,Date endDate){

//        if(){
//
//        }
        return "";
    }

    /**
     * 根据日期 查询当天值班人详情
     * @param query
     * @return
     */
    @Override
    public Page selectDutyPeopleListByLike(Query<Object> query) {
//        query.setSize(999);
        CompanyDutyVO companyDutyVO = BeanUtil.mapToBean(query.getCondition(), CompanyDutyVO.class, false);
        List add = new ArrayList<>();
        SecruityUser userInfo = SecurityUtils.getUserInfo();
        if(!SecurityTools.checkROLE_ADMIN()){
            if("1".equals(userInfo.getTenantFlag())){
                List comids = companyDutyMapper.getTenantConpanyId( SecurityUtils.getUserInfo().getTenantId());
                add.addAll(comids);
            }else{
                add.add(userInfo.getCompanyId());
                companyDutyVO.setComIds(add);
            }
        }
        List<CompanyDutyDTO> companyDutyDTOList = companyDutyMapper.selectCompanyDutyListByLike(query, companyDutyVO);
        List VOList = new ArrayList<>();
        companyDutyDTOList.forEach(companyDutyDTO -> {
            companyDutyDTO.setTime(new String[]{date.format(companyDutyDTO.getStartTime()),date.format(companyDutyDTO.getEndTime())});
            companyDutyDTO.setTaskBool(companyDutyDTO.getTaskFlag().equals(CommonConstant.STATUS_NORMAL));
            companyDutyDTO.setDutyStatus("0".equals(companyDutyDTO.getDutyStatus())?"未开始":("1".equals(companyDutyDTO.getDutyStatus())?"执行中":"已结束"));
            companyDutyDTO.getPeople().forEach(dutyPeopleList -> {
                ZtDutyidPeopleidVO vo = new ZtDutyidPeopleidVO();
                StringBuffer peopleName = new StringBuffer();
                vo.setPeopleId(dutyPeopleList.getPeopleId());
                vo.setDutyStartTime(dutyPeopleList.getDutyStartTime());
                vo.setDutyEndTime(dutyPeopleList.getDutyEndTime());
                String[] time = {dutyPeopleList.getDutyStartTime(), dutyPeopleList.getDutyEndTime()};
                vo.setTime(time);

                dutyPeopleList.getTask().forEach(pointDutyJobDTO -> {
                    pointDutyJobDTO.setNumber(pointDutyJobDTO.getEquipmentNum()==null?new String[]{}:pointDutyJobDTO.getEquipmentNum().split(","));
                });

                vo.setTask(dutyPeopleList.getTask());
                vo.setTaskDescription(companyDutyDTO.getTaskDescription());
                vo.setPeopleName(dutyPeopleList.getPeopleName());
                vo.setPeoplePhone(dutyPeopleList.getPeoplePhone());
                VOList.add(vo);
            });

        });
        query.setRecords(VOList);
        return query;
    }

    /**
     * 新增值班信息管理表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 结果
     */
    @Override
    public int insertCompanyDuty(CompanyDutyDTO companyDuty, SecruityUser user) {
        companyDuty.setCreateTime(new Date());
        companyDuty.setDelFlag("1");
        companyDuty.setDutyStatus("1");
        companyDuty.setTaskFlag(companyDuty.getTaskBool() == true ? "1" : "0");
        if(companyDuty.getComId()==null){
            companyDuty.setComId(Integer.valueOf(SecurityUtils.getUserInfo().getCompanyId()));
        }
        companyDuty.setCreateBy(SecurityUtils.getUserInfo().getId());
        int i = companyDutyMapper.insertCompanyDuty(companyDuty);
        //2、保存值班人员信息关联表
        List<ZdutyidPeopleidDTO> people = companyDuty.getPeople();
        people.forEach(PeopleDTO -> {
            ZdutyidPeopleid zdutyidPeopleid = new ZdutyidPeopleid();
            BeanUtils.copyProperties(PeopleDTO,zdutyidPeopleid);
            zdutyidPeopleid.setDutyId(companyDuty.getId());
            zdutyidPeopleidMapper.insertZdutyidPeopleid(zdutyidPeopleid);
            //3、保存人员设备点检任务添加
            List<PointDutyJobDTO> task = PeopleDTO.getTask();
            task.forEach(taskDTO -> {
                PointDutyJob pointDutyJob = new PointDutyJob();
                BeanUtils.copyProperties(taskDTO,pointDutyJob);
                pointDutyJob.setAssignUserId(String.valueOf(user.getId()));
                pointDutyJob.setAssignUserName(user.getRealName());
                pointDutyJob.setAssignTime(new Date());
                pointDutyJob.setDutyId(String.valueOf(companyDuty.getId()));
                pointDutyJob.setPeopleId(PeopleDTO.getPeopleId());
                pointDutyJob.setEquipmentName(taskDTO.getEquipmentName());
                pointDutyJob.setEquipmentNum(taskDTO.getEquipmentNum());
                pointDutyJob.setDelFlag(CommonConstant.STATUS_NORMAL);
                pointDutyJobMapper.insertPointDutyJob(pointDutyJob);
                pointDutyJobMapper.insertZtDutyId_PeopleIdTask(companyDuty.getId(),pointDutyJob.getId(),zdutyidPeopleid.getDutyPeopleId());
            });
        });
        return i;
    }

    /**
     * 修改值班信息管理表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 结果
     */
    @Override
    public int updateCompanyDuty(CompanyDutyDTO companyDuty, SecruityUser user) {
        companyDuty.setUpdateTime(new Date());
        companyDuty.setUpdateBy(user.getId());
        companyDuty.setTaskFlag(companyDuty.getTaskBool() == true ? "1" : "0");
        int i = companyDutyMapper.updateCompanyDuty(companyDuty);
        pointDutyJobMapper.deleteZtDutyIdPeopleIdTaskByCompanyDutyId(companyDuty.getId());
        pointDutyJobMapper.deletePointDutyJobByDutyId(companyDuty.getId());
//        zdutyidPeopleidMapper.deleteZdutyidPeopleidById(1);
        //2、保存值班人员信息关联表
        List<ZdutyidPeopleidDTO> people = companyDuty.getPeople();
        people.forEach(PeopleDTO -> {
            ZdutyidPeopleid zdutyidPeopleid = new ZdutyidPeopleid();
            BeanUtils.copyProperties(PeopleDTO,zdutyidPeopleid);
            zdutyidPeopleidMapper.updateZdutyidPeopleid(zdutyidPeopleid);
            //3、保存人员设备点检任务添加
            List<PointDutyJobDTO> task = PeopleDTO.getTask();
            task.forEach(taskDTO -> {
                PointDutyJob pointDutyJob = new PointDutyJob();
                BeanUtils.copyProperties(taskDTO,pointDutyJob);
                pointDutyJob.setId(taskDTO.getDutyJobId());
                pointDutyJob.setAssignUserId(String.valueOf(user.getId()));
                pointDutyJob.setAssignUserName(user.getRealName());
                pointDutyJob.setAssignTime(new Date());
                pointDutyJob.setDutyId(String.valueOf(companyDuty.getId()));
                pointDutyJob.setPeopleId(PeopleDTO.getPeopleId());
                pointDutyJob.setEquipmentName(taskDTO.getEquipmentName());
                pointDutyJob.setEquipmentNum(taskDTO.getEquipmentNum());
                pointDutyJobMapper.insertPointDutyJob(pointDutyJob);
                pointDutyJobMapper.insertZtDutyId_PeopleIdTask(companyDuty.getId(),pointDutyJob.getId(),zdutyidPeopleid.getDutyPeopleId());
            });
        });
        return i;
    }


    /**
     * 删除值班信息管理表
     *
     * @param id 值班信息管理表ID
     * @return 结果
     */
    @Override
    public int deleteCompanyDutyById(Integer id) {
        return companyDutyMapper.deleteCompanyDutyById(id);
    }

    /**
     * 批量删除值班信息管理表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCompanyDutyByIds(String[] ids) {
        return companyDutyMapper.deleteCompanyDutyByIds(ids);
    }

}
