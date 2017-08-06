package com.wyp.boot.earthlyfisher.controller;

import com.wyp.boot.earthlyfisher.common.CommonConstant;
import com.wyp.boot.earthlyfisher.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class EntryController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null != session) {
            User currentUser = (User) session.getAttribute(CommonConstant.SESSION_CURRENT_USER);
            if (currentUser != null) {
                return "/html/main.html";
            }
        }
        return "/login.html";
    }


    /**
     * HTTP Streaming Directly To The OutputStream
     *
     * @return
     */
    @RequestMapping("/download")
    public StreamingResponseBody handle() {

        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                outputStream.write("中文2424sfs".getBytes());
                outputStream.close();
            }
        };
    }
}
