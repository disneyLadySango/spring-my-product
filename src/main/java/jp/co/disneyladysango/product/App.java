package jp.co.disneyladysango.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * メインアプリケーションクラス
 *
 * @author k-tsuda
 */
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "jp.co.jwebgate.product")
@EntityScan(basePackages = "jp.co.jwebgate.product.entities")
@EnableJpaRepositories(basePackages = "jp.co.jwebgate.product.repositories")
public class App {

    /**
     * メインメソッド
     *
     * @param args 起動パラメータ
     */
    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
