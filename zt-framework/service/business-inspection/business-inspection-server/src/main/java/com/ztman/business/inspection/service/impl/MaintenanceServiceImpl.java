package com.ztman.business.inspection.service.impl;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
 
import com.ztman.business.inspection.mapper.MaintenanceMapper;
import com.ztman.business.inspection.client.dto.MaintenanceDTO;
import com.ztman.business.inspection.client.entity.Maintenance;
import com.ztman.business.inspection.service.MaintenanceService;


@Service("maintenanceService")
public class MaintenanceServiceImpl extends ServiceImpl<MaintenanceMapper, Maintenance> implements MaintenanceService {

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    

	@Override
	public MaintenanceDTO info(String workOrderId) {
		// TODO Auto-generated method stub
		MaintenanceDTO maintenanceDTO = null;
		
		Maintenance maintenance = new Maintenance();
		maintenance.setWorkOrderId(workOrderId);
		maintenance = maintenanceMapper.selectOne(maintenance);
		
		if( null != maintenance ) {
			maintenanceDTO = new MaintenanceDTO();
			maintenanceDTO.setAttachmentGroupId(maintenance.getAttachmentGroupId());
			maintenanceDTO.setCompleteDatetime(maintenance.getCompleteDatetime());
			maintenanceDTO.setDescription(maintenance.getDescription());
			maintenanceDTO.setPart(maintenance.getPart());
			maintenanceDTO.setWorkOrderId(workOrderId);
		}
		
		return maintenanceDTO;
	}

}
