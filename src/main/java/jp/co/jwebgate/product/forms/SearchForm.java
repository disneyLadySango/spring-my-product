package jp.co.jwebgate.product.forms;

/**
 * 検索用のフォーム
 * キーワード検索
 *
 * @author y-wayama
 */


public class SearchForm {

	//ID（詳細を呼び出す際に使用）
	private Integer id;

	//キーワード
	private String keyword;

	//検索条件
	private String conditions;

	//最小金額
	//あとで入力異常チェック機能を実装
	private Integer minPrice;

	//最大金額
	//あとで入力異常チェック機能を実装
	private Integer maxPrice;

	/**
     * 検索用のタグリスト
     * タグは複数ある為、全てのタグを入れる入れ物
     */
    private String[] tags;

    //ソート用
    /**
     * ソート条件
     */
    private String sort;

    //降順昇順の指定
    /**
     * 昇順降順
     * 降順DESC→false
     * 昇順ASC→true
     */
    private boolean sortSc;


	/**
	 * キーワードを取得します
	 *
	 * @return キーワード
	 */

	public String getKeyword() {
		return keyword;
	}

	/**
	 * キーワードを設定します
	 *
	 * @param keyword キーワード
	 */

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 最小金額を取得します
	 *
	 * @return 最小金額
	 */
	public Integer getMinPrice() {
		return minPrice;
	}

	/**
	 * 最小金額を設定します
	 *
	 * @param minPrice 最小金額
	 */

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * 最大金額を取得します
	 *
	 * @return 最大金額
	 */

	public Integer getMaxPrice() {
		return maxPrice;
	}

	/**
	 * 最大金額を設定します
	 *
	 * @param maxPrice 最大金額
	 */

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * タグの配列を取得します
	 * @return タグ配列
	 */
	public String[] getTags() {
		return tags;
	}
	/**
	 * タグの配列を設定します
	 * @param tags タグ配列
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * ソート条件を取得します
	 * @return ソート条件
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * ソート条件を設定します
	 * @param sort ソート条件
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 昇順降順を取得します
	 * @return 昇順降順
	 */
	public boolean isSortSc() {
		return sortSc;
	}

	/**
	 * 昇順降順を設定します
	 * @param sortSc 昇順降順
	 */
	public void setSortSc(boolean sortSc) {
		this.sortSc = sortSc;
	}

	/**
	 * IDを取得します
	 * @return ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * IDを設定します
	 * @param id ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 検索条件を取得します
	 * @return 検索条件
	 */
	public String getConditions() {
		return conditions;
	}

	/**
	 * 検索条件を設定します
	 * @param conditions 検索条件
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}
