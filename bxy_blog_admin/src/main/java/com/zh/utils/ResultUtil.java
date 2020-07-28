package com.zh.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Beloved
 * @date 2020/6/22 15:33
 */
@Slf4j
public class ResultUtil {

    /**
     * 使用response输出JSON
     *
     * @param response
     */
    public static void out(ServletResponse response, String result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(result);
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
