package wiki.jixing.myblog.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

@SpringBootTest
@Disabled
public class EmailTest {
    @Resource
    private JavaMailSender mailSender;

    @Test
    public void email() {
        for (int i = 0; i < 10; i++) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jixingiff@163.com");
            message.setTo("1185225165@qq.com");
            message.setSubject("From jixing'blog");
            String checkCode = RandomCheckCode.getCheckCode();
            message.setText("您的验证码为: " + checkCode + ", 有效时间60秒");
            try {
                mailSender.send(message);
//            return new Result(1, "success", toEmail);
            } catch (MailException e) {
                e.printStackTrace();
//            return new Result(0, "error", null);
            }
        }
    }
}
