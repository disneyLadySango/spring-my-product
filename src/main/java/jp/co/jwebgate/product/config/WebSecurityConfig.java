package jp.co.jwebgate.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.co.jwebgate.product.handller.SampleAuthenticationFailureHandler;
import jp.co.jwebgate.product.services.UserServiceImpl;

/**
 * WebSecurityコンフィグ
 * @author y-wayama
 *
 * @Configuration
 * クラス宣言の前に記述を行う
 * このクラスがBeanの設定を行うものであることを示している
 * Bean設定クラスには常にこれをつける
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


	@Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/sass/**");
    }

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{

		//認可の設定を行う、以下のアクセスは
		httpSecurity.authorizeRequests()
					//antMatvchersは指定の部分「/login」は認証なしでログイン可能にする
					//premitAllで全ユーザー許可
					//※hasrollで指定の権限を持たないと不可にするadminとか…
					.antMatchers("/login" ,"/account/**").permitAll()
					.antMatchers("/items/**").hasRole("ADMIN")
					// その他のアクセスは
					.anyRequest()
					// 禁止にします
					.authenticated();

		//ログイン設定を行う
		httpSecurity.formLogin()
					//認証処理用のページを指定
					.loginProcessingUrl("/login")
					//ログインページのパス
					.loginPage("/login")
					// 認証失敗時に選択されるハンドラクラス(エラーIDの表示)
					.failureHandler(new SampleAuthenticationFailureHandler())
					//ユーザー名のパラメータ
					.usernameParameter("username")
					//パスワードのパラメータ
					.passwordParameter("password")
					//認証成功時の遷移先
					.defaultSuccessUrl("/login/complete" ,true)
					.and();
		//ログアウトの設定を行う
		httpSecurity.logout()
					//ログアウト処理時のパス
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
					//ログアウト完了後のパス
					.logoutSuccessUrl("/login");

	}
	 @Configuration
	    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
	        @Autowired
	        UserServiceImpl userDetailsService;

	        @Override
	        public void init(AuthenticationManagerBuilder auth) throws Exception {
	            // 認証するユーザーを設定する
	            auth.userDetailsService(userDetailsService);
	        }
	    }

}
