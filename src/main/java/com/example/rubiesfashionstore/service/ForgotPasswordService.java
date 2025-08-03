package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.form.user.ForgotPasswordForm;
import com.example.rubiesfashionstore.form.user.ResetPasswordForm;

public interface ForgotPasswordService {
    void sendOtp(ForgotPasswordForm form);

    void resetPassword(ResetPasswordForm form);
}
