fun readLineTrim() = readLine()!!.trim()
var memberLogin: Member? = null
val articleRepository = ArticleRepository()
val memberRepository = MemberRepository()
val boardRepository = BoardRepository()
fun main() {
    println("== SIMPLE SSG 시작 ==")
    val memberController = MemberController()
    val articleController = ArticleController()
    val systemController = SystemController()
    val boardController = BoardController()


    articleRepository.makeTestArticle()
    memberRepository.makeTestMember()

    while (true) {
        val prompt = if (memberLogin == null) {
            print("명령어 : ")
        } else {
            print("${memberLogin!!.nickName} :")
        }
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath) {
            "/system/exit" -> {
                systemController.exit(rq)
                break
            }
            "/article/write" -> {
                articleController.write(rq)
            }
            "/article/detail" -> {
                articleController.detail(rq)
            }
            "/article/delete" -> {
                articleController.delete(rq)
            }
            "/article/list" -> {
                articleController.list(rq)
            }
            "/article/modify" -> {
                articleController.modify(rq)
            }
            "/member/join" -> {
                memberController.join(rq)
            }
            "/member/login" -> {
                memberController.login(rq)
            }
            "/member/logout" -> {
                memberController.logout(rq)
            }
            "/board/add" -> {
                boardController.add(rq)
            }
            "/board/list" -> {
                boardController.list(rq)
            }
            "/board/delete" -> {
                boardController.delete(rq)
            }
            "/board/modify" -> {
                boardController.modify(rq)
            }

        }
    }

    println("== SIMPLE SSG 끝 ==")
}