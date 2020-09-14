package com.ztman.admin.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysSystemIntegration;
import com.ztman.admin.api.vo.SysConsumerVO;
import com.ztman.admin.api.vo.SysSystemIntegrationVO;
import com.ztman.admin.service.StudentService;
import com.ztman.admin.service.SysSystemIntegrationService;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ztman.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 1、系统集成、2、物联网集成
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:12
 */
@RestController
@RequestMapping("/integration")
@Slf4j
public class SysSystemIntegrationController {
    @Autowired
    private SysSystemIntegrationService sysSystemIntegrationService;
    private final String SYSTEM_STATUS = "systemStatus";
    //excel工具类
    ExcelUtil util = new ExcelUtil();

    /**
    *  列表
    * @param params
    * @return
    */
    @PostMapping("/page")
    public Page page(@RequestBody Map<String, Object> params) {
        if (StringUtils.isEmpty(String.valueOf(params.get(SYSTEM_STATUS)))) {
            return new Page();
        }
        //数据库字段值完整查询
        // return  sysSystemIntegrationService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysSystemIntegrationService.selectSysSystemIntegrationListByLike(new Query<>(params));
    }


    /**
     * 信息
     * @param systemId
     * @return R
     */
    @GetMapping("/{systemId}")
    public R info(@PathVariable("systemId") Integer systemId) {
        SysSystemIntegration sysSystemIntegration =sysSystemIntegrationService.selectById(systemId);
        return new R<>(sysSystemIntegration);
    }

    /**
     * 保存
     * @param sysSystemIntegration
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysSystemIntegration sysSystemIntegration) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syssystemintegration/save", "POST", JSONUtil.toJsonStr(sysSystemIntegration), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        sysSystemIntegration.setCreateBy(user.getId());
        sysSystemIntegration.setSystemFlag("1");
        sysSystemIntegration.setDelFlag("1");
        sysSystemIntegrationService.insert(sysSystemIntegration);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     * @param sysSystemIntegration
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysSystemIntegration sysSystemIntegration) {
        SecruityUser user = SecurityUtils.getUserInfo();
        sysSystemIntegration.setUpdateBy(user.getId());
        sysSystemIntegration.setUpdateTime(new Date());
        Boolean boo = sysSystemIntegrationService.updateById(sysSystemIntegration);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syssystemintegration/update", "PUT", JSONUtil.toJsonStr(sysSystemIntegration), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        return new R<>(boo);
    }



    /**
     * 删除
     * @param systemId
     * @return R
     */
    @DeleteMapping("/{systemId}")
    public R delete(@PathVariable  Integer systemId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syssystemintegration/delete", "DELETE", String.valueOf(systemId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        sysSystemIntegrationService.deleteById(systemId);
        return new R<>(Boolean.TRUE);
    }



    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(@RequestParam Map<String, Object> params,HttpServletResponse res) throws IOException {
        List<SysSystemIntegrationVO> records = page(params).getRecords();
        String[] headInfo = {"序号", "系统名称", "系统地址", "接口数量", "管理单位", "对接人", "联系电话", "状态", "创建人", "创建时间", "是否启用"};
        String[] sheetName = {"系统集成"};
        util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
        //创建第一行标题头
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(headInfo);

        List<Integer[]> alist = new ArrayList<>();
        Integer[] a1 = {0, 0, 0, headInfo.length};//开始行,结束行,开始列,结束列
        alist.add(a1);
        List<Integer[]> blist = new ArrayList<>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
        for (int i = 0; i < records.size(); i ++) {
            SysSystemIntegrationVO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i+1);
            tb[index++] = obj.getSystemName();
            tb[index++] = obj.getSystemHttp();
            tb[index++] = String.valueOf(obj.getInterfaceCount());
            tb[index++] = obj.getSystemUnit();
            tb[index++] = obj.getSystemMeetPeople();
            tb[index++] = obj.getSystemMeetPeoplePhone();
            tb[index++] = obj.getSystemStatus();
            tb[index++] = obj.getCreateName();
            tb[index++] = sf.format(obj.getCreateTime());
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "系统集成");

    }

    /**
     * 上传文件
     *
     * @param file
     * @throws IOException
     */
    @PostMapping("/uploadExcel")
    public void uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream in = null;
        List<List<Object>> listob = null;
        if (file.isEmpty()) {
            throw new RuntimeException("文件不存在！");
        }
        in = file.getInputStream();

        //获取出来这个sheet页面的全部信息
        //sheet 0
        listob = new ExcelUtil().getBankListByExcel(in, file.getOriginalFilename(), 0, 3);
        in.close();

    }

    public static LocalDateTime LocalDateTime(Object obj) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sim.parse(obj.toString());
        Instant instant = parse.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
}
