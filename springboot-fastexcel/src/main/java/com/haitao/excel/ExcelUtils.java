package com.haitao.excel;

import cn.idev.excel.FastExcel;
import cn.idev.excel.context.AnalysisContext;
import com.haitao.constent.SymbolConstents;
import com.haitao.enums.FileType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel工具类
 */
@Slf4j
public class ExcelUtils {
    /**
     * 单文件限制条数
     */
    private final static int FILE_LIMIT = 10000;

    /**
     * 更具表名生成，默认路径为本地路径
     * @param list 需要生成的数据
     * @param head 表头
     * @return 路径列表
     */
    public static <T> List<String> exoprtByName(List<T> list, Class<?> head,String name){
        List<String> fileNameList =  new ArrayList<>();
        ListUtils.getPartitionList(list,FILE_LIMIT).stream().parallel().forEach(x->{
            String fileName = ExcelUtils.class.getResource(SymbolConstents.VIRGULE).getPath() + name + (x.getIndex() != 0 ? x.getIndex() : "") + FileType.XLSX;
            I18nEasyExcelBuilder.writeToLocal(head,fileName).doWrite(x.getList());
            fileNameList.add(fileName);
        });
        return fileNameList;
    }

    /**
     * 根据路径生成到本地
     * @param list 需要生成的数据
     * @param head 表头
     * @param pathName 路径名
     * @return 路径列表
     */
    public static <T> List<String> exoprtByPath(List<T> list, Class<?> head,String pathName){
        List<String> fileNameList =  new ArrayList<>();
        //对路径切割
        String path = pathName.substring(0,pathName.lastIndexOf(SymbolConstents.VIRGULE)+1);
        String fileName = pathName.substring(pathName.lastIndexOf(SymbolConstents.VIRGULE)+1,pathName.lastIndexOf(FileType.XLSX.getValue()));
        ListUtils.getPartitionList(list,FILE_LIMIT).stream().parallel().forEach(x->{
            //组装路径导出到本地并保存
            String filePath = path + fileName + (x.getIndex() != 0 ? x.getIndex() : "") + FileType.XLSX;
            I18nEasyExcelBuilder.writeToLocal(head, pathName).doWrite(x.getList());
            fileNameList.add(filePath);
        });
        return fileNameList;
    }

    /**
     * 导出excel到缓存中
     * @param list 需要生成的数据
     * @param head 表头
     * @param fileName 文件名
     * @return 缓存map<文件名, byte[]>
     */
    public static <T> Map<String, byte[]> exoprtToByteArray(List<T> list, Class<?> head, String fileName){
        Map<String,byte[]> fileMap = new HashMap<>();
        ListUtils.getPartitionList(list,FILE_LIMIT).stream().parallel().forEach(x->{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            I18nEasyExcelBuilder.write(baos,head).doWrite(x.getList());
            fileMap.put(fileName + (x.getIndex() != 0 ? x.getIndex() : "") + FileType.XLSX,baos.toByteArray());
        });
        return fileMap;
    }

    /**
     * 实时导出生成压缩文件
     * @param list 需要生成的数据
     * @param head 表头
     * @param fileName 文件名
     * @param httpResponse http相应流
     */
    public static <T> void exoprtToZip(List<T> list, Class<?> head, String fileName, HttpServletResponse httpResponse){
        Map<String,byte[]> map =  exoprtToByteArray(list, head, fileName);
        FileZipUtils.zipFiles(map,fileName,httpResponse);
    }

    /**
     * 生成excel写入到流中，超出限值生成压缩文件否则只是单文件
     * @param list 需要生成的数据
     * @param head 表头
     * @param fileName 文件名
     * @param outputStream 输出
     */
    public static <T> void write(List<T> list, Class<?> head, String fileName, OutputStream outputStream){
        if (list.size() > FILE_LIMIT){
            Map<String,byte[]> map =  exoprtToByteArray(list, head, fileName);
            FileZipUtils.zipFiles(map,outputStream);
        }else {
            I18nEasyExcelBuilder.write(outputStream,head).doWrite(list);
        }
    }

    /**
     * 读取excel
     * @param file 需要的数据
     * @param head 表头
     */
    public static <T> List<T> read(InputStream file, Class<?> head){

        List<T> list = new ArrayList<>();
        I18nEasyExcelBuilder.read(file, head, new I18nAnalysisListener<T>() {
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                list.addAll(super.getDataList());
                log.info("解析完毕一共{}行",list.size());
            }
        }).doRead();
        return list;

    }
}
