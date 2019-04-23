package com.xudasong.service.springcloudservice.util;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xudasong.service.springcloudservice.config.exception.BizException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

/**
 * excel操作公共类-提供excel按照模板输出
 */
public class ExcelUtils {

    public void writeDate(InputStream input, String modelName, OutputStream out,SheetData...sheetDatas){
        Workbook wb = null;
        try {
            if (modelName.endsWith(".xlsx")){
                wb = new XSSFWorkbook(input);
            }else if (modelName.endsWith(".xls")){
                wb = new HSSFWorkbook(input);
            }else {
                throw new RuntimeException("model file format is not vaild, this : "+modelName+" ,eg:.xlsx or xls");
            }
        }catch (IOException e){
            throw new RuntimeException("model excel file load error : "+modelName);
        }
        Sheet source = wb.getSheetAt(0);
        //就一个的话 直接用模板
        int size = sheetDatas.length;
        for (int i = 0; i<size; i++){
            if (i==0){
                wb.setSheetName(0,sheetDatas[0].getName());
            }else {
                Sheet toSheet = wb.createSheet(sheetDatas[i].getName());
                //复制格式
                copySheet(wb,source,toSheet,true);
            }
        }
        for (int i=0;i<size;i++){
            //写数据
            writeData(sheetDatas[i],wb.getSheetAt(i));
        }
        try {
            wb.write(out);
            out.flush();
            wb.close();
            out.close();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Sheet复制
     * @param wb
     * @param fromSheet
     * @param toSheet
     * @param copyValueFlag
     */
    private void copySheet(Workbook wb,Sheet fromSheet,Sheet toSheet,boolean copyValueFlag){
        //合并区域处理
        mergerRegion(fromSheet,toSheet);
        int index = 0;
        for (Iterator<Row> rowIt = fromSheet.rowIterator();rowIt.hasNext();){
            Row tmpRow = rowIt.next();
            Row newRow = toSheet.createRow(tmpRow.getRowNum());
            CellStyle style = tmpRow.getRowStyle();
            if (style!=null){
                newRow.setRowStyle(tmpRow.getRowStyle());
            }
            newRow.setHeight(tmpRow.getHeight());
            //针对第一行设置行宽
            if (index == 0){
                int first = tmpRow.getFirstCellNum();
                int last = tmpRow.getLastCellNum();
                for (int i=first;i<last;i++){
                    int w = fromSheet.getColumnWidth(i);
                    toSheet.setColumnWidth(i,w + 1);
                }
                toSheet.setDefaultColumnWidth(fromSheet.getDefaultColumnWidth());
            }

            //行复制
            copyRow(wb,tmpRow,newRow,copyValueFlag);

            index++;
        }
    }

    /**
     * 复制原有sheet的合并单元格到新创建的sheet
     * @param fromSheet
     * @param toSheet
     */
    private void mergerRegion(Sheet fromSheet,Sheet toSheet){
        int sheetMergerCount = fromSheet.getNumMergedRegions();
        for (int i=0;i<sheetMergerCount;i++){
            CellRangeAddress cra = fromSheet.getMergedRegion(i);
            toSheet.addMergedRegion(cra);
        }
    }

    /**
     * 行复制功能
     * @param wb
     * @param fromRow
     * @param toRow
     * @param copyFlag
     */
    private void copyRow(Workbook wb,Row fromRow,Row toRow,boolean copyFlag){
        for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext();){
            Cell tmpCell = cellIt.next();
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb,tmpCell,newCell,copyFlag);

        }
    }

    /**
     * 复制单元格
     * @param wb
     * @param srcCell
     * @param disCell
     * @param copyValueFlag true就连同cell的内容一起复制
     */
    private void copyCell(Workbook wb,Cell srcCell,Cell disCell,boolean copyValueFlag){
        CellStyle newStyle = wb.createCellStyle();
        newStyle.cloneStyleFrom(srcCell.getCellStyle());
        //样式
        disCell.setCellStyle(newStyle);
        //评论
        if (srcCell.getCellComment() != null){
            disCell.setCellComment(srcCell.getCellComment());
        }
        //不同数据类型处理
        CellType srcCellType = srcCell.getCellTypeEnum();
        disCell.setCellType(srcCellType);
        if (copyValueFlag){
            if (srcCellType == CellType.NUMERIC){
                if (DateUtil.isCellDateFormatted(srcCell)){
                    disCell.setCellValue(srcCell.getDateCellValue());
                }else {
                    disCell.setCellValue(srcCell.getNumericCellValue());
                }
            }else if (srcCellType == CellType.STRING){
                disCell.setCellValue(srcCell.getRichStringCellValue());
            }else if (srcCellType == CellType.BLANK){
                //nothing21
            }else if (srcCellType == CellType.BOOLEAN){
                disCell.setCellValue(srcCell.getBooleanCellValue());
            }else if (srcCellType == CellType.ERROR){
                disCell.setCellValue(srcCell.getErrorCellValue());
            }else if (srcCellType == CellType.FORMULA){
                disCell.setCellValue(srcCell.getCellFormula());
            }else {
                //nothing29
            }
        }
    }

    /**
     * 从实体中解析出字段数据
     * @param data
     * @param field
     * @return
     */
    private Object getValue(Object data,String field){
        if (data instanceof Map){
            Map map = (Map)data;
            return map.get(field);
        }
        try {
            String method = "get" + field.substring(0,1).toUpperCase() + field.substring(1);
            Method m = data.getClass().getMethod(method,null);
            if (m != null){
                return m.invoke(data,null);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    /**
     * 向sheet页中写入数据
     * @param sheetData
     * @param sheet
     */
    public void writeData(SheetData sheetData,Sheet sheet){
        //从sheet中找到匹配符 #{}表示单个，${}表示集合，从该单元格开始向下追加
        for (Iterator<Row> rowIt = sheet.rowIterator();rowIt.hasNext();){
            Row row = rowIt.next();
            //取cell
            for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                Cell cell = row.getCell(j);
                //判断cell的内容是否包含 $ 或者 #
                if (cell !=null && cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null
                        && (cell.getStringCellValue().contains("$") || cell.getStringCellValue().contains("#"))){
                    //剥离$ #
                    String[] winds = CommonUtils.getWildcard(cell.getStringCellValue().trim());
                    for (String wind : winds){
                        writeDate(sheetData,wind,cell,sheet);
                    }
                }
            }
        }
    }

    /**
     * 填充数据
     * @param sheetData
     * @param keyWind #{name}只替换当前 or ${name} 从当前行开始向下替换
     * @param cell
     * @param sheet
     */
    public void writeDate(SheetData sheetData,String keyWind, Cell cell, Sheet sheet){
        String key = keyWind.substring(2,keyWind.length() - 1);
        if (keyWind.startsWith("#")){
            //简单替换
            Object value = sheetData.get(key);
            //为空则替换成空字符串
            if (value == null){
                value = "";
            }
            String cellValue = cell.getStringCellValue();
            cellValue = cellValue.replace(keyWind,value.toString());
            cell.setCellValue(cellValue);
        }else if (keyWind.startsWith("$")){
            //从list中每个实体开始解，行数从当前开始
            int rowindex = cell.getRowIndex();
            int columnindex = cell.getColumnIndex();
            List<? extends Object> listdata = sheetData.getDatas();
            //不为空的时候开始填充
            if (listdata != null && !listdata.isEmpty()){
                for (Object o : listdata){
                    Object cellValue = getValue(o,key);
                    Row row = sheet.getRow(rowindex);
                    if (row == null){
                        row = sheet.createRow(rowindex);
                    }
                    //取出cell
                    Cell c = row.getCell(columnindex);
                    if (c == null){
                        c = row.createCell(columnindex);
                    }
                    if (cell.getCellStyle() != null){
                        c.setCellStyle(cell.getCellStyle());
                    }
                    if (cell.getCellTypeEnum() != null){
                        c.setCellType(cell.getCellTypeEnum());
                    }
                    if (cellValue != null){
                        if (cellValue instanceof Number || CommonUtils.isNumber(cellValue)){
                            c.setCellValue(Double.valueOf(cellValue.toString()));
                        }else if (cellValue instanceof Boolean){
                            c.setCellValue((Boolean) cellValue);
                        }else if (cellValue instanceof Date){
                            c.setCellValue((Date) cellValue);
                        }else {
                            c.setCellValue(cellValue.toString());
                        }
                    }else {
                        //数据为空 如果当前单元格已经有数据则重置为空
                        if (c.getStringCellValue() != null){
                            c.setCellValue("");
                        }
                    }
                    rowindex++;
                }
            }else {
                //list数据为空则将$全部替换空字符串
                String cellValue = "";
                cell.setCellValue(cellValue);
            }
        }
    }

    /**
     * 读取Excel列表
     * @param wsheet
     * @param startIndex
     * @param fields
     * @return
     */
    public List<Map<String,Object>> readSimple(Sheet wsheet, int startIndex, String[] fields){
        List<Map<String,Object>> list = new ArrayList<>();
        for(Row row : wsheet){
            //从数据行开始读
            if (row.getRowNum() >= startIndex){
                Map<String,Object> rowMap = new HashMap<>();
                for (Cell cell : row){
                    //fields代表的列以后的数据忽略 fields.length=6
                    if (cell.getColumnIndex() < fields.length){
                        String key = fields[cell.getColumnIndex()];
                        Object value = getValue(cell);
                        rowMap.put(key,value);
                    }
                }
                list.add(rowMap);
            }
        }
        return list;
    }

    /**
     * 得到Excel中单元格的数据
     * @param cell
     * @return
     */
    private Object getValue(Cell cell){
        Object value = null;
        DecimalFormat df = new DecimalFormat("#.####");
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING :
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC :
                if (DateUtil.isCellDateFormatted(cell)){
                    value = com.xiaoleilu.hutool.date.DateUtil.format(cell.getDateCellValue(), DatePattern.HTTP_DATETIME_PATTERN);
                }else {
                    value = df.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN :
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                value = cell.getStringCellValue();
        }
        return value;
    }


}
