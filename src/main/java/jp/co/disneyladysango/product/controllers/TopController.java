package jp.co.disneyladysango.product.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.disneyladysango.product.entities.Images;
import jp.co.disneyladysango.product.entities.Items;
import jp.co.disneyladysango.product.forms.SearchForm;
import jp.co.disneyladysango.product.services.TopService;

/**
 * トップコントローラ
 * ブラウザからのリクエストに対する入り口であるコントローラ
 *
 * @author y-wayama
 */
@Controller
@RequestMapping("/index")
public class TopController {

    /**
     * トップサービス
     */
    @Autowired
    TopService topService;

    /**
     * トップにあたるログイン画面の表示
     *
     * @param model Model
     * @return テンプレートID
     */
    @RequestMapping(value = "" , method = RequestMethod.GET)
    public String top(Model model){
    	return "/index";
    }

    /**
     * トップ画面(Redirect)
     *
     * ModelAttribute→リクエストされたデータを受けとる
     *
     * @param searchForm 検索フォーム
     * @param model Model
     * @return テンプレートID
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String index(@ModelAttribute("searchForm") SearchForm searchForm,
    										Model model, Pageable pageable) {

    	Integer id;
    	//	Page<Items> itemsPage = topService.getAllItems(pageable);
        //	model.addAttribute("page", itemsPage);
        //	model.addAttribute("itemsList", itemsPage.getContent());
        //	model.addAttribute("url", "index");
    	//もしidがnullでなければ詳細表示
    	if(searchForm.getId() != null){
    		id = searchForm.getId();
    		model.addAttribute("itemsList", topService.getItems(id));
   		}else{
   			model.addAttribute("page", topService.getItemsList(searchForm, pageable));
   			model.addAttribute("itemsList", topService.getItemsList(searchForm, pageable));
   			model.addAttribute("url", "search");
    	}

    	// タグリスト（全件）を取得
		model.addAttribute("tagsList", topService.getTagsList());

        return "index";
    }


    /**
     * 画像取得
     *
     * @param imageId 画像ID
     * @return ResponseEntity
     */
    @RequestMapping(value = "images/{imageId}", method = RequestMethod.GET)
    @ResponseBody
    public void images(@PathVariable("imageId")
    	Integer imageId, HttpServletResponse response) throws IOException {

        // 画像データ取得
        Images images = topService.getImages(imageId);
        byte[] data = new byte[]{};
        if (images != null && images.getData() != null) {
            data = images.getData();
        }

        // 画像のContentTypeを設定するためにHttpServletResponseにレスポンスの内容を自前で設定する
        response.setContentLength(data.length);
        response.setContentType(topService.getContentType(data));
        response.getOutputStream().write(data);
    }

    @RequestMapping(value="indexPage", method=RequestMethod.GET)
    public String page(Model model, Pageable pageable){
    	Page<Items> itemsPage = topService.getAllItems(pageable);
    	model.addAttribute("page", itemsPage);
    	model.addAttribute("items", itemsPage.getContent());
    	model.addAttribute("url", "/index");
    	return "/index";
    }

}

