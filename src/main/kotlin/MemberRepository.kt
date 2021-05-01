class MemberRepository {
    private val members = mutableListOf<Member>()
    private var lastId = 0

    fun joinMember(
        loginId: String,
        loginPw: String,
        name: String,
        nickName: String,
        cellphoneNo: String,
        email: String
    ): Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        members.add(Member(id, regDate, updateDate, loginId, loginPw, name, nickName, cellphoneNo, email))

        return id
    }

    fun getMemberLoginId(loginId: String): Member? {
        for (member in members) {
            if (member.loginId == loginId) {
                return member
            }
        }
        return null
    }

    fun isableMemberLoginId(loginId: String): Boolean {
        val member = getMemberLoginId(loginId)

        return member == null
    }

    fun makeTestMember() {
        for (i in 1..9) {
            joinMember("user${i}", "user${i}", "홍길동${i}", "사용자${i}", "010123123${i}", "${i}@gmail.com")
        }
    }

    fun getMemberId(memberId: Int): Member? {
        for (member in members) {
            if (member.id == memberId) {
                return member
            }
        }
        return null
    }
}