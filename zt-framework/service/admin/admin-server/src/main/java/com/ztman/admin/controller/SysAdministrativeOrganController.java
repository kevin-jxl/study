package com.ztman.admin.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import com.ztman.admin.api.vo.AdministrativeOrganVO;
import com.ztman.admin.service.StudentService;
import com.ztman.admin.service.SysAdministrativeOrganService;
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
 * 行政机构表
 *
 * @author jxl
 * @date 2020-08-21 15:37:10
 */
@RestController
@RequestMapping("/sysadministrativeorgan")
@Slf4j
public class SysAdministrativeOrganController {
    @Autowired
    private SysAdministrativeOrganService sysAdministrativeOrganService;
    @Autowired
    private StudentService studentService;
    //excel工具类
    ExcelUtil util = new ExcelUtil();

    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    public Page page(@RequestParam(required = false) Map<String, Object> params) {
        //数据库字段值完整查询
        SecruityUser user = SecurityUtils.getUserInfo();
        // return  sysAdministrativeOrganService.selectPage(new Query<>(params), new EntityWrapper<>());
        if(!SecurityUtils.checkRoles()){ // 租户对象模糊查询
            String companyIds = null;
            String comId = (String) params.get("comId");
            int id = user.getId();

            if (comId == null) {
                Integer userId = id;
                companyIds = sysAdministrativeOrganService.selectCompanyIdsByUserId(userId);
                params.put("companyId", companyIds);
            }

            return sysAdministrativeOrganService.selectSysAdministrativeOrganListByLikeAndTenant(new Query<>(params));
        }
        //超管对象模糊查询
        return sysAdministrativeOrganService.selectSysAdministrativeOrganListByLike(new Query<>(params));
    }

    @GetMapping("list")
    public List<HashMap> list(@RequestParam(required = false) Map<String, Object> params) {

        if (!SecurityUtils.checkRoles()) {
            String companyIds = null;
            Object id = params.get("userId");

            if (id != null) {
                Integer userId = Integer.valueOf((String) id);
                companyIds = sysAdministrativeOrganService.selectCompanyIdsByUserId(userId);
                params.put("companyId", companyIds);
            }
            params.put("role", "user");
        }

        List<HashMap> list = sysAdministrativeOrganService.list(new Query<>(params));
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
    public List<HashMap> inputList(@RequestParam(required = false) String comId) {

        if (null == comId && comId.equals("")) {
            return new ArrayList<>();
        }

        List<HashMap> list = sysAdministrativeOrganService.inputList(comId);

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
     * @param organId
     * @return R
     */
    @GetMapping("/{organId}")
    public R info(@PathVariable("organId") Integer organId) {
        AdministrativeOrganVO sysAdministrativeOrgan =sysAdministrativeOrganService.selectSysAdministrativeOrganById(organId);
        return new R<>(sysAdministrativeOrgan);
    }

    /**
     * 保存
     * @param sysAdministrativeOrgan
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_agency_add')")
    @SysLog("行政机构增加")
    @PostMapping
    public R save(@RequestBody SysAdministrativeOrgan sysAdministrativeOrgan) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysadministrativeorgan/save", "POST", JSONUtil.toJsonStr(sysAdministrativeOrgan), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == sysAdministrativeOrgan.getComId()) {
            r.setData(Boolean.FALSE);
            r.setMsg("没有选择机构所属企业,或当前用户没有所属企业");
            return r;
        }

        sysAdministrativeOrganService.insertSysAdministrativeOrgan(sysAdministrativeOrgan);
        return r;
    }

    /**
     * 修改
     * @param sysAdministrativeOrgan
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_agency_edit')")
    @SysLog("行政机构修改")
    @PutMapping
    public R update(@RequestBody SysAdministrativeOrgan sysAdministrativeOrgan) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysadministrativeorgan/update", "PUT", JSONUtil.toJsonStr(sysAdministrativeOrgan), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == sysAdministrativeOrgan.getComId()) {
            r.setData(Boolean.FALSE);
            r.setMsg("没有选择机构所属企业,或当前用户没有所属企业");
            return r;
        }

        sysAdministrativeOrganService.updateById(sysAdministrativeOrgan);
        return r;
    }



    /**
     * 删除
     * @param organId
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_agency_del')")
    @SysLog("行政机构删除")
    @DeleteMapping("/{organId}")
    public R delete(@PathVariable  Integer organId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysadministrativeorgan/delete", "DELETE", String.valueOf(organId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        try {
            sysAdministrativeOrganService.deleteSysAdministrativeOrganById(organId);
        } catch (Exception e) {
            r.setData(Boolean.FALSE);
            r.setMsg("该行政机构有关联子数据，暂时无法删除");
        }
        return r;
    }


    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {

        List<AdministrativeOrganVO> records = page(params).getRecords();
        String[] headInfo = {"序号", "机构名称", "机构编码", "负责人", "联系电话", "创建人", "创建时间"};
        String[] sheetName = {"行政机构表"};
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
            AdministrativeOrganVO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getName();
            tb[index++] = obj.getCode();
            tb[index++] = obj.getAdminname();
            tb[index++] = obj.getPhone();
            tb[index++] = obj.getCreateNmae();
            tb[index++] = obj.getCreateTime().toLocaleString();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "行政机构");
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
