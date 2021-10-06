package btlltw.demo;



import configuration.DatabaseConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"configuration","tokenAuthen","btlltw.demo","User"})
//@EntityScan("btlltw.girl")
public class DemoApplication  extends SpringBootServletInitializer {
   
   public static void main(String[] args) {
      ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
      
      // Lấy ra bean GirlService
//      GirlService  girlService = context.getBean(GirlService.class);
////        GirlService girlService = context.getBean(GirlService.class);
//        // Lấu ra random một cô gái từ tầng service
//        Girl girl = girlService.getRandomGirl();
//         System.out.println("> Trước khi IoC Container destroy Girl");
//        ((ConfigurableApplicationContext) context).getBeanFactory().destroyBean(girl);
//        System.out.println("> Sau khi IoC Container destroy Girl");
        // In ra màn hình
//        System.out.println(girl);
//        
//        DatabaseConnector databaseConnector = (DatabaseConnector) context.getBean("mysqlConnector");
//        databaseConnector.connect();
        
       
   }    
   
   @RequestMapping(value = "/")
   public String hello() {
      return "Hello World from Tomcat";
   }
}
