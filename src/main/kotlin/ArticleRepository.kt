class ArticleRepository {
    private val articles = mutableListOf<Article>()
    private var lastId = 0

    fun articleDetail(id: Int): Article? {
        for (article in articles) {
            if (article.id == id) {
                return article
            }
        }
        return null
    }

    fun addArticle(boardId :Int, memberId: Int, title: String, body: String): Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        articles.add(Article(id, regDate, updateDate, title, body, memberId, boardId))

        return id
    }

    fun makeTestArticle() {
        for (i in 1..25) {
            addArticle(2,i % 9 + 1, "제목${i}", "내용${i}")
        }
    }

    fun deleteArticle(article: Article) {
        articles.remove(article)
    }

    fun filteredArticles(searchKeyword: String, page: Int, pageCount: Int): List<Article> {
        val filtered1Articles = getSearchKeywordfilteredArticles(articles, searchKeyword)
        val filtered2Articles = getPagefilteredArticles(filtered1Articles, page, pageCount)

        return filtered2Articles
    }

    private fun getPagefilteredArticles(articles: List<Article>, page: Int, pageCount: Int): List<Article> {
        val filteredArticles = mutableListOf<Article>()

        val fromIndex = (page - 1) * pageCount

        val startIndex = articles.lastIndex - fromIndex
        var endIndex = startIndex - pageCount + 1

        if (endIndex < 0) {
            endIndex = 0
        }
        for (i in startIndex downTo endIndex) {
            filteredArticles.add(articles[i])
        }
        return filteredArticles
    }

    private fun getSearchKeywordfilteredArticles(articles: MutableList<Article>, searchKeyword: String): List<Article> {
        val filteredArticles = mutableListOf<Article>()
        for (article in articles) {
            if (article.title.contains(searchKeyword)) {
                filteredArticles.add(article)
            }
        }
        return filteredArticles
    }

    fun updateArticle(articleModify: Article, title: String, body: String) {
        articleModify.title = title
        articleModify.body = body
        articleModify.updateDate = Util.getNowDateStr()
    }

    fun filtered2Articles(filtered1Articles: List<Article>, boardId: Int): List<Article> {
        val filteredArticles = mutableListOf<Article>()
        for(article in filtered1Articles){
            if(article.boardId == boardId){
                filteredArticles.add(article)
            }
        }
        return filteredArticles
    }
}