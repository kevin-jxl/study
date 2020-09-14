package com.ztman.admin.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.SysTenantDTO;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.api.entity.SysTenant;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.entity.SysUserRole;
import com.ztman.admin.api.vo.OrganVO;
import com.ztman.admin.api.vo.RoleVO;
import com.ztman.admin.api.vo.SysConsumerVO;
import com.ztman.admin.api.vo.SysTenantVo;
import com.ztman.admin.service.SysRoleService;
import com.ztman.admin.service.SysTenantService;
import com.ztman.admin.service.SysUserRoleService;
import com.ztman.admin.service.SysUserService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.annotation.SysLog;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


/**
 * 
 *
 * @author ztman code generator
 * @date 2020-08-26 10:02:33
 */
@RestController
@RequestMapping("/tenant")
@Slf4j
public class SysTenantController {
    @Autowired
    private SysTenantService sysTenantService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserService sysUserService;
    //excel工具类
    ExcelUtil util = new ExcelUtil();

    @Autowired
    private RedisTemplate redisTemplate;
    /**
    *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('sys_tenant_find')")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        Query query = new Query<>(params);
        if(!SecurityUtils.getRoles().contains(CommonConstant.ROLE_ADMIN)){
            return new Page();
        }
        return  sysTenantService.selectSysTenantListByLike(query);
        //对象模糊查询
//        return sysTenantService.selectSysTenantListByLike(new Query<>(params));
    }


    /**
     * 信息
     * @param tenantId
     * @return R
     */
    @GetMapping("/{tenantId}")
    public R info(@PathVariable("tenantId") Integer tenantId) {
        SysTenantVo sysTenantVo = new SysTenantVo();
        SysTenantDTO sysTenant =sysTenantService.selectSysTenantById(tenantId);
        if(sysTenant.getAdmin()!=null){
            List<RoleVO> roleVOS = sysUserService.selectUserRoleByUserId(sysTenant.getAdmin());
            if(roleVOS.size() > 0 && roleVOS != null) {
                sysTenantVo.setRoleVO(roleVOS.get(0));
            }
        }
        sysTenantVo.setSysTenant(sysTenant);
        return new R<>(sysTenantVo);
    }

    /**
     * 保存
     * @param sysTenant
     * @return R
     */
    @SysLog("租户新增")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_tenant_add')")
    public R save(@RequestBody SysTenant sysTenant) {
        if(StringUtils.isNotEmpty(sysTenant.getTenantCode())){
            SysTenant tenant = sysTenantService.findSysTenantByempno(sysTenant);
            if (tenant != null && !"".equals(tenant.getTenantCode())) {
                return new R<>(Boolean.FALSE,"租户编码重复，无法添加");
            }
        }
        SecruityUser user = SecurityUtils.getUserInfo();
        sysTenant.setCreateBy(user.getId());
        sysTenant.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysTenant.setTenantFlag(CommonConstant.STATUS_NORMAL);
        Boolean bool = sysTenantService.insert(sysTenant);
        SysUser sysUser = new SysUser();
        sysUser.setUserId(sysTenant.getAdmin());
        sysUser.setTenantFlag("1");
        sysUser.setTenantId(sysTenant.getTenantId());
        sysUserService.updateById(sysUser);
        SysRole sss = new SysRole();
        sss.setCreateBy(sysTenant.getAdmin());
        sss.setRoleDesc("default");
        sysRoleService.deleteDefaultCreateRole(sss);
        List<SysRole> sysRoles = initRole(sysTenant.getAdmin());
        sysRoles.forEach(sysRole -> {
            sysRoleService.insert(sysRole);
        });
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/systenant/save", "POST", JSONUtil.toJsonStr(sysTenant), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        return new R<>(bool);
    }
    private static List<SysRole> initRole(Integer createBy){
            List<SysRole> sysRoleList = new ArrayList<>();
            sysRoleList.add(new SysRole("车场领导",UUID.randomUUID().toString(),"default",new Date(),createBy,"1","1"));
            sysRoleList.add(new SysRole("运营管理人员",UUID.randomUUID().toString(),"default",new Date(),createBy,"1","1"));
            sysRoleList.add(new SysRole("坐席人员",UUID.randomUUID().toString(),"default",new Date(),createBy,"1","1"));
            sysRoleList.add(new SysRole("基础员工",UUID.randomUUID().toString(),"default",new Date(),createBy,"1","1"));
            sysRoleList.add(new SysRole("运维人员",UUID.randomUUID().toString(),"default",new Date(),createBy,"1","1"));
            sysRoleList.add(new SysRole("岗亭人员",UUID.randomUUID().toString(),"default",new Date(),createBy,"1","1"));
        return sysRoleList;
    }

    /**
     * 修改
     * @param sysTenant
     * @return R
     */
    @SysLog("租户修改")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_tenant_edit')")
    public R update(@RequestBody SysTenant sysTenant) throws ParseException {
        SecruityUser user = SecurityUtils.getUserInfo();
        sysTenant.setUpdateBy(user.getId());
        sysTenant.setUpdateTime(NewLocalDateTime(new Date()));
        SysUser sysUser = new SysUser();
        sysUser.setUserId(sysTenant.getAdmin());
        sysUser.setTenantFlag("1");
        sysUser.setTenantId(sysTenant.getTenantId());
        sysUserService.updateById(sysUser);
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/systenant/update", "PUT", JSONUtil.toJsonStr(sysTenant), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        Boolean bool =sysTenantService.updateById(sysTenant);
        SysRole sss = new SysRole();
        sss.setCreateBy(sysTenant.getAdmin());
        sss.setRoleDesc("default");
        sysRoleService.deleteDefaultCreateRole(sss);
        List<SysRole> sysRoles = initRole(sysTenant.getAdmin());
        sysRoles.forEach(sysRole -> {
            sysRoleService.insert(sysRole);
        });
        return new R<>(bool);
    }



    /**
     * 删除
     * @param tenantId
     * @return R
     */
    @SysLog("租户删除")
    @DeleteMapping("/{tenantId}")
    @PreAuthorize("@pms.hasPermission('sys_tenant_del')")
    public R delete(@PathVariable  Integer tenantId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        SysTenantDTO sysTenant =sysTenantService.selectSysTenantById(tenantId);
        SysTenant sysTenant1 = new SysTenant();
        sysTenant1.setDelFlag("1");
        sysTenant1.setAdmin(sysTenant.getAdmin());
        List<SysTenant> tenantList = sysTenantService.selectSysTenantList(sysTenant1);
        if (tenantList.size() < 1 && tenantList != null) {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(sysTenant.getAdmin());
            sysUser.setTenantFlag("0");
            sysUser.setTenantId(null);
            sysUserService.updateById(sysUser);
        }
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/systenant/delete", "DELETE", String.valueOf(tenantId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        sysTenantService.deleteById(tenantId);
        return new R<>(Boolean.TRUE);
    }



    /**
     * 车场树查询
     * @param tenantId
     * @return R
     */
    @GetMapping("/organ/{tenantId}")
    public R selectOrgan(@PathVariable  Integer tenantId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        List<OrganVO> tenantVOS = sysTenantService.selectOrgan(tenantId);
        return new R<>(tenantVOS);
    }
    /**
     *  列表
     * @param sysTenant
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody SysTenant sysTenant) {
        //数据库字段值完整查询
        List<String> roles = SecurityUtils.getRoles();
        sysTenant.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysTenant.setTenantFlag(CommonConstant.STATUS_NORMAL);
        if (!roles.contains(CommonConstant.ROLE_ADMIN)) {
            sysTenant.setAdmin(SecurityUtils.getUserInfo().getId());
        }
        return new R(sysTenantService.selectList(new EntityWrapper<SysTenant>(sysTenant)));
    }



    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res,@RequestParam Map<String, Object> params) throws IOException {
        List<SysTenantDTO> records = page(params).getRecords();
        String[] headInfo = {"序号", "组户名", "租户编码", "上级租户", "所属区域", "管理员", "联系方式", "创建人", "创建时间", "是否启用"};
        String[] sheetName = {"租户"};
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
            SysTenantDTO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i+1);
            tb[index++] = obj.getTenantName();
            tb[index++] = obj.getTenantCode();
            tb[index++] = obj.getTenantParent();
            tb[index++] = obj.getAreaName();
            tb[index++] = obj.getAdminName();
            tb[index++] = obj.getAdminPhone();
            tb[index++] = obj.getCreateName();
            tb[index++] = sf.format(obj.getCreateTime());
            tb[index++] = (obj.getTenantFlag() != null && "1".equals(obj.getTenantFlag())) ? "正常" : "锁定";
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "租户");
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

//        if (listob != null && listob.size() > 0) {
//            for (List<Object> objects : listob) {
//                Student student = new Student();
//                student.setName(objects.get(0).toString());
//                student.setAge(Integer.valueOf(objects.get(1).toString()));
//                student.setAddress(objects.get(2).toString());
//                student.setCreatetime(LocalDateTime(objects.get(3)));
//                student.setUpdatetime(LocalDateTime(objects.get(4)));
//                studentService.insert(student);
//            }
//        }
    }

    public static LocalDateTime LocalDateTime(Object obj) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sim.parse(obj.toString());
        Instant instant = parse.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
    public static LocalDateTime NewLocalDateTime(Date date) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = date;
        Instant instant = parse.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
}
