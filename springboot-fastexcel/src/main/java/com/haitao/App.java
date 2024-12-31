package com.haitao;

import cn.idev.excel.context.AnalysisContext;
import com.haitao.entity.User;
import com.haitao.fastexcelconfig.EasyExcelI18n;
import com.haitao.fastexcelconfig.I18nAnalysisListener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

//    @Bean
//	public ApplicationRunner myRunner(MessageSource messageSource){
//		return args -> {
//			List<User> userList = new ArrayList<>();
//			userList.add(new User("王xx",10));
//			userList.add(new User("李xx",11));
//			OutputStream outputStream = new FileOutputStream("D:\\用户.xlsx");
//			Locale locale = new Locale("zh-CN");
//			EasyExcelI18n.write(outputStream, User.class,messageSource,locale).sheet(1).doWrite(userList);
//		};
//	}

//    @Bean
//    public ApplicationRunner myRunner(MessageSource messageSource) {
//        return args -> {
//
//            InputStream inputStream = new FileInputStream("D:\\用户.xlsx");
//            Locale locale = new Locale("zh-CN");
//            List<User> userList = new ArrayList<>();
//            EasyExcelI18n.read(inputStream, User.class, new I18nAnalysisListener<User>(messageSource, locale, User.class) {
//                @Override
//                public void invoke(User user, AnalysisContext context) {
//                    userList.add(user);
//                }
//
//                @Override
//                public void doAfterAllAnalysed(AnalysisContext context) {
//
//                }
//            }).sheet().doRead();
//
//            System.out.println(userList);
//        };
//    }
}
