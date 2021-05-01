class ArticleController {
    fun modify(rq: Rq) {
        if (memberLogin == null) {
            println("로그인후 사용 가능 합니다.")
            return
        }
        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
            return
        }
        val articleModify = articleRepository.articleDetail(id)
        if (articleModify == null) {
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }
        if (articleModify.memberId != memberLogin!!.id) {
            println("${id}번 게시물 작성자만 삭제 가능합니다.")
            return
        }

        print("새 제목 : ")
        val title = readLineTrim()
        print("새 내용 : ")
        val body = readLineTrim()

        articleRepository.updateArticle(articleModify, title, body)

        println("${id}번 게시물이 수정 되었스비다.")

    }

    fun write(rq: Rq) {
        if (memberLogin == null) {
            println("로그인후 사용 가능 합니다.")
            return
        }
        println("게시판을 선택 해주세요 ")
        val boards = boardRepository.getBoards()
        for(board in boards){
            println("번호 : ${board.id} 제목 : ${board.name}")
        }
        val num = readLineTrim().toInt()
        val board = boardRepository.getBoardById(num)
        if(board == null){
            println("없는 게시판 입니다.")
            return
        }
        println("${board.name}게시판이 선택 되었습니다.")
        print("제목 : ")
        val title = readLineTrim()
        print("내용 : ")
        val body = readLineTrim()

        val id = articleRepository.addArticle(board.id, memberLogin!!.id, title, body)

        println("${id}번 게시물이 등록 되었습니다.")
    }

    fun detail(rq: Rq) {
        if (memberLogin == null) {
            println("로그인후 사용 가능 합니다.")
            return
        }
        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
            return
        }
        val articleDetail = articleRepository.articleDetail(id)
        if (articleDetail == null) {
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }
        if (articleDetail.memberId != memberLogin!!.id) {
            println("${id}번 게시물 작성자만 삭제 가능합니다.")
            return
        }

        println("번호 : ${articleDetail.id}")
        println("제목 : ${articleDetail.title}")
        println("내용 : ${articleDetail.body}")
        println("등록날짜 : ${articleDetail.regDate}")
        println("갱신날짜 : ${articleDetail.updateDate}")
    }

    fun list(rq: Rq) {
        val page = rq.getIntParam("page", 1)
        val searchKeyword = rq.getStringParam("searchKeyword", "")
        val boardId = rq.getIntParam("boardId", 0)

        val filtered1Articles = articleRepository.filteredArticles(searchKeyword, page, 10)
        val filtered2Articles = articleRepository.filtered2Articles(filtered1Articles, boardId)

        for (article in filtered2Articles) {
            val writes = memberRepository.getMemberId(article.memberId)!!
            val boardId = boardRepository.getBoardById(article.boardId)!!
            println("게사판 : ${boardId.name}/ 번호 : ${article.id} / 제목 : ${article.title} / 등록날짜 : ${article.regDate} / 작성자 : ${writes.nickName}")
        }
    }
    fun delete(rq: Rq) {
        if (memberLogin == null) {
            println("로그인후 사용 가능 합니다.")
            return
        }
        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
            return
        }
        val articleDelete = articleRepository.articleDetail(id)
        if (articleDelete == null) {
            println("${id}번 게시물은 존재하지 않습니다.")
            return
        }
        if (articleDelete.memberId != memberLogin!!.id) {
            println("${id}번 게시물 작성자만 삭제 가능합니다.")
            return
        }
        articleRepository.deleteArticle(articleDelete)

        println("${id}번 게시물이 삭제 되었습니다.")
    }

}