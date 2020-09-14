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
import com.ztman.ztspark.entity.company.CompanyWork;
import com.ztman.ztspark.service.CompanyWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * 考勤管理表
 *
 * @author spark code generator
 * @date 2020-09-03 19:07:55
 */
@RestController
@RequestMapping("/companywork")
@Slf4j
public class CompanyWorkController {
    @Autowired
    private CompanyWorkService companyWorkService;
    //excel工具类
    ExcelUtil util = new ExcelUtil();

    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
//         return  companyWorkService.selectPage(new Query<>(params), new EntityWrapper<>());

        //对象模糊查询
        return companyWorkService.selectCompanyWorkListByLike(new Query<>(params));
    }

    /**
     * 信息
     * @param workId
     * @return R
     */
    @GetMapping("/{workId}")
    public R info(@PathVariable("workId") Integer workId) {
        CompanyWork companyWork =companyWorkService.selectCompanyWorkById(workId);
        return new R<>(companyWork);
    }

    /**
     * 保存
     * @param companyWork
     * @return R
     */
    @PostMapping
    public R save(@RequestBody CompanyWork companyWork) {
        SecruityUser user = SecurityUtils.getUserInfo();
        String companyId = user.getCompanyId();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companywork/save", "POST", JSONUtil.toJsonStr(companyWork), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null != companyId) {
            companyWork.setComId(Integer.valueOf(companyId));
        }
        companyWorkService.insertCompanyWork(companyWork);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     * @param companyWork
     * @return R
     */
    @PutMapping
    public R update(@RequestBody CompanyWork companyWork) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companywork/update", "PUT", JSONUtil.toJsonStr(companyWork), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        companyWorkService.updateCompanyWork(companyWork);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     * @param workId
     * @return R
     */
    @DeleteMapping("/{workId}")
    public R delete(@PathVariable  Integer workId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companywork/delete", "DELETE", String.valueOf(workId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        companyWorkService.deleteCompanyWorkById(workId);
        return new R<>(Boolean.TRUE);
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {
        List<CompanyWork> records = page(params).getRecords();
        String[] headInfo = {"序号", "时间", "姓名", "部门", "职位", "出勤天数", "异常天数", "请假", "加班", "调休", "外出"};
        String[] sheetName = {"人员表"};
        util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
        //创建第一行标题头
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(headInfo);

        List<Integer[]> alist = new ArrayList<>();
        Integer[] a1 = {0, 0, 0, headInfo.length};//开始行,结束行,开始列,结束列
        alist.add(a1);
        List<Integer[]> blist = new ArrayList<>();

        util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
        for (int i = 0; i < records.size(); i ++) {
            CompanyWork obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getWorkStartTime().toLocaleString() + "~" + obj.getWorkEndTime().toLocaleString();
            tb[index++] = obj.getWorkName();
            tb[index++] = obj.getWorkPost();
            tb[index++] = obj.getWorkDepartment();
            tb[index++] = obj.getWorkAttendance() == null ? "0" : obj.getWorkAttendance() + "";
            tb[index++] = obj.getWorkAbnormal() == null ? "0" : obj.getWorkAbnormal() + "";
            tb[index++] = obj.getWorkLeave() == null ? "0" : obj.getWorkLeave() + "";
            tb[index++] = obj.getWorkOvertime() == null ? "0" : obj.getWorkOvertime() + "";
            tb[index++] = obj.getWorkOff() == null ? "0" : obj.getWorkOff() + "";
            tb[index++] = obj.getWorkOut() == null ? "0" : obj.getWorkOut() + "";
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "人员管理");
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
