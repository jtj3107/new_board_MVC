class BoardController {
    fun add(rq: Rq) {
        if(memberLogin == null){
            println("로그인후 사용 가능 합니다.")
            return
        }
        println("게시판 이름을 정해주세요 : ")
        val name = readLineTrim()

        val isableBoardName = boardRepository.isableBoardName(name)

        if(!isableBoardName){
            println("${name}라는 이름은 이미 사용중 입니다.")
            return
        }
        println("게시판 코드를 정해주세요 : ")
        val code = readLineTrim()
        val isableBoardCode = boardRepository.isableBoardCode(code)

        if(!isableBoardCode){
            println("${code}라는 코드는 이미 사용중 입니다.")
            return
        }
        val id = boardRepository.boardAdd(name, code, memberLogin!!.id)

        println("${id}번 게시판이 생성되었습니다.")

    }

    fun list(rq: Rq) {
        val boardId = rq.getIntParam("boardId", 0)
        val page = rq.getIntParam("page", 1)
        val searchKeyword = rq.getStringParam("searchKeyword", "")

        val filteredBoards = boardRepository.getFilteredBoards(searchKeyword, page, 10)

        if(boardId != 0){
            for(board in filteredBoards){
                if(board.id == boardId) {
                    println("번호 : ${board.id} / 게시판 이름 : ${board.name} / 게시판 코드 ${board.code} / 생성날짜 ${board.regDate}")
                }
            }
        }else {
            for(board in filteredBoards){
                println("번호 : ${board.id} / 게시판 이름 : ${board.name} / 게시판 코드 ${board.code} / 생성날짜 ${board.regDate}")
            }
        }

    }

    fun delete(rq: Rq) {
        if(memberLogin == null){
            println("로그인후 사용 가능 합니다.")
            return
        }
        val id = rq.getIntParam("id", 0)

        if(id == 0){
            println("id를 입력해주세요")
            return
        }
        val boardToDelete = boardRepository.getBoardById(id)

        if(boardToDelete == null){
            println("${id}번 게시판은 존재하지 않습니다.")
            return
        }
        if(boardToDelete.memberId != memberLogin!!.id){
            println("해당 게시판 생성자만 삭제 가능합니다.")
            return
        }
        boardRepository.boardRemove(boardToDelete)

        println("${id}번 게시판이 삭제 되었습니다.")

    }

    fun modify(rq: Rq) {
        if(memberLogin == null){
            println("로그인후 사용 가능 합니다.")
            return
        }
        val id = rq.getIntParam("id", 0)

        if(id == 0){
            println("id를 입력해주세요")
            return
        }
        val boardToModify = boardRepository.getBoardById(id)

        if(boardToModify == null){
            println("${id}번 게시판은 존재하지 않습니다.")
            return
        }
        print("새 게시판 이름 : ")
        val name = readLineTrim()
        val isableBoardName = boardRepository.isableBoardName(name)

        if(!isableBoardName){
            println("${name}라는 이름은 이미 사용중 입니다.")
            return
        }
        print("새 게시판 코드 : ")
        val code = readLineTrim()
        val isableBoardCode = boardRepository.isableBoardCode(code)

        if(!isableBoardCode){
            println("${code}라는 코드는 이미 사용중 입니다.")
            return
        }
        if(boardToModify.memberId != memberLogin!!.id){
            println("해당 게시판 생성자만 수정 가능합니다.")
            return
        }

        boardRepository.boardModify(boardToModify, name, code)

        println("${id}번 게시판 수정 되었습니다.")
    }

}