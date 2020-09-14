package com.ztman.admin.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.Student;
import com.ztman.admin.api.entity.SysServiceOrgan;
import com.ztman.admin.api.vo.SysServiceOrganVO;
import com.ztman.admin.service.StudentService;
import com.ztman.admin.service.SysServiceOrganService;
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
 * 业务机构表
 *
 * @author spark code generator
 * @date 2020-08-25 19:44:47
 */
@RestController
@RequestMapping("/sysserviceorgan")
@Slf4j
public class SysServiceOrganController {
    @Autowired
    private SysServiceOrganService sysServiceOrganService;
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
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysServiceOrganService.selectPage(new Query<>(params), new EntityWrapper<>());
        SecruityUser user = SecurityUtils.getUserInfo();
        if(!SecurityUtils.checkRoles()){ // 租户对象模糊查询
            String organIds = null;

            String aoId = (String) params.get("administrativeOrganId");
            int id = user.getId();

            if (aoId == null) {
                Integer userId = Integer.valueOf(id);
                organIds = sysServiceOrganService.selectOrganIdsByUserId(userId);
                params.put("organId", organIds);
            }

            return sysServiceOrganService.selectSysServiceOrganListByLikeOrgan(new Query<>(params));
        }
        //对象模糊查询
        return sysServiceOrganService.selectSysServiceOrganListByLike(new Query<>(params));
    }

    @GetMapping("/list")
    public List<HashMap<String, Object>> inputList(@RequestParam Map<String, Object> params) {

        if (!SecurityUtils.checkRoles()) {
            String organIds = null;

            Object id = params.get("userId");

            if (id == null) {
                return null;
            }
            Integer userId = Integer.valueOf((String) id);
            organIds = sysServiceOrganService.selectOrganIdsByUserId(userId);
            params.put("organId", organIds);
            params.put("role", "user");
        }

        List<HashMap<String, Object>> list = sysServiceOrganService.inputList(new Query<>(params));

        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {

                return com.compare(o1.get("service_name"), o2.get("service_name"));
            }
        });

        return list;
    }

    @GetMapping("inputList")
    public List<HashMap<String, Object>> list(@RequestParam(required = false) String organId) {

        if (null != organId && organId.equals("")) {
            return new ArrayList<>();
        }

        List<HashMap<String, Object>> list = sysServiceOrganService.list(organId);

        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {

                return com.compare(o1.get("service_name"), o2.get("service_name"));
            }
        });


        return list;
    }


    /**
     * 信息
     * @param serviceId
     * @return R
     */
    @GetMapping("/{serviceId}")
    public R info(@PathVariable("serviceId") Integer serviceId) {
        SysServiceOrganVO sysServiceOrgan =sysServiceOrganService.selectSysServiceOrganById(serviceId);
        return new R<>(sysServiceOrgan);
    }

    /**
     * 保存
     * @param sysServiceOrgan
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_mechanism_add')")
    @SysLog("业务机构增加")
    @PostMapping
    public R save(@RequestBody SysServiceOrgan sysServiceOrgan) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysserviceorgan/save", "POST", JSONUtil.toJsonStr(sysServiceOrgan), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == sysServiceOrgan.getAdministrativeOrganId()) {
            r.setMsg("没有选择所属车场，无法增加");
            r.setData(Boolean.FALSE);
            return r;
        }

        sysServiceOrganService.insertSysServiceOrgan(sysServiceOrgan);
        return r;
    }

    /**
     * 修改
     * @param sysServiceOrgan
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_mechanism_edit')")
    @SysLog("业务机构修改")
    @PutMapping
    public R update(@RequestBody SysServiceOrgan sysServiceOrgan) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysserviceorgan/update", "PUT", JSONUtil.toJsonStr(sysServiceOrgan), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == sysServiceOrgan.getAdministrativeOrganId()) {
            r.setMsg("没有选择所属车场，无法修改");
            r.setData(Boolean.FALSE);
            return r;
        }

        sysServiceOrganService.updateSysServiceOrgan(sysServiceOrgan);
        return r;
    }



    /**
     * 删除
     * @param serviceId
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_mechanism_del')")
    @SysLog("业务机构删除")
    @DeleteMapping("/{serviceId}")
    public R delete(@PathVariable  Integer serviceId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/sysserviceorgan/delete", "DELETE", String.valueOf(serviceId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        try {
            sysServiceOrganService.deleteSysServiceOrganById(serviceId);
        } catch (Exception e) {
            r.setData(Boolean.FALSE);
            r.setMsg("该业务机构有关联子数据，暂时无法删除");
        }
        return r;
    }



    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {

        List<SysServiceOrganVO> records = page(params).getRecords();
        String[] headInfo = {"序号", "业务机构名称", "业务机构编码", "所属车场", "负责人", "联系电话", "创建人", "创建时间"};
        String[] sheetName = {"业务机构"};
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
            SysServiceOrganVO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getServiceName();
            tb[index++] = obj.getCode();
            tb[index++] = obj.getOrganName();
            tb[index++] = obj.getAdminName();
            tb[index++] = obj.getPhone();
            tb[index++] = obj.getCreateName();
            tb[index++] = obj.getCreateTime().toLocaleString();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "业务机构");
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
                Student student = new Student();
                student.setName(objects.get(0).toString());
                student.setAge(Integer.valueOf(objects.get(1).toString()));
                student.setAddress(objects.get(2).toString());
                student.setCreatetime(LocalDateTime(objects.get(3)));
                student.setUpdatetime(LocalDateTime(objects.get(4)));
                studentService.insert(student);
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
