package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.CompanyDTO;
import com.ztman.business.inspection.client.entity.SysCompany;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 企业 服务接口层
 *
 * @author zxx
 * @date 2020-08-30 01:59:00
 */
public interface SysCompanyService extends IService<SysCompany> {
     

	public List<CompanyDTO> withList(Integer userId);

}

