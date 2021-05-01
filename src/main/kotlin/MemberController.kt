class MemberController{
    fun join(rq: Rq) {
        print("사용할 아이디 입력 : ")
        val loginId = readLineTrim()
        val isableMemberLoginId = memberRepository.isableMemberLoginId(loginId)
        if (!isableMemberLoginId) {
            println("${loginId}는 중복된 아이디입니다.")
            return
        }
        print("사용할 비밀번호 입력 : ")
        val loginPw = readLineTrim()
        print("사용할 이름 입력 : ")
        val name = readLineTrim()
        print("사용할 닉네임 입력 : ")
        val nickName = readLineTrim()
        print("휴대전화번호 입력 : ")
        val cellphoneNo = readLineTrim()
        print("이메일 입력 : ")
        val email = readLineTrim()

        val id = memberRepository.joinMember(loginId, loginPw, name, nickName, cellphoneNo, email)

        println("${id}번 회원으로 등록 되었습니다.")
    }

    fun login(rq: Rq) {
        if (memberLogin != null) {
            println("이미 로그인 하셨습니다.")
            return
        }
        print("아이디를 입력하세요 : ")
        val loginId = readLineTrim()

        val member = memberRepository.getMemberLoginId(loginId)
        if (member == null) {
            println("${loginId}는 존재 하지않는 아이디입니다.")
            return
        }
        print("비밀번호를 입력하세요 : ")
        val loginPw = readLineTrim()
        if (member.loginPw != loginPw) {
            println("비밀번호가 일치 하지않습니다.")
            return
        }
        println("${member.nickName}님 환영합니다.")

        memberLogin = member
    }

    fun logout(rq: Rq) {
        println("로그아웃 합니다.")
        memberLogin = null
    }

}