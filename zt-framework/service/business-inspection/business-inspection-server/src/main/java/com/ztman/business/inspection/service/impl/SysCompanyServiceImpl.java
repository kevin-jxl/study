package com.ztman.business.inspection.service.impl;
 

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
 
import com.ztman.business.inspection.mapper.SysCompanyMapper;
import com.ztman.business.inspection.mapper.SysUserMapper;
import com.ztman.business.inspection.client.dto.CompanyDTO;
import com.ztman.business.inspection.client.entity.SysCompany;
import com.ztman.business.inspection.client.entity.SysUser;
import com.ztman.business.inspection.service.SysCompanyService;


@Service("sysCompanyService")
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements SysCompanyService {

    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
     

	@Override
	public List<CompanyDTO> withList(Integer userId) {
		// TODO Auto-generated method stub
		//获取与用户相关的租户下的企业列表
		List<CompanyDTO> companyDTOs = null;
		SysUser sysUser = sysUserMapper.selectSysUserById(userId);
		if(null != sysUser) {
			
		SysCompany sysCompany =	sysCompanyMapper.selectSysCompanyById(sysUser.getCompanyId());
			if (null != sysCompany) {
				companyDTOs = new ArrayList<CompanyDTO>();
				CompanyDTO companyDTO = new CompanyDTO();
				companyDTO.setComId(sysCompany.getComId());
				companyDTO.setName(sysCompany.getName());
				companyDTOs.add(companyDTO);
			}
			
		}
		return companyDTOs;
	}

}
