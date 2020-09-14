package com.ztman.admin.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysAdministrativeArea;
import com.ztman.admin.service.SysAdministrativeAreaService;
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
 * 行政区域表
 *
 * @author jxl
 * @date 2020-08-21 15:36:59
 */
@RestController
@RequestMapping("/sysadministrativearea")
@Slf4j
public class SysAdministrativeAreaController {
    @Autowired
    private SysAdministrativeAreaService sysAdministrativeAreaService;
//    @Autowired
//    private StudentService studentService;
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
        // return  sysAdministrativeAreaService.selectPage(new Query<>(params), new EntityWrapper<>());

        if (!SecurityUtils.checkRoles()) {
            return new Page();
        }

        // 对象模糊查询
        return sysAdministrativeAreaService.selectSysAdministrativeAreaListByLike(new Query<>(params));
    }

    @GetMapping
    public List<HashMap> inputList() {
        List<HashMap> list = sysAdministrativeAreaService.list();

        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {

                return com.compare(o1.get("area_name"), o2.get("area_name"));
            }
        });

        return list;
    }

    /**
     * 信息
     * @param areaId
     * @return R
     */
    @GetMapping("/{areaId}")
    public R info(@PathVariable("areaId") Integer areaId) {
        SysAdministrativeArea sysAdministrativeArea =sysAdministrativeAreaService.selectById(areaId);
        return new R<>(sysAdministrativeArea);
    }

    /**
     * 保存
     * @param sysAdministrativeArea
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_area_add')")
    @SysLog("行政区域增加")
    @PostMapping
    public R save(@RequestBody SysAdministrativeArea sysAdministrativeArea) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysadministrativearea/save", "POST", JSONUtil.toJsonStr(sysAdministrativeArea), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (!SecurityUtils.checkRoles()) {
            r.setData(Boolean.FALSE);
            r.setMsg("只有超级管理员拥有区域增加权限");
            return r;
        }

        sysAdministrativeAreaService.insertSysAdministrativeArea(sysAdministrativeArea);
        return r;
    }

    /**
     * 修改
     * @param sysAdministrativeArea
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_area_edit')")
    @SysLog("行政区域修改")
    @PutMapping
    public R update(@RequestBody SysAdministrativeArea sysAdministrativeArea) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysadministrativearea/update", "PUT", JSONUtil.toJsonStr(sysAdministrativeArea), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (!SecurityUtils.checkRoles()) {
            r.setData(Boolean.FALSE);
            r.setMsg("只有超级管理员拥有区域修改权限");
            return r;
        }

        sysAdministrativeAreaService.updateSysAdministrativeArea(sysAdministrativeArea);
        return r;
    }



    /**
     * 删除
     * @param areaId
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_area_del')")
    @SysLog("行政区域删除")
    @DeleteMapping("/{areaId}")
    public R delete(@PathVariable  Integer areaId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Object> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysadministrativearea/delete", "DELETE", String.valueOf(areaId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (!SecurityUtils.checkRoles()) {
            r.setData(Boolean.FALSE);
            r.setMsg("只有超级管理员拥有区域删除权限");
            return r;
        }

        try {
            sysAdministrativeAreaService.deleteSysAdministrativeAreaById(areaId);
        } catch (Exception e) {
            r.setData(Boolean.FALSE);
            r.setMsg("该区域有关联子数据，暂时无法删除");
        }
        return r;
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {

        List<SysAdministrativeArea> records = page(params).getRecords();
        String[] headInfo = {"序号", "行政区域名称", "行政区划代码", "负责人", "联系电话", "创建人", "创建时间"};
        String[] sheetName = {"行政区域表"};
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
            SysAdministrativeArea obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getAreaName();
            tb[index++] = obj.getCode();
            tb[index++] = obj.getAdminname();
            tb[index++] = obj.getPhone();
            tb[index++] = obj.getCreateName();
            tb[index++] = obj.getCreateTime().toLocaleString();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "行政区域");
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

        /*if (listob != null && listob.size() > 0) {
            for (List<Object> objects : listob) {
                Student student = new Student();
                student.setName(objects.get(0).toString());
                student.setAge(Integer.valueOf(objects.get(1).toString()));
                student.setAddress(objects.get(2).toString());
                student.setCreatetime(LocalDateTime(objects.get(3)));
                student.setUpdatetime(LocalDateTime(objects.get(4)));
                studentService.insert(student);
            }
        }*/
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
