package com.ztman.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.Student;
import com.ztman.admin.service.RedisService;
import com.ztman.admin.service.StudentService;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
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
 * 学生表
 *
 * @author ztman code generator
 * @date 2018-10-08 09:58:04
 */
@RestController
@RequestMapping("/student")
@Api(value = "StudentController|一个用来测试swagger注解的控制器")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisService redisService;


    ExcelUtil util = new ExcelUtil();


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        return studentService.selectPage(new Query<>(params), new EntityWrapper<>());
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        Student student = studentService.selectById(id);
        return new R<>(student);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getuser")
    public R getUser(){
      SecruityUser t= SecurityUtils.getUserInfo();
      return  new R<>(t);
    }

    /**
     * 保存
     *
     * @param student
     * @return R
     */
    @PostMapping
    public R save(@RequestBody Student student) {
        studentService.insert(student);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param student
     * @return R
     */
    @PutMapping
    public R update(@RequestBody Student student) {
        studentService.updateById(student);
        return new R<>(Boolean.TRUE);
    }


    /**
     * 删除
     *
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        studentService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }


    @GetMapping("/delObjs")
    public R delObjs(@RequestParam(value = "ids") String ids) {
        if (ids != null && !"".equals(ids)) {
            String[] split = ids.split(",");
            for (String s : split) {
                studentService.deleteById(Integer.valueOf(s));
            }
        }
        return new R<>(Boolean.TRUE);
    }

    //表头
    public static final String[] tableHeader1 = {"学生表"};
    public static final String[] tableHeader2 = {"序列", "性别", "", "累计", "", "计划", "实际"};
    public static final String[] tableHeader3 = {"序列", "男", "女", "月累计", "年累计", "合计"};

    public static List<String[]> getStringDate() {
        List<String[]> cacheList = new ArrayList<String[]>();
        for (int j = 0; j < 50; j++) {
            String[] tb = new String[10];
            for (int i = 0; i < 7; i++) {
                tb[i] = "数据" + i;
            }
            cacheList.add(tb);
        }
        return cacheList;
    }

    public static List<String[]> getStudentDate() {
        List<String[]> cacheList = new ArrayList<String[]>();
        for (int j = 0; j < 50; j++) {
            String[] tb = new String[10];
            tb[0] = String.valueOf(j);
            tb[1] = "小明" + j;
            tb[2] = String.valueOf(j);
            tb[3] = "北京中路" + j + "号";
            tb[4] = "2019-03-02";
            tb[5] = "2019-03-02";
            cacheList.add(tb);
        }
        return cacheList;
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res) throws IOException {
        /*//创建sheet名字(集合形式，同时创建多个sheet页)
        String[] sheetName = {"我的sheet1"};
        //创建Excel(创建的类型，并传入sheet集合)
        util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
        //创建第一行信息
        List<String[]> list1 = new ArrayList<String[]>();
        list1.add(tableHeader1);
        //创建第二行信息
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(tableHeader2);
        //创建第三行信息
        List<String[]> list3 = new ArrayList<String[]>();
        list3.add(tableHeader3);


        //设置合并的单元格
        List<Integer[]> alist = new ArrayList<>();
        Integer[] a1 = {0, 0, 0, 6};//开始行,结束行,开始列,结束列
        alist.add(a1);
        //设置第二行单元格合并
        List<Integer[]> blist = new ArrayList<>();
        Integer[] b1 = {1, 2, 0, 0};//开始行,结束行,开始列,结束列
        Integer[] b11 = {1, 1, 1, 2};//开始行,结束行,开始列,结束列
        Integer[] b111 = {1, 1, 3, 4};//开始行,结束行,开始列,结束列
        blist.add(b1);
        blist.add(b11);
        blist.add(b111);

        //设置第三行合并
        List<Integer[]> clist = new ArrayList<>();
        Integer[] c1 = {2, 2, 5, 6};//开始行,结束行,开始列,结束列
        clist.add(c1);

        util.setValue(list1, util.getCellType4Merge(0, 12, alist), 0, 0);
        util.setValue(list2, util.getCellType4Merge(0, 8, blist), 0, 1);
        util.setValue(list3, util.getCellType4Merge(0, 6, clist), 0, 2);
        util.setValue(getStringDate(), util.getCellType4Number(), 0, 3);
        //设置批注
        util.addComment(10, 5, "这是批注信息。", "李明赫");
        //设置数据有效性 目前只支持2003
        //util.SetValidationList(0, 500, 2, 2, new String[]{"选择1", "选择2", "选择3"});
        //给单元格添加提示内容
        //util.setMessage(1, 1, 2, 2, "qq", "12589");
        //util.save2File(res, "E://a");
        util.exportExcel(res, "工资条模板");*/

        String[] student1 = {"学生表导出测试"};
        String[] student2 = {"序号", "姓名", "年龄", "地址", "创建时间", "更新时间"};
        //创建sheet名字(集合形式，同时创建多个sheet页)
        String[] sheetName = {"学生表"};
        //创建Excel(创建的类型，并传入sheet集合)
        util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
        //创建第一行标题头
        List<String[]> list1 = new ArrayList<String[]>();
        list1.add(student1);
        //创建第二行标题头
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(student2);

        List<Integer[]> alist = new ArrayList<>();
        Integer[] a1 = {0, 0, 0, 6};//开始行,结束行,开始列,结束列
        alist.add(a1);
        List<Integer[]> blist = new ArrayList<>();

        util.setValue(list1, util.getCellType4Merge(0, 12, alist), 0, 0);
        util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 1);
        util.setValue(getStudentDate(), util.getCellType4Number(), 0, 2);
        util.exportExcel(res, "学生");
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
