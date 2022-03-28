package com.sky.test;

import com.sky.test.utils.ExcelPoiUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        SupervisePointDto point1 = new SupervisePointDto("1","1","部门1","ROLE1","处理人1","状态1","2021-08-21","true");
        SupervisePointDto point2 = new SupervisePointDto("1","2","部门2","ROLE1","处理人1","状态1","2021-08-21","true");
        SupervisePointDto point3 = new SupervisePointDto("1","3","部门3","ROLE1","处理人1","状态1","2021-08-21","true");
        SupervisePointDto point4 = new SupervisePointDto("2","4","部门4","ROLE1","处理人1","状态1","2021-08-21","true");
        SupervisePointDto point5 = new SupervisePointDto("2","5","部门5","ROLE1","处理人1","状态1","2021-08-21","true");

        List<SupervisePointDto> pointList1 = new ArrayList<>();
        pointList1.add(point1);
        pointList1.add(point2);
        pointList1.add(point3);
        List<SupervisePointDto> pointList2 = new ArrayList<>();
        pointList2.add(point4);
        pointList2.add(point5);


        SuperviseBusinessHeaderDto header1 = new SuperviseBusinessHeaderDto("1", "RED", "A", "2021032", "ET", "test1", "d打开了几顿饭", "特地方的", "已处理", pointList1);
        SuperviseBusinessHeaderDto header2 = new SuperviseBusinessHeaderDto("2", "YELLOW", "A", "2021032", "ET", "test1", "d打开了几顿饭", "特地方的", "已处理", pointList2);
        SuperviseBusinessHeaderDto header3 = new SuperviseBusinessHeaderDto("", "ORANGE", "A", "2021032", "ET", "test1", "d打开了几顿饭", "特地方的", "已处理", null);
        SuperviseBusinessHeaderDto header4 = new SuperviseBusinessHeaderDto("10", "ORANGE", "A", "2021032", "ET", "test1", "d打开了几顿饭", "特地方的", "已处理", null);

        List<SuperviseBusinessHeaderDto> headerList = new ArrayList<>();
        headerList.add(header1);
        headerList.add(header2);
        headerList.add(header3);
        headerList.add(header4);
        headerList.add(header1);
        headerList.add(header3);
        headerList.add(header2);

        List<SuperviseBusinessHeaderDto> sortedHeaderList = headerList.stream().sorted(Comparator.comparing(header -> {
            if (StringUtils.isEmpty(header.getSbHeaderId())){
                return 0;
            }else {
                return Integer.parseInt(header.getSbHeaderId());
            }
        })).collect(Collectors.toList());

        List<SuperviseAllInfo> allList = new ArrayList<>();
        for (SuperviseBusinessHeaderDto header : sortedHeaderList){
            if (header.getPointList()!=null){
                for (SupervisePointDto point: header.getPointList()){
                    SuperviseAllInfo allInfo = new SuperviseAllInfo(header, point);
                    allList.add(allInfo);
                }
            }else {
                SuperviseAllInfo allInfo = new SuperviseAllInfo(header);
                allList.add(allInfo);
            }
        }


        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    Workbook wb = new XSSFWorkbook();
//        writeExcel(headerList,wb,"监督报告");

                    ExcelPoiUtils.writeExcelSheet(allList,SuperviseAllInfo.class,wb,"廉洁监督",sortedHeaderList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }


    }



    public static void writeExcel(List<SuperviseBusinessHeaderDto> headerList,Workbook wb, String sheetName) throws IOException {

        Class class1 = SuperviseBusinessHeaderDto.class;
        Class class2 = SupervisePointDto.class;

        Field[] fields = class1.getDeclaredFields();
        // 从dto的属性中提取出表头
        List<Field> fieldList = getFileds(class1);
        List<Field> fieldList2 = getFileds(class2);

        //Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
        AtomicInteger rowNum = new AtomicInteger();

        // 创建表头

        Row row = sheet.createRow(rowNum.getAndIncrement());
        AtomicInteger titleColNum = new AtomicInteger();
        // 左侧表单表头
        fieldList.forEach(field -> {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            // 表头名
            String columnName = "";
            if (annotation != null) {
                columnName = annotation.value();
            }else {
                columnName = field.getName();
            }

            Cell cell = row.createCell(titleColNum.getAndIncrement());

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            cellStyle.setFont(font);
            // 设置自动换行
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnName);
        });
        // 右侧表单表头
        fieldList2.forEach(field -> {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            // 表头名
            String columnName = "";
            if (annotation != null) {
                columnName = annotation.value();
            }else {
                columnName = field.getName();
            }
            Cell cell = row.createCell(titleColNum.getAndIncrement());

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
            cellStyle.setFont(font);
            // 设置自动换行
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(columnName);
        });




        if (headerList.size()!=0) {
            headerList.forEach(headerDto -> {
                Row row1 = sheet.createRow(rowNum.get());
                AtomicInteger colNum = new AtomicInteger();

                fieldList.forEach(field -> {
                    Class<?> type = field.getType();
                    Object value = "";
                    try {
                        Object o = field.get(headerDto);
                        if (o instanceof List){
                            value = ((List) o).size();
                        }else {
                            value = o;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cell cell = row1.createCell(colNum.get());
                    CellRangeAddress region = new CellRangeAddress(rowNum.get(), rowNum.get()+headerDto.getPointList().size(), colNum.get(), colNum.get());
                    sheet.addMergedRegion(region);
                    colNum.getAndIncrement();
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                });
                rowNum.getAndAdd(headerDto.getPointList().size()+1);
            });
        }


        //写入表格数据
//        if (headerList.size()!=0) {
//            headerList.forEach(headerDto -> {
//                Row row1 = sheet.createRow(rowNum.getAndIncrement());
//                AtomicInteger colNum = new AtomicInteger();
//                fieldList.forEach(field -> {
//                    Class<?> type = field.getType();
//                    Object value = "";
//
//                    Object headerValue = null;
//                    Object pointValue = null;
//                    try {
//                        headerValue = field.get(headerDto);
//                        if (headerValue instanceof List){
//                            List<SupervisePointDto> pointList = (List) headerValue;
//                            for (SupervisePointDto pointDto : pointList){
//                                Row row2= sheet.createRow(rowNum.getAndIncrement());
//                                fieldList2.forEach(pointField->{
//                                    try {
//                                        Object o = pointField.get(pointDto);
//                                        Cell cell = row2.createCell(fieldList.size()+colNum.getAndIncrement());
//                                        if (o != null) {
//                                            System.out.println(o);
//                                            cell.setCellValue(o.toString());
//                                        }
//                                    } catch (IllegalAccessException e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//
//                            }
//                        }else {
//                            Cell cell = row1.createCell(colNum.getAndIncrement());
//                            if (headerValue != null) {
//                                System.out.println(headerValue);
//                                cell.setCellValue(headerValue.toString());
//                            }
//                        }
//
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//
//                });
//                CellRangeAddress region = new CellRangeAddress(1, 2, 0, 0);
//                sheet.addMergedRegion(region);
//
//            });
//        }
        for (int i = 0; i < fieldList.size(); i++) {
            sheet.autoSizeColumn(i);
            // The maximum column width for an individual cell is 255 characters.
            // 原方法会导致单元格数据量过大时,单元格列宽超过最大列宽255的限制
            // sheet.setColumnWidth(i,sheet.getColumnWidth(i)*17/10);
            int columnWidth = sheet.getColumnWidth(i) * 17 / 10;
            // 如果列宽超出限制,则默认设置为250
            if (columnWidth >= 255 * 256) {

                columnWidth = 255 * 10;
            }

            sheet.setColumnWidth(i, columnWidth);
        }
        //冻结窗格
        wb.getSheet(sheetName).createFreezePane(0, 1, 0, 1);
        //浏览器下载excel
        //buildExcelDocument(URLEncoder.encode(fileName, "UTF-8")+".xlsx",wb,response);
        //生成excel文件
//        buildExcelFile(".\\default.xlsx",wb);


        File file = new File("/Users/sky-mbp16/Downloads/"+sheetName+"_" +LocalDateTime.now()+".xls");
        FileOutputStream fout = new FileOutputStream(file);
        wb.write(fout);
        fout.close();

    }


    private static List<Field> getFileds(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields)
                .filter(field -> {
//                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
//                    if (annotation != null && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
//                    }
//                    return false;
                }).sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        col = annotation.col();
                    }
                    return col;
                }))
                .collect(Collectors.toList());
        return fieldList;
    }
}
