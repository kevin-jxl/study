package com.ztman.business.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.business.inspection.mapper.SysDeptMapper;
import com.ztman.business.inspection.mapper.SysUserMapper;
import com.ztman.business.inspection.client.dto.CompanyDTO;
import com.ztman.business.inspection.client.dto.SysDeptDTO;
import com.ztman.business.inspection.client.entity.SysCompany;
import com.ztman.business.inspection.client.entity.SysDept;
import com.ztman.business.inspection.client.entity.SysUser;
import com.ztman.business.inspection.service.SysDeptService;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    
     

	@Override
	public List<SysDeptDTO> withList(Integer userId) {
		// TODO Auto-generated method stub
		//获取与用户相关的租户下的企业列表
				List<SysDeptDTO> sysDeptDTOs = null;
//				SysUser sysUser = sysUserMapper.selectSysUserById(userId);
//				if(null != sysUser) {
//				SysDept sysDept =	new SysDept();
//				sysDept.set
//				sysDeptMapper.selectSysDeptList(sysDept);
//					CompanyDTO companyDTO = new CompanyDTO();
//					companyDTO.setComId(sysCompany.getComId());
//					companyDTO.setName(sysCompany.getName());
//					companyDTOs.add(companyDTO);
//				}
				return sysDeptDTOs;
	}

}
