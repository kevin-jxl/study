package com.ztman.admin.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ztman.admin.api.entity.SysSystemIntegration;
import com.ztman.admin.api.entity.SysSystemInterface;
import com.ztman.admin.api.vo.SysSystemIntegrationVO;
import com.ztman.admin.service.SysSystemIntegrationService;
import com.ztman.admin.service.SysSystemInterfaceService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


import javax.annotation.Resource;

/**
 * 定时任务
 */

@Component
@EnableScheduling
public class TriggerClass {
    @Resource(name="sysSystemInterfaceService")
    private  SysSystemInterfaceService interfaceService;
    @Resource(name="sysSystemIntegrationService")
    private SysSystemIntegrationService systemService;

    /**
     * 检测接口集成中异常接口状态，接口管理中为异常，系统状态改为异常
     */
//    @Scheduled(cron = "0/60 * * * * ?")
    public void InterfaceStatusJob() {
        SysSystemIntegration integration = new SysSystemIntegration();
        integration.setDelFlag("1");
        integration.setSystemFlag("1");
        List<SysSystemIntegrationVO> integrations = systemService.selectSysSystemIntegrationListJob(integration);
        for (int i = 0; i < integrations.size(); i++) {
            System.out.println("******************自定定时任务*"+integrations.get(i));
            SysSystemInterface sysSystemInterface = new SysSystemInterface();
            sysSystemInterface.setDelFlag("1");
            sysSystemInterface.setInterfaceFlag("1");
            sysSystemInterface.setSysId(integrations.get(i).getSystemId());
            List<SysSystemInterface> interfaceList = interfaceService.selectList(new EntityWrapper<>(sysSystemInterface));
            for (int j = 0; j < interfaceList.size(); j++) {
                if ("0".equals(interfaceList.get(j).getStatus())) {
                    SysSystemIntegration updateSystem = new SysSystemIntegration();
                    updateSystem.setSystemId(interfaceList.get(i).getSysId());
                    updateSystem.setSystemFlag("0");
                    systemService.updateById(updateSystem);
                }
            }
        }

    }

}
