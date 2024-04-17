package com.jt.www.util;

import com.jt.www.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @ClassName ReadExcelUtil
 * @Description
 * @Author yanglichao
 * @Date 2019/4/16
 * @Version 1.0
 **/
@Slf4j
public class ReadExcelUtil {

    private static FormulaEvaluator evaluator;
    /**
     * 功能描述: 总条数
     *
     * @auther: yanglichao
     * @param: * @param null
     * @return:
     * @date: 2019/4/16 11:35
     */
    private int totalCells = 0;

    /**
     * 功能描述: 构造方法
     *
     * @auther: yanglichao
     * @param: * @param
     * @return:
     * @date: 2019/4/16 11:34
     */
    public ReadExcelUtil() {
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            /*
      功能描述: 错误信息接收器

      */
            String errorMsg = "文件不是excel格式";
            return false;
        }
        return true;
    }

    /**
     * 读EXCEL文件，获取Excel信息集合
     *
     * @param fileName
     * @param multipartFile
     * @param keys
     * @param sheetNo       读取第几个sheet
     * @param fileName
     * @param keys
     * @param multipartFile
     * @param row           第几行开始
     * @param cell          第几列开始
     * @return
     */
    public List<LinkedHashMap<String, String>> getExcelInfo(String fileName, List<String> keys, MultipartFile multipartFile, int sheetNo, int row, int cell, int offset) {
        // 转换为0开始
        row--;
        cell--;
        //初始化分社信息的集合
        List<LinkedHashMap<String, String>> pds;
        //初始化输入流
        InputStream is = null;
        // 判断sheetNo有效性，如果小于等于零则返回异常
        //验证文件名是否合格
        if (!validateExcel(fileName)) {
            throw new BizException("文件不合格，请上传Excel文件");
        }
        //根据文件名判断文件是2003版本还是2007版本
        boolean isExcel2003 = true;
        if (isExcel2007(fileName)) {
            isExcel2003 = false;
        }
        // 读取输入流
        try {
            is = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根据excel里面的内容读取信息
        pds = getExcelInfo(is, keys, isExcel2003, sheetNo, row, cell, offset);
        return pds;
    }

    /**
     * 根据excel里面的内容读取信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<LinkedHashMap<String, String>> getExcelInfo(InputStream is, List<String> keys, boolean isExcel2003, int sheetNo, int row, int cell, int offset) {
        List<LinkedHashMap<String, String>> pds = null;
        try {
            // 根据版本选择创建Workbook的方式
            Workbook wb = null;
            //当excel是2003时
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面的信息
            pds = readExcelValue(wb, sheetNo, keys, row, cell, offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pds;
    }

    /**
     * 读取Excel里面的信息
     *
     * @param wb
     * @return
     */
    private List<LinkedHashMap<String, String>> readExcelValue(Workbook wb, int sheetNo, List<String> keys, int t_row, int t_cell, int offset) {
        //得到第几个shell
        Sheet sheet = wb.getSheetAt(sheetNo - 1);
        // 启用公式计算
        evaluator = wb.getCreationHelper().createFormulaEvaluator();
        //得到Excel的行数
        /*
      功能描述: 总行数

      */
        int totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(t_row) != null) {
            this.totalCells = sheet.getRow(t_row).getPhysicalNumberOfCells() + offset;
        }
        log.info("totalCells:{}",totalCells);
        List<LinkedHashMap<String, String>> pds = new ArrayList<>();
        LinkedHashMap<String, String> pd;
        //循环Excel行数,从第二行开始，标题不读取
        for (int r = t_row; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            pd = new LinkedHashMap<>();
            //循环Excel的列
            int a_cell = 0;
            for (int c = t_cell; c < this.totalCells; c++) {
                /*if (a_cell>5) {
                    continue;
                }*/
                // 获取列对象
                Cell cell = row.getCell(c);
                //添加列的信息
                pd.put(keys.get(a_cell), getStringValueFromCell(cell).trim());
                a_cell++;
            }
            //添加分社
            pds.add(pd);
        }
        return pds;
    }

    /**
     * 读入excel的内容转换成字符串
     *
     * @param cell
     * @return
     */
    public String getStringValueFromCell(Cell cell) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                double d = cell.getNumericCellValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                cellValue = sFormat.format(date);
            } else {
                cellValue = decimalFormat.format((cell.getNumericCellValue()));
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            cellValue = "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            cellValue = "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cellValue = getCellValue(evaluator.evaluate(cell));
        }
        return cellValue;
    }

    /**
     * 功能描述: 获取值
     *
     * @auther: yanglichao
     * @param: * @param cell
     * @return: java.lang.String
     * @date: 2019/5/20 20:48
     */
    private static String getCellValue(CellValue cell) {
        String cellValue = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                double d = cell.getNumberValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                cellValue = sFormat.format(date);
//                } else {
//                    cellValue = decimalFormat.format((cell.getNumberValue()));
//                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                break;
            default:
                break;
        }
        return cellValue;
    }

    /**
     * 功能描述: 是否是2003的excel，返回true是2003
     *
     * @auther: yanglichao
     * @param: * @param null
     * @return:
     * @date: 2019/4/16 11:32
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 功能描述: 是否是2007的excel，返回true是2007
     *
     * @auther: yanglichao
     * @param: * @param null
     * @return:
     * @date: 2019/4/16 11:33
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}