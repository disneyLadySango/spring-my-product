package jp.co.disneyladysango.product.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.disneyladysango.product.forms.AccountForm;
import jp.co.disneyladysango.product.services.AccountService;

/**
 * アカウント作成時のコントローラを作成
 * @author y-wayama
 *
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {


	@Autowired
	AccountService accountService;

	/**
	 * setupForm
	 * アカウント登録時のフォームの値を受け取るクラス
	 * あらかじめsetupForm()を使ってインスタンス化しておき、
	 * AccountFormで設定したフィールドをフォームで利用できる
	 * @return
	 */
	@ModelAttribute
	public AccountForm setupForm(){
		return new AccountForm();
	}

	/**
	 * accountフォルダ内にあるアカウントフォームの呼び出し
	 * （登録フォームの呼び出しを実施）
	 * @return
	 */
	@RequestMapping(value = "/acNew", method = RequestMethod.GET)
	public String acNew(Model model){
		AccountForm accountForm = new AccountForm();
		model.addAttribute("accountForm", accountForm);
		return "account/accountForm";
	}
	/**
	 * アカウント作成時の確認画面を呼び出します
	 * @param accountForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/acConfirm", method = RequestMethod.POST)
	public String acConfirm(@ModelAttribute("accountForm")
							@Valid AccountForm accountForm, BindingResult bindingResult, Model model)
							throws IOException{
		//バリデーションチェックを実施
		if(bindingResult.hasErrors()){
			return "account/accountForm";
		}

		//確認画面へ返します
		return "account/confirm";
	}

	/**
	 * アカウント情報を保存します
	 * @param accountForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/acCreate", method = RequestMethod.POST)
	public String acCreate(@ModelAttribute("accountForm")AccountForm accountForm,Model model){

		//アカウント情報の保存 パスワードのエンコードが必要なのでパスも渡す
		accountService.saveAccount(accountForm);
		//完了しましたの画面を表示
		return "account/complete";
	}


}
