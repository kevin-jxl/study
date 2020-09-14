package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.dto.SysTenantDTO;
import com.ztman.admin.api.entity.SysTenant;
import com.ztman.admin.api.vo.OrganTenantVO;
import com.ztman.admin.api.vo.OrganVO;
import com.ztman.admin.api.vo.ServiceVO;
import com.ztman.admin.mapper.SysTenantMapper;
import com.ztman.admin.service.SysTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service("sysTenantService")
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Autowired
    private SysTenantMapper sysTenantMapper;

    /**
     * 查询信息
     *
     * @param tenantId ID
     * @return 信息
     */
    @Override
    public SysTenantDTO selectSysTenantById(Integer tenantId)
    {
        return sysTenantMapper.selectSysTenantById(tenantId);
    }

    /**
     * 查询列表
     *
     * @param sysTenant 信息
     * @return 集合
     */
    @Override
    public List<SysTenant> selectSysTenantList(SysTenant sysTenant)
    {
        return sysTenantMapper.selectSysTenantList(sysTenant);
    }


    /**
     * 分页模糊查询列表
     * @return 集合
     */
    @Override
    public Page selectSysTenantListByLike(Query query)
    {
        SysTenantDTO sysTenant =  BeanUtil.mapToBean(query.getCondition(), SysTenantDTO.class,false);
        query.setRecords(sysTenantMapper.selectSysTenantListByLike(query,sysTenant));
        return query;
    }

    /**
     * 新增
     *
     * @param sysTenant 信息
     * @return 结果
     */
    @Override
    public int insertSysTenant(SysTenant sysTenant)
    {
        return sysTenantMapper.insertSysTenant(sysTenant);
    }

    /**
     * 修改
     *
     * @param sysTenant 信息
     * @return 结果
     */
    @Override
    public int updateSysTenant(SysTenant sysTenant)
    {
        return sysTenantMapper.updateSysTenant(sysTenant);
    }


    /**
     * 删除
     *
     * @param tenantId ID
     * @return 结果
     */
    @Override
    public int deleteSysTenantById(Integer tenantId)
    {
        return sysTenantMapper.deleteSysTenantById( tenantId);
    };

    /**
     * 查询租户信息
     *
     * @param
     * @return 结果
     */
    @Override
    public SysTenant findSysTenantByempno(SysTenant sysTenant) {
        return sysTenantMapper.findSysTenantByempno(sysTenant);
    }

    /**
     * 批量删除对象
     *
     * @param tenantIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysTenantByIds(String[] tenantIds)
    {
        return sysTenantMapper.deleteSysTenantByIds( tenantIds);
    }

    @Override
    public  List<OrganVO> selectOrgan(Integer tenantId) {
        List<OrganTenantVO> data = sysTenantMapper.selectOrgan(tenantId);
        Map<Integer, OrganVO> map = new LinkedHashMap<>();
        for (OrganTenantVO vo : data) {
            if (vo != null && map.get((vo.getOrganId() == null ? "" : vo.getOrganId())) != null) {
                ServiceVO add = new ServiceVO();
                add.setId(vo.getServiceId());
                add.setName(vo.getServiceName());
                add.setLabel(vo.getServiceName());
                add.setType("service");
                map.get(vo.getOrganId()).getChildren().add(add);
                continue;
            }
            OrganVO organ = new OrganVO();
            organ.setId(vo.getOrganId());
            organ.setName(vo.getOrganName());
            organ.setLabel(vo.getOrganName());
            organ.setType("organ");
            if(vo.getServiceId()!=null){
                ServiceVO serviceVO = new ServiceVO();
                serviceVO.setId(vo.getServiceId());
                serviceVO.setName(vo.getServiceName());
                serviceVO.setLabel(vo.getServiceName());
                serviceVO.setType("service");
                organ.getChildren().add(serviceVO);
            }
            map.put(vo.getOrganId(), organ);

        }
        List<OrganVO> result = new ArrayList<>();
        map.forEach((integer, organVO) -> {
            result.add(organVO);
        });
        return result;
    }
}
