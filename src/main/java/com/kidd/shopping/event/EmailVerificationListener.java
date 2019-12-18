package com.kidd.shopping.event;

import com.kidd.shopping.auth.entity.User;
import com.kidd.shopping.utils.ApplicationConstant;
import com.kidd.shopping.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailVerificationListener implements ApplicationListener<EmailVerificationEvent> {
    @Autowired
    private EmailVerificationTokenService emailVerificationTokenService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(EmailVerificationEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(EmailVerificationEvent event) {
        User user = event.getUser();
//        String token = emailVerificationTokenService.createEmailVerificationToken(user).getToken();

        String token = "";
        SimpleMailMessage email = null;
        switch (user.getRole()) {
            case Constant.STUDENT: {
                email = new SimpleMailMessage();
                email.setTo(user.getUsername());
                email.setSubject("Xác nhận đăng ký tài khoản Student_Room_Renting");
                String url = "http://" + ApplicationConstant.HOST + "/api/auth/registration-confirm/" + token;
                email.setText("Xin chào,\n" +
                        "Đây là email xác nhận tài khoản Student_Room_Renting. Vui lòng click vào đường dẫn bên dưới để tiến hành xác minh tài khoản của bạn. Email này có hiệu lực trong vòng 24 tiếng tính từ thời điểm được gửi đến\n" +
                        url +
                        "\n\nTrân trọng!\n" +
                        "Kidd\n");
            }
            break;
            case Constant.OWNER:{
                email = new SimpleMailMessage();
                email.setTo(user.getUsername());
                email.setSubject("Xác nhận đăng ký tài khoản Student_Room_Renting");
                String url = "http://" + ApplicationConstant.HOST + "/api/auth/registration-confirm/" + token;
                email.setText("Xin chào,\n" +
                        "Đây là email xác nhận tài khoản Student_Room_Renting. Vui lòng click vào đường dẫn bên dưới để tiến hành xác minh tài khoản của bạn. Email này có hiệu lực trong vòng 24 tiếng tính từ thời điểm được gửi đến\n" +
                        url +
                        "\n\nTrân trọng!\n" +
                        "Kidd\n");
            }
            default: {
                break;
            }
        }
        if(email != null) {
            javaMailSender.send(email);
        }
    }
}
