package com.ztman.admin.controller;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.Student;
import com.ztman.admin.api.entity.SysTenant;
import com.ztman.admin.api.vo.SysConsumerVO;
import com.ztman.admin.service.*;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 用户管理
 *
 * @author shuda
 * @date 2020-08-21 16:21:27
 */
@RestController
@RequestMapping("/consumer")
@Slf4j
public class SysConsumerController {
    @Autowired
    private SysConsumerService sysConsumerService;
    @Autowired
    private SysTenantService sysTenantService;
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
        List<String> roles = SecurityUtils.getRoles();
        if(!roles.contains(CommonConstant.ROLE_ADMIN)){
            //租户查询 租户下所有企业的所有用户
            if ("1".equals(SecurityUtils.getUserInfo().getTenantFlag())) {
                SysTenant sysTenant = new SysTenant();
                sysTenant.setDelFlag(CommonConstant.STATUS_NORMAL);
                sysTenant.setAdmin(SecurityUtils.getUserInfo().getId());
                List<SysTenant> sysTenants = sysTenantService.selectList(new EntityWrapper<SysTenant>(sysTenant));
                List<Integer> ids = new ArrayList<>();
                for (int i = 0; i < sysTenants.size(); i++) {
                    List<Integer> ss = sysConsumerService.getALlCompany(sysTenants.get(i).getTenantId());
                    ids.addAll(ss);
                }
                if (ids.size() < 1) {
                    ids.add(0);
                }
                params.put("companyIds", ids);
                return sysConsumerService.selectSysConsumerListByLike(new Query<>(params));
            }else{
                //非租户查询（企业）查询当前企业用户
                List<Integer> ids = new ArrayList<>();
                String companyId = SecurityUtils.getUserInfo().getCompanyId();
                if(StringUtils.isNotEmpty(companyId)&& !"null".equals(companyId)){
                    ids.add(Integer.parseInt(SecurityUtils.getUserInfo().getCompanyId()));
                    params.put("companyIds", ids);
                    return sysConsumerService.selectSysConsumerListByLike(new Query<>(params));
                }
                params.put("createBy", SecurityUtils.getUserInfo().getId());
                return sysConsumerService.selectSysConsumerListByLike(new Query<>(params));
            }


        }else{
            return sysConsumerService.selectSysConsumerListByLike(new Query<>(params));
        }
        //对象模糊查询

    }
    /**
     * 信息
     * @param userId
     * @return R
     */
    @GetMapping("/{userId}")
    public R info(@PathVariable("userId") Integer userId) {
        SysConsumerVO sysConsumerVO =sysConsumerService.selectSysConsumerById(userId);
        return new R<>(sysConsumerVO);
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel( @RequestParam Map<String, Object> params,HttpServletResponse res) throws IOException {
        List<SysConsumerVO> records = page(params).getRecords();
        String[] headInfo = {"序号", "用户名", "姓名", "联系电话", "所属企业", "所属车场", "所属角色", "添加时间", "职务", "最后登录时间", "是否启用"};
        String[] sheetName = {"用户管理表"};
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
            SysConsumerVO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i+1);
            tb[index++] = obj.getUsername();
            tb[index++] = obj.getUserRealname();
            tb[index++] = obj.getPhone();
            tb[index++] = obj.getCompanyName();
            tb[index++] = obj.getOrganName();
            tb[index++] = obj.getRoleName();
            tb[index++] = sf.format(obj.getCreateTime());
            tb[index++] = obj.getPostName();
            tb[index++] = obj.getLastLoginDate()!=null?obj.getLastLoginDate().toLocalDate().toString():"";
            tb[index++] = (obj.getUserFlag() != null && "1".equals(obj.getUserFlag())) ? "正常" : "锁定";
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "用户管理");
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
