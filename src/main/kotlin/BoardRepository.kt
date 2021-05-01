class BoardRepository {
    private var lastId = 2
    private val boards = mutableListOf<Board>(
        Board(1, Util.getNowDateStr(), Util.getNowDateStr(), "공지", "notice", 1),
        Board(2, Util.getNowDateStr(), Util.getNowDateStr(), "자유", "free", 1)
    )

    fun isableBoardName(name: String): Boolean{
        val board = getBoardByName(name)

        return board == null
    }

    private fun getBoardByName(name: String): Board? {
        for(board in boards){
            if(board.name == name){
                return board
            }
        }
        return null
    }

    fun isableBoardCode(code: String): Boolean {
        val board = getBoardByCode(code)

        return board == null
    }

    private fun getBoardByCode(code: String): Board? {
        for(board in boards){
            if(board.code == code){
                return board
            }
        }
        return null
    }

    fun boardAdd(name: String, code: String, memberId: Int): Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        boards.add(Board(id, regDate, updateDate, name, code, memberId))

        return id
    }

    fun getBoards(): List<Board> {
        return boards
    }

    fun getBoardById(num: Int): Board?{
        for(board in boards){
            if(board.id == num){
                return board
            }
        }
        return null
    }

    fun boardRemove(boardToDelete: Board) {
        boards.remove(boardToDelete)
    }

    fun boardModify(boardToModify: Board, name: String, code :String) {
        boardToModify.name = name
        boardToModify.code = code
        boardToModify.updateDate = Util.getNowDateStr()
    }

    fun getFilteredBoards(searchKeyword: String, page: Int, pageCount: Int): List<Board>{
        val filtered1Boards = getSearchKeywordFilteredBoards(boards, searchKeyword)
        val filtered2Boards = getPageFilteredBoard(filtered1Boards, page, pageCount)

        return filtered2Boards
    }


    private fun getPageFilteredBoard(filtered1Boards: List<Board>, page: Int, pageCount: Int): List<Board> {
        val filteredBoards = mutableListOf<Board>()

        val offsetCount = (page -1) * pageCount
        val startIndex = offsetCount
        var endIndex = startIndex + pageCount

        if(endIndex > filtered1Boards.size){
            endIndex = filtered1Boards.size
        }
        for(i in startIndex until endIndex){
            filteredBoards.add(filtered1Boards[i])
        }
        return filteredBoards
    }

    private fun getSearchKeywordFilteredBoards(boards: MutableList<Board>, searchKeyword: String): List<Board> {
        val filteredBoards = mutableListOf<Board>()

        for(board in boards){
            if(board.name.contains(searchKeyword)){
                filteredBoards.add(board)
            }
        }
        return filteredBoards
    }
}