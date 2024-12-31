package com.haitao.excel;

import com.haitao.constent.EncodeConstents;
import com.haitao.constent.HttpHeadConstents;
import com.haitao.constent.SymbolConstents;
import com.haitao.enums.FileType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 */
@Slf4j
public class FileZipUtils {
    /**
     * 对本地文件压缩
     *
     * @param inputFileName 你要压缩的文件夹(整个完整路径)
     * @param zipFileName   压缩后的文件(整个完整路径)
     */
    public static Boolean zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
        return true;
    }

    /**
     * 压缩到本地
     *
     * @param zipFileName 压缩后的文件(整个完整路径)
     * @param inputFile   输入流
     */
    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }

    /**
     * 将文件压缩到流
     *
     * @param out  输出流
     * @param file 文件包
     */
    private static void zip(ZipOutputStream out, File file, String base) throws Exception {
        if (file.isDirectory()) {
            File[] fl = file.listFiles();
            out.putNextEntry(new ZipEntry(base + SymbolConstents.VIRGULE));
            base = base.length() == 0 ? "" : base + SymbolConstents.VIRGULE;
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(file);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }


    /**
     * 多个文件压缩成压缩包并下载
     *
     * @param fileMap      输入文件缓存 Map<文件名, byte[]>
     * @param httpResponse 输出
     */
    public static void zipFiles(Map<String, byte[]> fileMap, String fileName, HttpServletResponse httpResponse) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(httpResponse.getOutputStream())) {
            //下载压缩包
            httpResponse.setContentType(HttpHeadConstents.APPLICATION_ZIP);
            try {
                httpResponse.setHeader(HttpHeadConstents.CONTENT_DISPOSITION, HttpHeadConstents.ATTACHMENT + SymbolConstents.SEMICOLON + HttpHeadConstents.FILE_NAME + URLEncoder.encode(fileName + FileType.ZIP, EncodeConstents.UTF_8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 创建 ZipEntry 对象
            for (String key : fileMap.keySet()) {
                ZipEntry zipEntry = new ZipEntry(key);
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(fileMap.get(key));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 多个文件压缩成压缩包并下载到指定流
     *
     * @param fileMap      输入文件缓存 Map<文件名, byte[]>
     * @param outputStream 输出
     */
    public static void zipFiles(Map<String, byte[]> fileMap, OutputStream outputStream) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // 创建 ZipEntry 对象
            for (String key : fileMap.keySet()) {
                ZipEntry zipEntry = new ZipEntry(key);
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(fileMap.get(key));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
