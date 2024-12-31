package com.haitao.excel;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.metadata.Head;
import cn.idev.excel.read.metadata.property.ExcelReadHeadProperty;
import cn.idev.excel.util.StringUtils;
import com.haitao.fastexcelconfig.PlaceholderResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国际化excel数据读取监听者
 * @param <T>
 */
@Slf4j
public abstract class I18nAnalysisListener<T> extends AnalysisEventListener<T> {

    private List<T> dataList = new ArrayList<>();

    private Class<?> head;

    private MessageSource messageSource;


    public I18nAnalysisListener<T> configure(Class<?> head, MessageSource messageSource) {
        this.head = head;
        this.messageSource = messageSource;
        return this;
    }

    protected List<T> getDataList() {
        return dataList;
    }

    /**
     * 每条数据解析后调用
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到第{}行数据:{}", context.readRowHolder().getRowIndex(), data.toString());
        dataList.add(data);
    }

    /**
     * 所有数据解析完成后调用
     * @param context
     */
    @Override
    public abstract void doAfterAllAnalysed(AnalysisContext context);


    /**
     * excel 头部国际化处理
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //获取真实文件头数据
        ExcelReadHeadProperty excelHeadPropertyData = context.readSheetHolder().excelReadHeadProperty();
        Map<Integer, Head> originHeadMapData = new ExcelReadHeadProperty(context.currentReadHolder(), head,
                context.readWorkbookHolder().getHead()).getHeadMap();
        // 下面代码就是 copy的 com.alibaba.excel.read.processor.DefaultAnalysisEventProcessor#buildHead
        //更具国际化替换excel头
        Map<Integer, Head> tmpHeadMap = new HashMap<>(originHeadMapData.size() * 4 / 3 + 1);
        for (Map.Entry<Integer, Head> entry : originHeadMapData.entrySet()) {
            Head headData = entry.getValue();

            //国际化解析数据
            List<String> headNameList = headData.getHeadNameList();
            String headPlaceholder = headNameList.get(headNameList.size() - 1);
            String headName = PlaceholderResolver.getDefaultResolver().resolveByRule(headPlaceholder,
                    x -> messageSource.getMessage(x, null, LocaleContextHolder.getLocale()));
            headNameList.set(headNameList.size() - 1, headName);

            for (Map.Entry<Integer, String> stringEntry : headMap.entrySet()) {

                if (stringEntry == null) {
                    continue;
                }
                String headString = stringEntry.getValue();
                Integer stringKey = stringEntry.getKey();

                //数据校验
                if (StringUtils.isEmpty(headString)) {
                    continue;
                }
                if (context.currentReadHolder().globalConfiguration().getAutoTrim()) {
                    headString = headString.trim();
                }

                //如果值相等添加进有效head里面
                if (headName.equals(headString)) {
                    headData.setColumnIndex(stringKey);
                    tmpHeadMap.put(stringKey, headData);
                    break;
                }
            }
        }
        excelHeadPropertyData.setHeadMap(tmpHeadMap);
    }

}
