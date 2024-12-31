package com.haitao.excel;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.read.builder.ExcelReaderSheetBuilder;
import cn.idev.excel.write.builder.ExcelWriterSheetBuilder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Component
public class I18nEasyExcelBuilder {

    private static MessageSource messageSource;

    @Autowired
    public I18nEasyExcelBuilder(MessageSource messageSource) {
        I18nEasyExcelBuilder.messageSource = messageSource;
    }

    /**
     * Excel写本地文件
     *
     * @param head     表头
     * @param pathName 路径名
     */
    public static ExcelWriterSheetBuilder writeToLocal(Class<?> head, String pathName) {
        return EasyExcel.write(pathName).head(head).registerWriteHandler(new I18nCellWriteHandler(messageSource)).sheet();
    }

    /**
     * Excel写
     *
     * @param outputStream 输出流
     * @param head         表头
     * @return
     */
    public static ExcelWriterSheetBuilder write(OutputStream outputStream, @NonNull Class head) {
        return EasyExcel.write(outputStream, head).registerWriteHandler(new I18nCellWriteHandler(messageSource)).sheet();
    }

    /**
     * Excel读
     *
     * @param inputStream      输入流
     * @param head             表头
     * @param analysisListener 读取监听
     */
    public static <T> ExcelReaderSheetBuilder read(InputStream inputStream, @NonNull Class head, @NonNull I18nAnalysisListener<T> analysisListener) {
        return EasyExcel.read(inputStream, head, analysisListener.configure(head, messageSource)).sheet();
    }

}
