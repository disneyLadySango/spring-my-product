package jp.co.jwebgate.product.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.jwebgate.product.entities.Images;
import jp.co.jwebgate.product.entities.Items;
import jp.co.jwebgate.product.entities.ItemsTags;
import jp.co.jwebgate.product.forms.ItemsForm;
import jp.co.jwebgate.product.services.TopService;

/**
 * フォーム受け取り後、indexへ送り返すコントローラ
 *
 * @author y-wayama
 *
 */

@Controller
@RequestMapping("/items")
public class ItemsFormController {

	/**
	 * トップサービス
	 */
	@Autowired
	TopService topService;

    /**
     * Items修正画面の用意
     * 新規＆修正ボタンを押す→修正＆新規画面
     *
     * @RequestParam→URLに含まれるクエリパラメータや、
     * メッセージボディーに含まれるポストパラメータを受け取ることができる
     *
     * なぜ@modelAttributeではないのか
     * →ページをリクエストされた時点ではデータはなく
     * ページをeditページをリクエストされたのみ。
     * リクエストを受け取り条件式で分岐させる
     * 新規ならnew、修正ならフォームデータをリダイレクトする。
     * 分岐の条件はリクエストで@{edit?id=new}を受け取ったかどうか
     *
     * なぜIDはStringで指定したのか？
     * id=newで受け取っているので文字列となる。
     * そのため==ではなく.equals
     *
     * Integer.valueOfはStringをInteger型に戻すメソッド
     *
     * @param id ID
     * @param model Model
     * @param id レコードID
     * @return テンプレートID
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("id") String id, Model model) {
    	//Itemsフォームの定義
    	ItemsForm itemsForm;
    	//もしidがnewでない(!はNOT)ならば
    	if(!"new".equals(id)){
    		//修正画面を呼び出しフォームに値を用意
    		//idをIntegerに直しアイテムを取り出しフォームに入れる
    		Items items = topService.getItems(Integer.valueOf(id));
    		itemsForm = new ItemsForm();
    		itemsForm.setId(items.getId());
    		itemsForm.setName(items.getName());
    		itemsForm.setDescription(items.getDescription());
    		itemsForm.setPrice(items.getPrice());
    		//画像リストを用意
    		List<Images> imageList = items.getImageList();
            //もし画像が登録されていれば現在の画像をいれる
    		if (imageList != null && imageList.size() > 0) {
                itemsForm.setImageId(imageList.get(0).getId());
            }
    		//タグリストを用意
    		List<String> tags = new ArrayList<String>();
    		/**
    		 * .addで要素の追加
    		 * アイテムタグの数だけIDを参照にタグを用意して
    		 * Stringとして追加
    		 */
    		for (ItemsTags itemsTags : items.getItemsTagsList()) {
                tags.add(String.valueOf(itemsTags.getTags().getId()));
            }
    		//タグを設定
    		//toArrayでリストから配列に変換する
            itemsForm.setTags(tags.toArray(new String[0]));
    	}else{
    		//newであれば新規作成画面のために初期化
    		itemsForm = new ItemsForm();
    	}

        // IDからレコード情報を取得
        model.addAttribute("itemsForm", itemsForm);
        // タグ情報の取得
        model.addAttribute("tagsList", topService.getTagsList());

        return "items/edit";
    }


    /**
     * 送信確認画面への遷移
     * 修正新規画面→確認画面
     * @Valid
     *
     * @param itemsForm ItemsForm
     * @param model Model
     * @return confirm
     */

    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    public String confirm(@ModelAttribute("itemsForm")
    		@Valid ItemsForm itemsForm, BindingResult bindingResult,
    		Model model)throws IOException{

    	 // バリデーションチェック
    	//bindingResult.hasErrors()で入力チェックエラーが存在するかを判断しています
        if (bindingResult.hasErrors()) {
        	// 元のeditに戻すのでタグリストを再取得
            model.addAttribute("tagsList", topService.getTagsList());
            return "items/edit";
        }

        // 画像データの変換(画像データ受信時)
        if (itemsForm.getImageData() != null && itemsForm.getImageData().getSize() > 0) {
            itemsForm.setImageDataBase64
            	(topService.convertImageDataToBase64(itemsForm.getImageData()));
            itemsForm.setImageDataUriScheme(topService.convertImageDataToDataUriScheme(
                itemsForm.getImageData()));
        }
        // タグリストの取得
        model.addAttribute("tagsList", topService.getTagsList());

       	return "items/confirm";
    }

    /**
     * Items更新処理
     * 確認画面→完了画面
     * ModelAttribute→リクエストされたデータを受けとる
     *
     * @param itemsForm Form
     * @param model Model
     * @return テンプレートID
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("itemsForm")  ItemsForm itemsForm, Model model) {

        // レコード保存
        topService.saveItems(itemsForm);

        return "items/complete";
    }


    /**
     * Items削除処理→論理削除("remove")
     * ModelAttribute→リクエストされたデータを受けとる
     *
     * @param itemsForm Form
     * @param model Model
     * @return テンプレートID
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String remove(@ModelAttribute("itemsForm") ItemsForm itemsForm, Model model){

    	//トップサービスの削除機能を呼び出し
    	topService.removeItems(itemsForm);
    	return "redirect:/index/search";
    }



}
