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
import com.ztman.ztspark.entity.company.CompanyPeople;
import com.ztman.ztspark.service.CompanyPeopleService;
import com.ztman.ztspark.service.CompanyWorkService;
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
 * 人员管理表
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:54
 */
@RestController
@RequestMapping("/companypeople")
@Slf4j
public class CompanyPeopleController {
    @Autowired
    private CompanyPeopleService companyPeopleService;

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
        SecruityUser user = SecurityUtils.getUserInfo();

        if (!SecurityUtils.checkRoles()) { // 判断是否是管理员
            String tenantFlag = user.getTenantFlag(); // 租户标识
            int userId = user.getId(); // 用户id
            String companyId = user.getCompanyId(); // 用户企业id

            if (null != tenantFlag && tenantFlag.equals("1")) { // 如果当前登录人是租户， 查询所管理所有企业的人员
                String companyIds = companyPeopleService.selectCompanyIdsByUserId(userId);
                if (companyIds == null || companyIds.equals("")) {
                    return new Page();
                }
                params.put("companyId", companyIds);
                return companyPeopleService.selectCompanyPeopleListByLike(new Query<>(params));
            }

            if (companyId != null && !companyId.equals("")) { // 如果当前登录用户，不是管理员，且企业id不为空，查询当前公司的所有人员
                params.put("comId", companyId);
                return companyPeopleService.selectCompanyPeopleListByComId(new Query<>(params));
            }

        }
        //对象模糊查询
        return companyPeopleService.selectCompanyPeopleListByLike(new Query<>(params));
    }

    @GetMapping("inputList")
    public List<CompanyPeople> inputList(@RequestParam Map<String, Object> params) {

        if (!SecurityUtils.checkRoles()) { // 判断是否是管理员
            SecruityUser user = SecurityUtils.getUserInfo();
            String tenantFlag = user.getTenantFlag(); // 租户标识
            int userId = user.getId(); // 用户id
            String companyId = user.getCompanyId(); // 用户企业id

            if (null != tenantFlag && tenantFlag.equals("1")) { // 如果当前登录人是租户， 查询所管理所有企业的人员
                String companyIds = companyPeopleService.selectCompanyIdsByUserId(userId);
                if (companyIds == null || companyIds.equals("")) {
                    return new ArrayList<>();
                }
                params.put("companyId", companyIds);
            }

            if (companyId != null && !companyId.equals("")) { // 如果当前登录用户，不是管理员，且企业id不为空，查询当前公司的所有人员
                params.put("comId", companyId);
            }

        }

        return companyPeopleService.inputList(new Query<>(params));
    }

    /**
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        CompanyPeople companyPeople =companyPeopleService.selectCompanyPeopleById(id);
        return new R<>(companyPeople);
    }

    /**
     * 保存
     * @param companyPeople
     * @return R
     */
    @PostMapping
    public R save(@RequestBody CompanyPeople companyPeople) {
        SecruityUser user = SecurityUtils.getUserInfo();
        R<Boolean> r = new R<>(Boolean.TRUE);
        String companyId = user.getCompanyId();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companypeople/save", "POST", JSONUtil.toJsonStr(companyPeople), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        if (null == companyId && !SecurityUtils.checkRoles()) {
            r.setMsg("当前登录人没有所属公司无法添加人员");
            r.setData(Boolean.FALSE);
            return r;
        }
        if (null != companyId && !companyId.equals("null")) {
            System.out.println(companyId);
            companyPeople.setComId(Integer.valueOf(companyId));
        }

        CompanyPeople cp = companyPeopleService.selectCompanyPeopleByTel(companyPeople);

        if (cp != null) {
            r.setData(Boolean.FALSE);
            r.setMsg("手机号已存在，请重新输入");
            return r;
        }

        companyPeopleService.insertCompanyPeople(companyPeople);
        companyWorkService.addCompanyWorkByPeople(companyPeople);
        return r;
    }

    /**
     * 修改
     * @param companyPeople
     * @return R
     */
    @PutMapping
    public R update(@RequestBody CompanyPeople companyPeople) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companypeople/update", "PUT", JSONUtil.toJsonStr(companyPeople), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");

        companyPeople.setUpdateBy(user.getId());
        companyPeopleService.updateCompanyPeople(companyPeople);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable  Integer id) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/companypeople/delete", "DELETE", String.valueOf(id), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        companyPeopleService.deleteCompanyPeopleById(id);
        companyWorkService.deleteCompanyWorkByPeopleId(id);
        return new R<>(Boolean.TRUE);
    }



    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {
        List<CompanyPeople> records = page(params).getRecords();
        String[] headInfo = {"序号", "用户名", "姓名", "联系方式", "所属车场", "所属业务机构", "职位", "创建人", "创建时间"};
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
            CompanyPeople obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getUserName();
            tb[index++] = obj.getName();
            tb[index++] = obj.getTel();
            tb[index++] = obj.getOrganName();
            tb[index++] = obj.getComName();
            tb[index++] = obj.getDepartment() + "";
            tb[index++] = obj.getCreateName();
            tb[index++] = obj.getCreateTime().toLocaleString();
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
