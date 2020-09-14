package com.ztman.business.inspection.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.business.inspection.mapper.CasualPoininspectionMapper;
import com.ztman.business.inspection.client.dto.CasualPoininspectionDTO; 
import com.ztman.business.inspection.client.entity.CasualPoininspection;
import com.ztman.business.inspection.service.CasualPoininspectionService;


@Service("casualPoininspectionService")
public class CasualPoininspectionServiceImpl extends ServiceImpl<CasualPoininspectionMapper, CasualPoininspection> implements CasualPoininspectionService {

    @Autowired
    private CasualPoininspectionMapper casualPoininspectionMapper;

    /**
     * 查询随机点检信息
     *
     * @param id 随机点检ID
     * @return 随机点检信息
     */
    @Override
    public CasualPoininspection selectCasualPoininspectionById(Integer id)
    {
        return casualPoininspectionMapper.selectCasualPoininspectionById(id);
    }

    /**
     * 查询随机点检列表
     *
     * @param casualPoininspection 随机点检信息
     * @return 随机点检集合
     */
    @Override
    public List<CasualPoininspection> selectCasualPoininspectionList(CasualPoininspection casualPoininspection)
    {
        return casualPoininspectionMapper.selectCasualPoininspectionList(casualPoininspection);
    }


    /**
     * 分页模糊查询随机点检列表
     * @return 随机点检集合
     */
    @Override
    public Page selectCasualPoininspectionListByLike(Query query)
    {
        CasualPoininspection casualPoininspection =  BeanUtil.mapToBean(query.getCondition(), CasualPoininspection.class,false);
        query.setRecords(casualPoininspectionMapper.selectCasualPoininspectionListByLike(query,casualPoininspection));
        return query;
    }

    /**
     * 新增随机点检
     *
     * @param casualPoininspection 随机点检信息
     * @return 结果
     */
    @Override
    public int insertCasualPoininspection(CasualPoininspection casualPoininspection)
    {
        return casualPoininspectionMapper.insertCasualPoininspection(casualPoininspection);
    }

    /**
     * 修改随机点检
     *
     * @param casualPoininspection 随机点检信息
     * @return 结果
     */
    @Override
    public int updateCasualPoininspection(CasualPoininspection casualPoininspection)
    {
        return casualPoininspectionMapper.updateCasualPoininspection(casualPoininspection);
    }


    /**
     * 删除随机点检
     *
     * @param id 随机点检ID
     * @return 结果
     */
    public int deleteCasualPoininspectionById(Integer id)
    {
        return casualPoininspectionMapper.deleteCasualPoininspectionById( id);
    };


    /**
     * 批量删除随机点检对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCasualPoininspectionByIds(String[] ids)
    {
        return casualPoininspectionMapper.deleteCasualPoininspectionByIds( ids);
    }

	@Override
	public boolean addBy( CasualPoininspectionDTO casualPoininspectionDTO) {
		// TODO Auto-generated method stub
		LocalDateTime now = LocalDateTime.now();
		String id = now.getYear()+""+now.getMonthValue()+""+now.getDayOfMonth()+""+now.getHour()+""+now.getMinute()+""+now.getSecond()+""+((int)(new Random().nextDouble()*1000));
		System.out.print("id::"+id);
		CasualPoininspection casualPoininspection = new CasualPoininspection();
		casualPoininspection.setTenantId(((int)(new Random().nextDouble()*1000))+"");
		casualPoininspection.setStatus(casualPoininspectionDTO.getStatus());
		casualPoininspection.setId(id);
		casualPoininspection.setGroupId(casualPoininspectionDTO.getGroupId());
		casualPoininspection.setEquipmentName(casualPoininspectionDTO.getEquipmentName());
		casualPoininspection.setEquipmentId(casualPoininspectionDTO.getEquipmentId());
		casualPoininspection.setDoUserName(casualPoininspectionDTO.getDoUserName());
		casualPoininspection.setDoUserId(casualPoininspectionDTO.getDoUserId());
		casualPoininspection.setCreateTime(LocalDateTime.now());
		casualPoininspection.setAddress(casualPoininspectionDTO.getAddress());
		return casualPoininspectionMapper.insertCasualPoininspection(casualPoininspection)>0;
	}
 

}
