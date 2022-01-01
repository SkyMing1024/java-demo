package com.sky.test.utils;

import com.alibaba.fastjson.JSONObject;
import com.sky.test.ExcelColumn;
import com.sky.test.SuperviseBusinessHeaderDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ExcelPoiUtils {


    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";


    public static <T> void writeExcelSheet(List<T> dataList, Class<T> cls, Workbook wb, String sheetName,List<SuperviseBusinessHeaderDto> list) throws IOException {
        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields)
                .filter(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                }).sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        col = annotation.col();
                    }
                    return col;
                })).collect(Collectors.toList());

        //Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
        AtomicInteger ai = new AtomicInteger();
        {
            Row row = sheet.createRow(ai.getAndIncrement());
            AtomicInteger aj = new AtomicInteger();
            //写入头部
            fieldList.forEach(field -> {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String columnName = "";
                if (annotation != null) {
                    columnName = annotation.value();
                }
                Cell cell = row.createCell(aj.getAndIncrement());

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

                Font font = wb.createFont();
                font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
                cellStyle.setFont(font);
                // 设置自动换行
                cellStyle.setWrapText( true );
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnName);
            });
        }
        if (dataList!=null) {
            dataList.forEach(data -> {
                Row row1 = sheet.createRow(ai.getAndIncrement());
                AtomicInteger aj = new AtomicInteger();
                fieldList.forEach(field -> {
                    Class<?> type = field.getType();
                    Object value = "";
                    try {
                          value = field.get(data);
//                        JSONObject job = (JSONObject) data;
//                        value = job.get(field.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cell cell = row1.createCell(aj.getAndIncrement());
                    if (value != null) {
                        if (type == Date.class) {
                            cell.setCellValue(value.toString());
                        } else {
                            cell.setCellValue(value.toString());
                        }
                        cell.setCellValue(value.toString());
                    }
                });
            });
        }
        // 设置合并单元格
        int rowNum = 1;
        if (list!=null){
            for (SuperviseBusinessHeaderDto header : list) {
                if (header.getPointList()!=null){
                    for (int i = 0; i <= 8; i++) {
                        CellRangeAddress region = new CellRangeAddress(rowNum, rowNum+header.getPointList().size()-1, i, i);
                        sheet.addMergedRegion(region);
                    }
                    rowNum += header.getPointList().size();
                }else {
                    rowNum++;
                }

            }
        }

        // 设置颜色
        for (int i = 1; i <= dataList.size(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            String stringCellValue = cell.getStringCellValue();

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            if ("RED".equals(stringCellValue)){
                cellStyle.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
            }else if ("ORANGE".equals(stringCellValue)){
                cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            }else if ("YELLOW".equals(stringCellValue)){
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            }
            cell.setCellStyle(cellStyle);
        }

        for (int i = 0; i < fieldList.size(); i++) {
            sheet.autoSizeColumn(i);
            // The maximum column width for an individual cell is 255 characters.
            // 原方法会导致单元格数据量过大时,单元格列宽超过最大列宽255的限制
            // sheet.setColumnWidth(i,sheet.getColumnWidth(i)*17/10);
            int columnWidth = sheet.getColumnWidth( i ) * 17 / 10;
            // 如果列宽超出限制,则默认设置为250
            if( columnWidth >= 255 * 256 ) {

                columnWidth = 255 * 10;
            }

            sheet.setColumnWidth( i , columnWidth );
        }
        //冻结窗格
        wb.getSheet(sheetName).createFreezePane(0, 1, 0, 1);
        //浏览器下载excel
        //buildExcelDocument(URLEncoder.encode(fileName, "UTF-8")+".xlsx",wb,response);
        //生成excel文件
//        buildExcelFile(".\\default.xlsx",wb);



        File file = new File("/Users/sky-mbp16/Downloads/"+sheetName+"_" + LocalDateTime.now().toString()+".xls");
        FileOutputStream fout = new FileOutputStream(file);
        wb.write(fout);
        fout.close();
    }



    private static CellStyle getCellStyle(Workbook wb, String value){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        if ("RED".equals(value)){
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        }else if ("ORANGE".equals(value)){
            cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        }else if ("YELLOW".equals(value)){
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        }
        return cellStyle;
    }




}