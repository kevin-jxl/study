package com.ztman.ztspark.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.ztspark.dto.CompanyDutyDTO;
import com.ztman.ztspark.dto.PointDutyJobDTO;
import com.ztman.ztspark.dto.ZdutyidPeopleidDTO;
import com.ztman.ztspark.service.CompanyDutyService;
import com.ztman.ztspark.service.PointDutyJobService;
import com.ztman.ztspark.service.ZdutyidPeopleidService;
import com.ztman.ztspark.vo.CompanyDutyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
 * 值班信息管理表
 *
 * @author wsd
 * @date 2020-09-03 16:46:58
 */
@RestController
@RequestMapping("/companyduty")
@Slf4j
public class CompanyDutyController {
    @Autowired
    private CompanyDutyService companyDutyService;
    @Autowired
    private ZdutyidPeopleidService zdutyidPeopleidService;
    @Autowired
    private PointDutyJobService pointDutyJobService;
    //excel工具类
    ExcelUtil util = new ExcelUtil();

    /**
    *  值班信息列表
    * @param params
    * @return
    */
    @PostMapping("/page")
    public Page page(@RequestBody Map<String, Object> params) {
        //对象模糊查询
        Page page = companyDutyService.selectCompanyDutyListByLike(new Query<>(params));
        return page;
    }
    /**
     *  详情列表
     * @param params
     * @return
     */
    @PostMapping("/dutyPeople/list")
    public Page list(@RequestBody Map<String, Object> params) {
        //对象模糊查询
        Page page = companyDutyService.selectDutyPeopleListByLike(new Query<>(params));
        return page;
    }
    /**
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        CompanyDutyDTO companyDutyDTO =companyDutyService.selectCompanyDutyById(id);
        return new R<>(companyDutyDTO);
    }
    /**
     * 保存
     * @param companyDuty
     * @return R
     */
    @PostMapping
    public R save(@RequestBody CompanyDutyDTO companyDuty) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companyduty/save", "POST", JSONUtil.toJsonStr(companyDuty), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        //1、保存值班信息
        int i = companyDutyService.insertCompanyDuty(companyDuty,user);

        return new R<>(i > 0);
    }
    /**
     * 修改
     * @param companyDuty
     * @return R
     */
    @PutMapping
    public R update(@RequestBody CompanyDutyDTO companyDuty) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companyduty/update", "PUT", JSONUtil.toJsonStr(companyDuty), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        //1、修改值班信息
        int i = companyDutyService.updateCompanyDuty(companyDuty,user);
        return new R<>(i > 0);
    }



    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable  Integer id) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companyduty/delete", "DELETE", String.valueOf(id), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        CompanyDutyDTO companyDuty = companyDutyService.selectCompanyDutyById(id);
        //1、删除值班信息
        int i = companyDutyService.deleteCompanyDutyById(companyDuty.getId());
        //2、保存值班人员信息关联表
        List<ZdutyidPeopleidDTO> people = companyDuty.getPeople();
        people.forEach(PeopleDTO -> {
            zdutyidPeopleidService.deleteZdutyidPeopleidById(PeopleDTO.getDutyId());
            //3、保存人员设备点检任务添加
            List<PointDutyJobDTO> task = PeopleDTO.getTask();
            task.forEach(taskDTO -> {
                pointDutyJobService.deletePointDutyJobById(String.valueOf(taskDTO.getDutyJobId()));
            });
        });
        return new R<>(Boolean.TRUE);
    }


    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {
            List<CompanyDutyVO> records = page(params).getRecords();
            String[] headInfo = {"序号", "值班人员",  "负责车场","开始时间", "结束时间", "值班状态", "排班人", "排班电话"};
            String[] sheetName = {"人员表"};
            util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
            //创建第一行标题头
            List<String[]> list2 = new ArrayList<String[]>();
            list2.add(headInfo);

            List<Integer[]> alist = new ArrayList<>();
            Integer[] a1 = {0, 0, 0, headInfo.length};//开始行,结束行,开始列,结束列
            alist.add(a1);
            List<Integer[]> blist = new ArrayList<>();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
            for (int i = 0; i < records.size(); i ++) {
                CompanyDutyVO obj = records.get(i);
                List<String[]> cacheList = new ArrayList<String[]>();
                String[] tb = new String[headInfo.length];
                int index = 0;
                tb[index++] = String.valueOf(i + 1);
                tb[index++] = obj.getDutyName();
                tb[index++] = obj.getOrganName();
                tb[index++] = sf.format(obj.getStartTime());
                tb[index++] = sf.format(obj.getEndTime());
                tb[index++] = obj.getDutyStatus();
                tb[index++] = obj.getCreateName();
                tb[index++] = obj.getCreatePhone();
                cacheList.add(tb);
                util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
            }
            util.exportExcel(res, "值班信息");
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
