package com.hendisantika;

import com.hendisantika.service.impl.UploadFileServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootInvoiceAppApplication implements CommandLineRunner {

    public SpringBootInvoiceAppApplication(UploadFileServiceImpl uploadFileServiceImpl, BCryptPasswordEncoder passwordEncoder) {
        this.uploadFileServiceImpl = uploadFileServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInvoiceAppApplication.class, args);
    }

    private final UploadFileServiceImpl uploadFileServiceImpl;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... arg0) throws Exception {
        uploadFileServiceImpl.deleteAll();
        uploadFileServiceImpl.init();

		/*String password = "12345";
		for(int i=0; i<2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println("bcryptPassword = " + bcryptPassword);
		}*/
    }
}
