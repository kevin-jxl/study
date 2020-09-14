package com.ztman.common.core.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 类工具类
 *
 * @author L.cm
 */
public class ExcelUtil {

    private Workbook wb = null;
    private Sheet sheet = null;

    /**
     * 模版文件路径
     */
    private String templateFilePath;
    /**
     * 总行数
     * 最大行的index值，实际行数要加1
     */
    private int rowNum = 0;
    /**
     * 总列数
     */
    private int columnNum = 0;

    /**
     * 要创建的excel版本
     *
     * @author liubenlong
     */
    public enum EXCELVERSION {
        /**
         * 2003一下版本（含2003）
         */
        EXCEL_VERSION_2003,
        /**
         * 2007以上版本（含2007）
         */
        EXCEL_VERSION_2007;
    }

    private EXCELVERSION currentVersion;

    private final static String excel2003L = ".xls";    //2003- 版本的excel

    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    /**
     * 根据模板初始化
     *
     * @param tmpleteFilePath
     * @param sheetIndex      <p>默认第一个sheet</p>
     */
    public ExcelUtil(String tmpleteFilePath, int sheetIndex) {
        InputStream in = null;
        try {
            in = new FileInputStream(tmpleteFilePath);
            wb = WorkbookFactory.create(in);

            sheet = wb.getSheetAt(sheetIndex);

            rowNum = sheet.getLastRowNum();

            if (sheet.getRow(0) != null) {
                /*
                 * 此处并不能代表整个工作表的总列数！！！
                 */
                columnNum = sheet.getRow(0).getLastCellNum();
            }

            System.out.println("第" + sheetIndex + "个工作薄；总行数：" + rowNum + "；总列数：" + columnNum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public ExcelUtil() {

    }


    /**
     * 最后总结一下HSSFDataFormat.GetFormat和HSSFDataFormat.GetBuiltinFormat的区别：
     * 当使用Excel内嵌的（或者说预定义）的格式时，直接用HSSFDataFormat.GetBuiltinFormat静态方法即可。
     * 当使用自己定义的格式时，必须先调用HSSFWorkbook.CreateDataFormat()，
     * 因为这时在底层会先找有没有匹配的内嵌FormatRecord，如果没有就会新建一个FormatRecord，所以必须先调用这个方法，
     * 然后你就可以用获得的HSSFDataFormat实例的GetFormat方法了，当然相对而言这种方式比较麻烦，所以内嵌格式还是用HSSFDataFormat.GetBuiltinFormat静态方法更加直接一些。
     * 不过自定义的格式也不是天马行空随便定义，还是要参照Excel的格式表示来定义，具体请看相关的Excel教程。
     */


    /**
     * 单元格文本样式
     */
    public CellStyle getCellType4Text() {
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        return cellStyle;
    }

    /**
     * 单元格日期格式
     */
    public CellStyle getCellType4Date() {
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));
        return cellStyle;
    }

    /**
     * 单元格格式:保留两位小数
     * 目前只支持2003版本，2007版本未找到对应方法
     * <p>
     * <b>注意:填入表格的数据 不可以 是字符串，单元格也不可以设为文本格式</b>
     * <p>
     * <b>完整的Excel内嵌格式列表大家可以excel中单元格格式  自定义 中有哪些内容。百分比、货币格式等都可以在其中找到</b>
     */
    public CellStyle getCellType4Number() {
        if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
            CellStyle cellStyle = wb.createCellStyle();
            short format = 0;
            format = HSSFDataFormat.getBuiltinFormat("0.00");
            cellStyle.setDataFormat(format);
            return cellStyle;
        }
        return null;
    }

    /**
     * 大小写转换
     * <p>
     * <b>注意:填入表格的数据 不可以 是字符串，单元格也不可以设为文本格式</b>
     *
     * @return
     */
    public CellStyle getCellType4CaseConversion() {
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("[DbNum2][$-804]0"));
        return cellStyle;
    }

    /**
     * 合并单元格
     *
     * @param sheetIndex       选择的sheet页
     * @param fontSize         字体大小
     * @param cellRangeAddress 单元格合并集合
     * @return
     */
    public CellStyle getCellType4Merge(int sheetIndex, int fontSize, List<Integer[]> cellRangeAddress) {
        sheet = wb.getSheetAt(sheetIndex);
        CellStyle cellStyle = wb.createCellStyle();
/*        //设置背景色:
        cellStyle.setFillForegroundColor((short) 13);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框*/

        //设置居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        //设置字体
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setBold(true);
        font.setFontHeightInPoints((short) fontSize);//设置字体大小

        Font font2 = wb.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setBold(true);//粗体显示
        font2.setFontHeightInPoints((short) fontSize);

        cellStyle.setFont(font);//选择需要用到的字体格式

        //设置列宽
        // sheet.setColumnWidth(0, 40);//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
        //设置自动换行
        //cellStyle.setWrapText(true);
        if (cellRangeAddress != null && cellRangeAddress.size() > 0) {
            for (Integer[] cell : cellRangeAddress) {
                sheet.addMergedRegion(new CellRangeAddress(cell[0], cell[1], cell[2], cell[3]));
            }
        }
        //sheet.addMergedRegion(new CellRangeAddress();
        return cellStyle;
    }

    /**
     * 取Excel所有数据，包含header
     */
    public List<String[]> getAllData() {
        List<String[]> dataList = new ArrayList<String[]>();
        if (columnNum > 0) {
            for (int i = 0; i < rowNum + 1; i++) {
                String[] singleRow = new String[columnNum];
                for (int j = 0; j < columnNum; j++) {
                    singleRow[j] = getCellVal(i, j);
                }
                dataList.add(singleRow);
            }
        }
        return dataList;
    }


    /**
     * 获取单元格数据
     *
     * @param rowNum    P>从0开始</P>
     * @param columnNum P>从0开始</P>
     * @return
     */
    public String getCellVal(int rowNum, int columnNum) {

        String cellVal = null;
        Cell cell = sheet.getRow(rowNum).getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                cellVal = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellVal = Boolean.toString(cell.getBooleanCellValue());
                break;
            //数值
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellVal = String.valueOf(cell.getDateCellValue());
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String temp = cell.getStringCellValue();
                    //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                    if (temp.indexOf(".") > -1) {
                        cellVal = String.valueOf(new Double(temp)).trim();
                    } else {
                        cellVal = temp.trim();
                    }
                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellVal = cell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_ERROR:
                cellVal = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellVal = cell.getStringCellValue();
                if (cellVal != null) {
                    cellVal = cellVal.replaceAll("#N/A", "").trim();
                }
                break;
            default:
                cellVal = "";
                break;
        }
        return cellVal;
    }

    /**
     * 设置单元格数据
     * 下标从0开使
     *
     * @param rownum
     * @param column
     * @param value
     */
    public void setCellVal(int rownum, int column, String value) {
        Row row = sheet.getRow(rownum);
        if (null == row) {
            row = sheet.createRow(rownum);
        }
        Cell cell = row.getCell(column);
        if (null == cell) {
            cell = row.createCell(column);
        }
        cell.setCellValue(value);
    }


    /**
     * 设置单元格数据    简单公式
     * excel 中可以用的这里都可以用
     *
     * @param rownum
     * @param column
     * @param value  例：setCellValByFormula(6,6,"1+2*3")
     *               setCellValByFormula(6,6,"A1*B1")
     *               setCellValByFormula(6,6,"sum(A1:C1)")
     *               <p>
     *               <b>但要注意，在利用NPOI写程序时，行和列的计数都是从0开始计算的，但在设置公式时又是按照Excel的单元格命名规则来的。</b>
     */
    public void setCellValByFormula(int rownum, int column, String value) {
        sheet.getRow(rownum).getCell(column).setCellFormula(value);
    }


    /**
     * 图形操作
     * 目前仅支持2003版本
     * <p>
     * HSSFSimpleShape.OBJECT_TYPE_RECTANGLE	//矩形
     * <p>
     * HSSFSimpleShape.OBJECT_TYPE_LINE	//线
     * <p>
     * HSSFSimpleShape.OBJECT_TYPE_OVAL	//(椭)圆
     * <p>
     * 等
     */
    public void draw(short shapeType, int col1, int row1, int col2, int row2) {
        if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
            HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();

            HSSFClientAnchor a1 = new HSSFClientAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row2);

            HSSFSimpleShape line1 = patriarch.createSimpleShape(a1);

            line1.setShapeType(shapeType);

            line1.setLineStyle(HSSFShape.LINESTYLE_SOLID);
            //在NPOI中线的宽度12700表示1pt,所以这里是0.5pt粗的线条。
            line1.setLineWidth(6350);
        }
    }


    /**
     * 图形操作
     * 目前仅支持2003版本
     */
    public void addPicture() {
        try {
            if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
                File file = new File("f:/q.jpg");

                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] buf = new byte[inputStream.available()];
                inputStream.read(buf);

                int pictureIdx = wb.addPicture(buf, HSSFWorkbook.PICTURE_TYPE_JPEG);

                // Create the drawing patriarch.  This is the top level container for all shapes.
                Drawing drawing = sheet.createDrawingPatriarch();

                //add a picture
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 0, (short) 0, 0, (short) 1, 3);

                Picture picture = drawing.createPicture(anchor, pictureIdx);

                //自动调节图片大小
//				 picture.resize();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 锁定单元格，单元格内容不可修改
     * <p>只能控制某些单元格不被锁定。因为sheet.protectSheet("password");会锁定所有单元格</p>
     */
    public void lockCell() {
        Row row1 = sheet.createRow(0);
        Cell cel1 = row1.createCell(0);
        Cell cel2 = row1.createCell(1);

        CellStyle unlocked = wb.createCellStyle();
        unlocked.setLocked(false);

        CellStyle locked = wb.createCellStyle();
        locked.setLocked(true);

        cel1.setCellValue("没被锁定");
        cel1.setCellStyle(unlocked);

        cel2.setCellValue("被锁定");
        cel2.setCellStyle(locked);

        sheet.protectSheet("password");//密码
    }


    /**
     * 锁定行或列，行或列不随滚动条滑动
     */
    public void lockRowOrColumn() {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("冻结列");
        /*
         * 第一个参数表示要冻结的列数；
         * 第二个参数表示要冻结的行数，这里只冻结列所以为0；
         * 第三个参数表示右边区域可见的首列序号，从1开始计算；
         * 第四个参数表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0；
         */
//			 sheet.createFreezePane(1, 0, 1, 0);

        sheet.createFreezePane(1, 2, 1, 2);//冻结两行，一列
    }


    /**
     * 保存修改后的数据到文件
     *
     * @param newFilePath <P>文件绝对路径 , 文件名不含后缀</P>
     * @throws IOException
     */
    public void save2File(String newFilePath) {
        FileOutputStream out = null;
        try {
            if (null != newFilePath && !"".equals(newFilePath)) {
                //校验excel版本
                if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) newFilePath += ".xls";
                else newFilePath += ".xlsx";

                out = new FileOutputStream(newFilePath);
                out.flush();
                wb.write(out);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * WEB环境中使用，文件下载
     *
     * @param response
     * @param fileName
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response, String fileName) throws IOException {
        // 如果文件名有中文，必须URL编码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        // ContentType 可以不设置
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        //校验excel版本
        if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) fileName += ".xls";
        else fileName += ".xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        wb.write(response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in
     * @param fileName //文件名
     * @param sheetNum //读取第几个sheet页
     * @param firstNum //从这个sheet的页的第几行开始读取
     * @return
     * @throws Exception
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName, Integer sheetNum, Integer firstNum) throws Exception {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        short firstCellNum = 0;
        short lastCellNum = 0;
        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        if (work != null && work.getNumberOfSheets() > 0) {
            sheet = work.getSheetAt(sheetNum);
            if (sheet == null) {
                return null;
            }
//        for (int i = 0; i < work.getNumberOfSheets(); i++) {
//            sheet = work.getSheetAt(i);
//            if (sheet == null) {
//                continue;
//            }
            //获取开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获取结束行
            int la = sheet.getLastRowNum();
            //计算数据从哪行开始
            int num = firstRowNum + firstNum;
            if (num > 0) {
                Row btrow = sheet.getRow(num - 1);
                if (btrow != null) {
                    firstCellNum = btrow.getFirstCellNum();
                    lastCellNum = btrow.getLastCellNum();
                }
            }

            //遍历当前sheet中的所有行
            for (int j = num; j <= la; j++) {
                //获取该行
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j || row.getRowNum() < 2) {
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = firstCellNum; y < lastCellNum; y++) {
                    if (y > 0) {
                        cell = row.getCell(y);
                        if (cell != null) {
                            li.add(this.getCellValue(cell));
                        } else {
                            li.add("");
                        }
                    }
                }
                list.add(li);
            }
            //}
        }
        in.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }


    /**
     * 方法名称：SetDataValidation
     * 内容摘要：设置数据有效性 --列表
     * 目前只支持2003
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param textList
     */
    public void SetValidationList(int firstRow, int lastRow, int firstCol, int lastCol, String[] textList) {
        //加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);

        //设置数据有效性加载在哪个单元格上。
        //四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        //数据有效性对象
        HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
        data_validation.createErrorBox("输入值类型或大小有误！", "请从下拉列表中选择值。");
        sheet.addValidationData(data_validation);
    }

    /**
     * 数据有效性--日期[1901以后]
     * 目前只支持2003
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @return
     */
    public void setValidateDate(int firstRow, int lastRow, int firstCol, int lastCol) {
        DVConstraint constraint = DVConstraint.createDateConstraint(
                DVConstraint.OperatorType.GREATER_THAN, "1901/01/01", "1901/01/01", "yyyy/MM/dd");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
        ret.createErrorBox("输入值类型或大小有误！", "日期型，输入日期格式：“2012-02-02”或“2012/02/02”。");
        sheet.addValidationData(ret);
    }

    /**
     *    * 根据公式设置数据有效性
     *    * <p>
     *    * //这里不管是哪一列都写A1
     *    * <p>
     *       * util.setValidateByFormula("COUNTIF(A1,\"*@*.*\")=1",0, 10, 3,3,"请输入正确邮箱格式！");
     *    * @param firstRow
     *    * @param lastRow
     *    * @param firstCol
     *    * @param lastCol
     *    * @return
     *   
     */
    public void setValidateByFormula(String formul, int firstRow, int lastRow, int firstCol, int lastCol, String errorMessage) {
        //创建公式约束（数据有效性）
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint(formul);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        //数据有效性对象
        DataValidation validation = new HSSFDataValidation(regions, constraint);
        validation.createErrorBox("输入值类型或大小有误！", errorMessage);


        sheet.addValidationData(validation);
    }


    /**
     * 数据有效性-->0的数字
     * 目前只支持2003
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public void setValidateDecimal(int firstRow, int lastRow, int firstCol, int lastCol) {
        // 创建一个规则：>0的数字
        DVConstraint constraint = DVConstraint.createNumericConstraint(
                DVConstraint.ValidationType.DECIMAL,
                DVConstraint.OperatorType.GREATER_OR_EQUAL, "0", "0");
        // 设定在哪个单元格生效
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 创建规则对象
        HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
        ret.createErrorBox("输入值类型或大小有误！", "数值型，请输入大于0 的数值。");
        sheet.addValidationData(ret);
    }


    /**
     * 数据有效性--长度
     * 目前只支持2003
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public void setValidateLength(int firstRow, int lastRow, int firstCol, int lastCol) {
        DataValidation ret = null;
        if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
            // 创建一个规则：1-100的数字
            DVConstraint constraint = DVConstraint.createNumericConstraint(
                    DataValidationConstraint.ValidationType.TEXT_LENGTH,
                    DataValidationConstraint.OperatorType.BETWEEN, "0", "10");
            // 设定在哪个单元格生效
            CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
            // 创建规则对象
            ret = new HSSFDataValidation(regions, constraint);
            ret.createErrorBox("输入值长度有误！", "长度介于[0,10]");
        }
        sheet.addValidationData(ret);
    }

    /**
     * 给单元格添加提示内容
     * <p>
     * <b>这里不会将原有的数据校验覆盖</b>
     *
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param title
     * @param text
     */
    public void setMessage(int firstRow, int lastRow, int firstCol, int lastCol, String title, String text) {
        /*
         * 这里创建一个空的数据有效性校验 。这里不会将原有的数据校验覆盖
         */
        DVConstraint constraint = DVConstraint.createNumericConstraint(
                DVConstraint.ValidationType.ANY,
                DVConstraint.OperatorType.IGNORED, null, null);
        // 设定在哪个单元格生效
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 创建规则对象
        HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);
        /*
         * 设置提示内容,标题,内容
         */
        ret.createPromptBox("mm", "www.tangxiangcun.com");
        sheet.addValidationData(ret);
    }


    /**
     * 获取某一行数据
     *
     * @param rowIndex 计数从0开始，rowIndex为0代表header行
     * @return
     */
    public String[] getRowData(int rowIndex) {
        String[] dataArray = null;
        if (rowIndex > columnNum) {
            return dataArray;
        } else {
            String[] singleRow = new String[columnNum];
            for (int j = 0; j < columnNum; j++) {
                singleRow[j] = getCellVal(rowIndex, j);
            }
            return singleRow;
        }
    }

    /**
     * 设置单元格为文本样式
     *
     * @param cell
     */
    public void setCell2Text(Cell cell) {
        cell.setCellStyle(getCellType4Text());
    }

    /**
     * 获取某一列数据
     *
     * @param columnIndex
     * @return
     */
    public String[] getColumnData(int columnIndex) {
        String[] dataArray = new String[rowNum + 1];
        if (columnIndex > columnNum) {
            return dataArray;
        } else {
            for (int i = 0; i < rowNum + 1; i++) {
                dataArray[i] = getCellVal(i, columnIndex);
            }
        }
        return dataArray;
    }


    /**
     * 创建excel--带数据
     *
     * @param filePath
     * @param headList
     * @param headstyle
     * @param bodyList
     * @param bodystyle
     */
    public void createExcel(String filePath,
                            List<String> headList,
                            HSSFCellStyle headstyle,
                            List<String[]> bodyList,
                            HSSFCellStyle bodystyle) throws IOException {
        sheet = wb.createSheet();
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            Cell cell = headRow.createCell(i);
            cell.setCellValue(headList.get(i));
            if (null != headstyle) cell.setCellStyle(headstyle);
        }
        for (int i = 1; i < bodyList.size() + 1; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < bodyList.get(i - 1).length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(bodyList.get(i - 1)[j]);
                if (null != bodystyle) cell.setCellStyle(bodystyle);
            }
        }
        save2File(filePath);
    }

    /**
     * 给excel赋值
     *
     * @param dataList
     * @param style
     * @param sheetIndex
     * @param firstRow   起始行，从0开始
     */
    public void setValue(List<String[]> dataList, CellStyle style, int sheetIndex, int firstRow) {

        sheet = wb.getSheetAt(sheetIndex);
        if (null == sheet) sheet = wb.createSheet();

        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(i + firstRow);
            String[] cellDatas = dataList.get(i);
            for (int j = 0; j < cellDatas.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(cellDatas[j]);
                if (null != style) cell.setCellStyle(style);
            }
        }
        sheet.autoSizeColumn(2);//列宽自适应
    }


    /**
     * 创建空的workBook
     * 直接创建，不适用模板的话，一些配置（比如数据有效应校验、标注等）office和WPS都支持
     *
     * @param version   excel版本
     * @param sheetName sheet名称，允许创建0-n个sheet
     */
    public void createWorkBook(ExcelUtil.EXCELVERSION version, String[] sheetName) {

        this.currentVersion = version;

        if (EXCELVERSION.EXCEL_VERSION_2003 == currentVersion) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        if (null != sheetName && sheetName.length > 0) {
            for (String sheetname : sheetName) {
                sheet = wb.createSheet(sheetname);
            }
        } else {
            sheet = wb.createSheet();
        }
    }


    /**
     * 添加批注
     *
     * @param rowIndex
     * @param columnIndex
     * @param richText
     * @param author
     */
    public void addComment(int rowIndex, int columnIndex, String richText, String author) {
        ClientAnchor anchor = null;
        RichTextString richTextString = null;

        //创建绘图对象
        Drawing p = sheet.createDrawingPatriarch();
        //创建单元格对象,批注插入到4行,1列,B5单元格
        Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
        //获取批注对象
// 	  HSSFClientAnchor的参数说明：
//   参数   说明
// 	dx1  第1个单元格中x轴的偏移量
// 	dy1  第1个单元格中y轴的偏移量
// 	dx2     第2个单元格中x轴的偏移量
// 	dy2  第2个单元格中y轴的偏移量
// 	col1 第1个单元格的列号
// 	row1  第1个单元格的行号
// 	col2 第2个单元格的列号
// 	row2 第2个单元格的行号
        //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
        if (this.currentVersion == EXCELVERSION.EXCEL_VERSION_2003) {
            anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6);
            richTextString = new HSSFRichTextString(richText);
        } else {
            anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6);
            richTextString = new XSSFRichTextString(richText);
        }

        Comment comment = p.createCellComment(anchor);
        //输入批注信息
        comment.setString(richTextString);
        //添加作者,选中B5单元格,看状态栏
        comment.setAuthor(author);
        //是否可见（可见值一打开excel表格就显示）；默认隐藏
        comment.setVisible(true);
        //将批注添加到单元格对象中
        cell.setCellComment(comment);
    }


    public Workbook getWb() {
        return wb;
    }


    public void setWb(Workbook wb) {
        this.wb = wb;
    }


    public Sheet getSheet() {
        return sheet;
    }


    public void setSheet(int sheetIndex) {
        this.sheet = wb.getSheetAt(sheetIndex);
    }


    /**
     * 获取总行数
     * 最大行的index值，实际行数要加1
     *
     * @return
     */
    public int getRowNum() {
        return rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }


    public String getTemplateFilePath() {
        return templateFilePath;
    }


    public EXCELVERSION getCurrentVersion() {
        return currentVersion;
    }


    public void setCurrentVersion(EXCELVERSION currentVersion) {
        this.currentVersion = currentVersion;
    }


    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
}
