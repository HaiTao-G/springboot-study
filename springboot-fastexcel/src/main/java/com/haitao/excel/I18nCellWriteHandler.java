package com.haitao.excel;

import cn.idev.excel.metadata.Head;
import cn.idev.excel.write.handler.CellWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteTableHolder;
import com.haitao.fastexcelconfig.PlaceholderResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 国际化excel数据写入拦截
 */
public class I18nCellWriteHandler implements CellWriteHandler {

    private final MessageSource messageSource;


    public I18nCellWriteHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            List<String> originHeadNames = head.getHeadNameList();
            if (CollectionUtils.isNotEmpty(originHeadNames)) {
                List<String> newHeadNames = originHeadNames.stream().
                        map(headName ->
                                PlaceholderResolver.getDefaultResolver().resolveByRule(headName,
                                        (name) -> messageSource.getMessage(name, null, LocaleContextHolder.getLocale()))).
                        collect(Collectors.toList());
                head.setHeadNameList(newHeadNames);
            }
        }
    }


}
