package com.haitao.fastexcelconfig;

import cn.idev.excel.read.builder.ExcelReaderBuilder;
import cn.idev.excel.write.builder.ExcelWriterBuilder;
import org.springframework.context.MessageSource;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class EasyExcelI18n {

    public static ExcelWriterBuilder write(OutputStream outputStream, Class head, MessageSource messageSource, Locale locale) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream);
        if (head == null) {
            throw new IllegalArgumentException("head must not be null");
        }
        excelWriterBuilder.head(head);
        excelWriterBuilder.registerWriteHandler(new I18nCellWriteHandler(messageSource, locale));
        return excelWriterBuilder;
    }

    public static ExcelReaderBuilder read(InputStream inputStream, Class head, I18nAnalysisListener analysisListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(inputStream);
        if (head == null || analysisListener == null) {
            throw new IllegalArgumentException("head or analysisListener must not be null");
        }
        excelReaderBuilder.head(head);
        excelReaderBuilder.registerReadListener(analysisListener);
        return excelReaderBuilder;
    }
}
