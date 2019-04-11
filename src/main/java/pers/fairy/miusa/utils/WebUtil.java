package pers.fairy.miusa.utils;

import com.alibaba.fastjson.JSON;
import pers.fairy.miusa.common.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/11 21:13
 */
public class WebUtil {

    public static void modifyResponse(HttpServletResponse response, Result result) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(result));
        writer.flush();
    }

    private WebUtil() {
    }
}
