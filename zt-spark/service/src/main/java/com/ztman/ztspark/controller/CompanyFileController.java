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
import com.ztman.ztspark.entity.company.CompanyFile;
import com.ztman.ztspark.service.CompanyFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * 文档管理表
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:56
 */
@RestController
@RequestMapping("/companyfile")
@Slf4j
public class CompanyFileController {
    @Autowired
    private CompanyFileService companyFileService;
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
        // return  companyFileService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return companyFileService.selectCompanyFileListByLike(new Query<>(params));
    }


    /**
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        CompanyFile companyFile =companyFileService.selectCompanyFileById(id);
        return new R<>(companyFile);
    }

    /**
     * 保存
     * @param companyFile
     * @return R
     */
    @PostMapping
    public R save(@RequestBody CompanyFile companyFile) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        String companyId = user.getCompanyId();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companyfile/save", "POST", JSONUtil.toJsonStr(companyFile), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        companyFileService.insertCompanyFile(companyFile);
        companyFileService.insertCompanyFileData(companyFile);
        return r;
    }

    /**
     * 修改
     * @param companyFile
     * @return R
     */
    @PutMapping
    public R update(@RequestBody CompanyFile companyFile) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companyfile/update", "PUT", JSONUtil.toJsonStr(companyFile), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        companyFileService.updateCompanyFile(companyFile);
        companyFileService.insertCompanyFileData(companyFile);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable  Integer id) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companyfile/delete", "DELETE", String.valueOf(id), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        companyFileService.deleteCompanyFileById(id);
        return new R<>(Boolean.TRUE);
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {
        List<CompanyFile> records = page(params).getRecords();
        String[] headInfo = {"序号", "文档名称", "文档编号", "签订方", "签订时间", "终止时间", "合作模式", "负责人", "负责人电话"};
        String[] sheetName = {"文档表"};
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
            CompanyFile obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getDocumentName();
            tb[index++] = obj.getCode();
            tb[index++] = obj.getSigningParty();
            tb[index++] = obj.getSigningTime().toLocaleString();
            tb[index++] = obj.getTerminationTime().toLocaleString();
            tb[index++] = obj.getCooperationMode().equals("1") ? "合作模式1" : "合作模式2";
            tb[index++] = obj.getLeading();
            tb[index++] = obj.getTel();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "文档管理");
    }

    /**
     * 上传文件
     *
     * @param file
     * @throws IOException
     */
    @PostMapping("/uploadExcel")
    public void uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {

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
