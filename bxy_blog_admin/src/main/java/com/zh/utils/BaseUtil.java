package com.zh.utils;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Beloved
 * @date 2020/7/10 17:44
 */
public class BaseUtil {

    /**
     * base64转inputStream
     * @param dataURL base64字符串
     * @return
     * dataURL 的格式为 “data:image/png;base64,****”,
     * 逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
     */
    public static InputStream BaseToInputStream(String dataURL){
        dataURL = dataURL.split(",")[1];
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(dataURL);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stream;
    }

}
