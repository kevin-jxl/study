package com.ztman.admin.controller;


import com.ztman.admin.api.entity.SysSystemInterface;
import com.ztman.admin.api.vo.SysSystemIntegrationVO;
import com.ztman.admin.service.SysSystemInterfaceService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
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
 * 系统集成的接口管理
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:15
 */
@RestController
@RequestMapping("/interface")
@Slf4j
public class SysSystemInterfaceController {
    @Autowired
    private SysSystemInterfaceService sysSystemInterfaceService;
    //excel工具类
    ExcelUtil util = new ExcelUtil();

    /**
    *  列表
    * @param params
    * @return
    */
    @PostMapping("/page")
    public Page page(@RequestBody Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysSystemInterfaceService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysSystemInterfaceService.selectSysSystemInterfaceListByLike(new Query<>(params));
    }


    /**
     * 信息
     * @param interfaceId
     * @return R
     */
    @GetMapping("/{interfaceId}")
    public R info(@PathVariable("interfaceId") Integer interfaceId) {
        SysSystemInterface sysSystemInterface =sysSystemInterfaceService.selectById(interfaceId);
        return new R<>(sysSystemInterface);
    }

    /**
     * 保存
     * @param sysSystemInterface
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysSystemInterface sysSystemInterface) {
        SecruityUser user = SecurityUtils.getUserInfo();
        sysSystemInterface.setCreateBy(user.getId());
        sysSystemInterface.setDelFlag("1");
        sysSystemInterface.setStatus("1");
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syssysteminterface/save", "POST", JSONUtil.toJsonStr(sysSystemInterface), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        sysSystemInterfaceService.insert(sysSystemInterface);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     * @param sysSystemInterface
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysSystemInterface sysSystemInterface) {
        SecruityUser user = SecurityUtils.getUserInfo();
        sysSystemInterface.setUpdateBy(user.getId());
        sysSystemInterface.setUpdateTime(new Date());
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syssysteminterface/update", "PUT", JSONUtil.toJsonStr(sysSystemInterface), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        sysSystemInterfaceService.updateById(sysSystemInterface);
        return new R<>(Boolean.TRUE);
    }



    /**
     * 删除
     * @param interfaceId
     * @return R
     */
    @DeleteMapping("/{interfaceId}")
    public R delete(@PathVariable  Integer interfaceId) {
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/syssysteminterface/delete", "DELETE", String.valueOf(interfaceId), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        sysSystemInterfaceService.deleteById(interfaceId);
        return new R<>(Boolean.TRUE);
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(@RequestParam Map<String, Object> params,HttpServletResponse res) throws IOException {
        List<SysSystemInterface> records = page(params).getRecords();
        String[] headInfo = {"序号", "接口名称", "URL", "状态", "是否启用", "最后监测时间"};
        String[] sheetName = {"系统集成"};
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
            SysSystemInterface obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i+1);
            tb[index++] = obj.getInterfaceName();
            tb[index++] = obj.getUrl();
            tb[index++] = "1".equals(obj.getStatus())?"正常":"异常";
            tb[index++] = "1".equals(obj.getInterfaceFlag())?"是":"否";
            tb[index++] = sf.format(obj.getLastTestTime());
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "接口集成");

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
