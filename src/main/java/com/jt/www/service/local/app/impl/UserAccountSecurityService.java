package com.jt.www.service.local.app.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserAccountSecurityService {
    public void imgKaptchaService(HttpServletRequest request, HttpServletResponse response);
}
