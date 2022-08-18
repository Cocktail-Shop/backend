function checkName(){
    const text = document.getElementById('name').value;
    const reg =  /^[가-힣{1,10}]+$/;
    const result=reg.test(text)
    if (!result) {
        alert('이름은 한글로 최대 10글자입니다.');
    }
    return result
}

function checkId(){
    const text = document.getElementById('user-id').value;
    const reg =  /^[a-zA-Z0-9{1,20}]+$/;
    const result=reg.test(text)
    if (!result) {
        alert('아이디는 영문, 숫자로 최대 20글자까지 입니다.');
    }
    return result
}


function checkPw(){
    const pw = document.getElementById('pw').value;
    const pw_check = document.getElementById('pw-check').value;
    const reg =  /^[a-zA-z0-9]{8,16}$/;
    if(reg.test(pw)===false){
        alert('비밀번호는 영문, 숫자로 8~16글자입니다.');
        return false
    }else {
        if(pw!==pw_check){
            alert('비밀번호를 확인해주세요.');
            return false
        }else{
            return true
        }
    }
}

function checkChbox(){
    const chbox1=document.getElementById('chbox1').checked
    const chbox2=document.getElementById('chbox2').checked

    if(!(chbox1&&chbox2)){
        alert("이용약관에 동의해주세요")
    }

    return chbox1&&chbox2

}

function checkTel() {
    const text = document.getElementById('tel').value;
    const reg = /^01([0|1|6|7|8|9])([0-9]{7,8})$/;
    const result=reg.test(text)
    if (!result) {
        alert('전화번호는 -를 빼고,숫자로 입력해주세요');
    }
    return result

}
function checkLoginPw(){
    const pw = document.getElementById('pw').value;
    const reg =  /^[a-zA-z0-9]{8,16}$/;
    const result=reg.test(pw)
    if(!result){
        alert('비밀번호가 잘못되었습니다.');
    }
    return result
}



function checkAuthNum(){
    const text = document.getElementById('auth-num').value;

    if(document.getElementById('isAuth').value==="false"){
        alert('이메일 인증을 진행해주세요.')
        return false
    }else{
        return true
    }
}

function checkPrePw(){
    const pw = document.getElementById('pre-pw').value;
    const reg =  /^[a-zA-z0-9]{8,16}$/;
    const result=reg.test(pw)
    if(!result){
        alert('비밀번호가 잘못되었습니다.');
    }
    return result
}

function checkAddr(){
    const addr=document.getElementById('addr').value;
    const subAddr = document.getElementById('sub-addr').value;
    const reg =  /[^ㄱ-ㅎㅏ-ㅣ{1,}]+$/;
    const result=reg.test(subAddr)
    if(addr===""){
        alert('주소를 입력해주세요.')
        return false
    }else if(!result){
        alert('상세주소를 정확하게 입력해주세요.')
    }
    return result
}


//각 text input마다 정규표현식 조건 모듈화



function checkSignUp(){
    return checkName()&&checkId()&&checkTel()&&checkAddr()&&checkAuthNum()
        &&checkPw()&&checkChbox()
}

function checkLogin(){
    return checkId()&&checkLoginPw()
}

function checkForgetId(){
    return checkAuthNum()
}

function checkForgetPw(){
    return checkId()&&checkAuthNum()
}

function checkNewPw(){
    const result=checkPw()

    return result
}

function checkMyPageChangePw(){
    return checkPrePw()&&checkPw()
}

function checkMyPageInfo(){
    return checkAddr()
}

//각 페이지마다 입력조건 체크하는 함수 하나씩 할당