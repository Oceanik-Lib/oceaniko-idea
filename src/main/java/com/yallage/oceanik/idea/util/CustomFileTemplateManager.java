package com.yallage.oceanik.idea.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class CustomFileTemplateManager {

    // 原味模板 适用于文本文件 类文本
    public byte[] getTemplate(String fileName){
        return getTemplateFromJar("template/" +fileName);
    }

    // 替换字符的模板 适用于文本文件 类文本
    public byte[] getTemplate(String fileName, Map<String, String> replace) {
        System.out.println("begin");
        byte[] bytes = getTemplateFromJar("template/" +fileName);
        // 循环遍历map和读取的bytes
        for (Map.Entry<String, String> entry : replace.entrySet()){
            bytes = replaceBytes(bytes
                    ,entry.getKey().getBytes(StandardCharsets.UTF_8)
                    ,entry.getValue().getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("done");
        return bytes;
    }

    /**
     * 通过ClassLoader拿到Jar包资源流 获取的是文本 类文本资源
     *
     * @param path 在jar包中的路径 相对于jar包根目录
     * @return 保持数据原汁原味 返回字节数组
     */
    private byte[] getTemplateFromJar(String path) {
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream( path);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = Objects.requireNonNull(is).read(buffer)) > -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    /**
     * 抄的 先用着 效率也不是很低吧 （bushi）
     * @param source 原始字节集
     * @param oldBytes 要被替换的字节集片段
     * @param newBytes 要替换成的目标片段
     * @return 字节数组
     */
    private byte[] replaceBytes(byte[] source, byte[] oldBytes, byte[] newBytes){
        byte[] result = new byte[source.length - newBytes.length + oldBytes.length];
        index: for (int i = 0; i < source.length; i++){
            result[i] = source[i];
            if (i >= oldBytes.length - 1){
                // 当前游标的长度满足于oldByte的长度比较值, 开始比较实际内容
                for (int j = 0; j < oldBytes.length; j++){
                    if (source[i - (oldBytes.length - 1) + j] != oldBytes[j]){
                        continue index;
                    }
                }
                // 成功匹配oldBytes, 开始替换newBytes
                for (int j = 0; j < newBytes.length; j++){
                    result[i - (oldBytes.length - 1) + j] = newBytes[j];
                }
                i += newBytes.length - oldBytes.length;
            }
        }
        return result;
    }

}
