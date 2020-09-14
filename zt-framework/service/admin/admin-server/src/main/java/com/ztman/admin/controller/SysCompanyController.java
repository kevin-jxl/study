package com.ztman.admin.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysCompany;
import com.ztman.admin.api.vo.SysCompanyVO;
import com.ztman.admin.service.SysCompanyService;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.annotation.SysLog;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


/**
 * 企业表
 *
 * @author jxl
 * @date 2020-08-21 10:04:46
 */
@RestController
@RequestMapping("/syscompany")
@Slf4j
public class SysCompanyController {
    @Autowired
    private SysCompanyService sysCompanyService;
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
        // return  sysCompanyService.selectPage(new Query<>(params), new EntityWrapper<>());
        if(!SecurityUtils.checkRoles() && params.get("tenantId") == null){ // 租户对象模糊查询
            return null;
        }
        // 超管对象模糊查询
        return sysCompanyService.selectSysCompanyListByLike(new Query<>(params));
    }

    @GetMapping
    public List<HashMap> list(@RequestParam(required = false) String tenantId) {

        if (!SecurityUtils.checkRoles() && (tenantId == null || tenantId.equals("")) ) {
            return new ArrayList<>();
        }

        List<HashMap> list = sysCompanyService.list(tenantId);

        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {

                return com.compare(o1.get("name"), o2.get("name"));
            }
        });

        return list;
    }

    @GetMapping("inputList")
    public List<HashMap> inputList(@RequestParam(required = false) String tenantId) {

        if (!SecurityUtils.checkRoles() && (tenantId == null || tenantId.equals("")) ) {
            return new ArrayList<>();
        }

        List<HashMap> list = sysCompanyService.inputList(tenantId);

        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {

                return com.compare(o1.get("name"), o2.get("name"));
            }
        });

        return list;
    }

    /**
     * 信息
     * @param comId
     * @return R
     */
    @GetMapping("/{comId}")
    public R info(@PathVariable("comId") Integer comId) {
        SysCompanyVO sysCompany = sysCompanyService.selectSysCompanyById(comId);
        return new R<>(sysCompany);
    }

    /**
     * 保存
     * @param sysCompany
     * @return R
     */
    @SysLog("企业增加")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_firm_add')")
    public R save(@RequestBody SysCompany sysCompany) {
        System.out.println(sysCompany.toString());
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syscompany/save", "POST", JSONUtil.toJsonStr(sysCompany), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == sysCompany.getTenantId() || sysCompany.getTenantId().trim().equals("")) {
            r.setMsg("该用户不是租户管理员，无法添加企业");
            r.setData(Boolean.FALSE);
            return r;
        }

        sysCompanyService.insertSysCompany(sysCompany);
        return r;
    }

    /**
     * 修改
     * @param sysCompany
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_firm_edit')")
    @SysLog("企业修改")
    @PutMapping
    public R update(@RequestBody SysCompany sysCompany) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syscompany/update", "PUT", JSONUtil.toJsonStr(sysCompany), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == sysCompany.getTenantId() || sysCompany.getTenantId().trim().equals("")) {
            r.setMsg("该用户不是租户管理员，无法修改企业");
            r.setData(Boolean.FALSE);
            return r;
        }

        sysCompanyService.updateById(sysCompany);
        return r;
    }

    /**
     * 修改企业状态
     * @param sysCompany
     * @return R
     */
    @SysLog("企业状态修改")
    @PutMapping("/status")
    public R updateStatus(@RequestBody SysCompany sysCompany) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syscompany/update", "PUT", JSONUtil.toJsonStr(sysCompany), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        sysCompanyService.updateStatus(sysCompany);
        return r;
    }

    /**
     * 删除
     * @param comId
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_firm_del')")
    @SysLog("企业删除")
    @DeleteMapping("/{comId}")
    public R delete(@PathVariable  Integer comId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syscompany/delete", "DELETE", String.valueOf(comId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        try {
            sysCompanyService.deleteSysCompanyById(comId);
        } catch (Exception e) {
            r.setData(Boolean.FALSE);
            r.setMsg("该企业有关联子数据，暂时无法删除");
        }
        return r;
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {
        List<SysCompanyVO> records = page(params).getRecords();
        records.forEach( obj -> {
            obj.setStatus(obj.getStatus().equals("1")  ? "正常" : "关闭");
        });
        String[] headInfo = {"序号", "企业名称", "企业编号", "所属租户", "负责人", "联系电话", "创建人", "创建时间", "服务状态"};
        String[] sheetName = {"企业管理"};
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
            SysCompanyVO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getName();
            tb[index++] = obj.getCode();
            tb[index++] = obj.getTenantName();
            tb[index++] = obj.getAdminName();
            tb[index++] = obj.getPhone();
            tb[index++] = obj.getCreateName();
            tb[index++] = obj.getCreateTime().toLocaleString();
            tb[index++] = obj.getStatus();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "企业管理");
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

        if (listob != null && listob.size() > 0) {
            for (List<Object> objects : listob) {
//                Student student = new Student();
//                student.setName(objects.get(0).toString());
//                student.setAge(Integer.valueOf(objects.get(1).toString()));
//                student.setAddress(objects.get(2).toString());
//                student.setCreatetime(LocalDateTime(objects.get(3)));
//                student.setUpdatetime(LocalDateTime(objects.get(4)));
//                studentService.insert(student);
            }
        }
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
