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
    const reg =  /^[a-zA-Z0-9{8,20}]+$/;
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
        alert('전화번호는 -를 빼고,숫자로 입력해주세요(10~11글자)');
    }
    return result

}
function checkLoginPw(){
    const pw = document.getElementById('password').value;
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

function checkAddr(){
    const addr=document.getElementById('addr').value;
    const subAddr = document.getElementById('sub-addr').value;
    const blank_pattern = /^\s+|\s+$/g;
    const isBlank= subAddr.replace(blank_pattern, '' ) === ""

    const reg =  /[^ㄱ-ㅎㅏ-ㅣ{1,}]+$/;
    const result=reg.test(subAddr)&&(!isBlank)
    if(addr===""){
        alert('주소를 입력해주세요.')
        return false
    }else if(!result){
        alert('상세주소를 정확하게 입력해주세요.')
    }
    return result
}

function checkRegisterItem(){
    const itemName=document.getElementById("itemName").value;
    const itemDescription=document.getElementById("itemDescription").value;
    const blank_pattern = /^\s+|\s+$/g;
    const isBlankStart=(itemName.replace(blank_pattern,'' ) === "")||(itemDescription.replace(blank_pattern,'' ) === "")
    const isImgEmpty=document.getElementById("itemImgUrl").files.length === 0

    const itemPrice=document.getElementById("price").value
    const itemAmount=document.getElementById("amount").value
    const itemDegree=document.getElementById("degree").value
    if(itemName===""){
        alert("상품 이름을 입력해주세요.(최대 120자)")
        return false
    }else if(isImgEmpty){
        alert("상품 사진을 넣어주세요.")
        return false
    }else if(itemPrice===""){
        alert("상품 가격을 넣어주세요.")
        return false
    }else if(itemAmount===""){
        alert("상품 수량을 넣어주세요.")
        return false
    }else if(itemDegree===""){
        alert("상품 도수를 넣어주세요 무알콜이면 0")
        return false
    }
    else if(itemDescription===""){
        alert("상품 설명을 입력해주세요.(최대 120자)")
        return false
    }else if(isBlankStart){
        alert("상품 이름, 상품 설명의 첫 시작은 공백이 될 수 없습니다.")
        return false
    }else
        return true
}

function checkUpdateItem(){
    const itemName=document.getElementById("itemName").value;
    const itemDescription=document.getElementById("itemDescription").value;
    const blank_pattern = /^\s+|\s+$/g;
    const isBlankStart=(itemName.replace(blank_pattern,'' ) === "")||(itemDescription.replace(blank_pattern,'' ) === "")

    const itemPrice=document.getElementById("price").value
    const itemAmount=document.getElementById("amount").value
    const itemDegree=document.getElementById("degree").value
    if(itemName===""){
        alert("상품 이름을 입력해주세요.(최대 120자)")
        return false
    }else if(itemPrice===""){
        alert("상품 가격을 넣어주세요.")
        return false
    }else if(itemAmount===""){
        alert("상품 수량을 넣어주세요.")
        return false
    }else if(itemDegree===""){
        alert("상품 도수를 넣어주세요 무알콜이면 0")
        return false
    }
    else if(itemDescription===""){
        alert("상품 설명을 입력해주세요.(최대 120자)")
        return false
    }else if(isBlankStart){
        alert("상품 이름, 상품 설명의 첫 시작은 공백이 될 수 없습니다.")
        return false
    }else
        return true
}

function checkRegisterCocktail(){
    const cocktailName=document.getElementById("cocktailName").value;
    const isImgEmpty=document.getElementById("cocktailImgUrl").files.length === 0
    const cocktailDescription=document.getElementById("cocktailDescription").value
    const blank_pattern = /^\s+|\s+$/g;
    const isBlankStart=(cocktailName.replace(blank_pattern,'' ) === "")||(cocktailDescription.replace(blank_pattern,'' ) === "")
    if(cocktailName===""){
        alert("칵테일 이름을 입력해주세요.(최대 120자)")
        return false
    }else if(isImgEmpty){
        alert("칵테일 사진을 넣어주세요.")
        return false
    }else if(cocktailDescription===""){
        alert("칵테일 설명을 넣어주세요.(최대 120자)")
        return false
    }else if(isBlankStart){
        alert("칵테일 이름, 칵테일 설명의 첫 시작은 공백이 될 수 없습니다.")
        return false
    }
    return true
}

function checkUpdateCocktail(){
    const cocktailName=document.getElementById("cocktailName").value;
    const cocktailDescription=document.getElementById("cocktailDescription").value
    const blank_pattern = /^\s+|\s+$/g;
    const isBlankStart=(cocktailName.replace(blank_pattern,'' ) === "")||(cocktailDescription.replace(blank_pattern,'' ) === "")
    if(cocktailName===""){
        alert("칵테일 이름을 입력해주세요.(최대 120자)")
        return false
    }else if(cocktailDescription===""){
        alert("칵테일 설명을 넣어주세요.(최대 120자)")
        return false
    }else if(isBlankStart){
        alert("칵테일 이름, 칵테일 설명의 첫 시작은 공백이 될 수 없습니다.")
        return false
    }
    return true
}

function checkSignUp(){
    return checkName()&&checkId()&&checkTel()&&checkAddr()&&checkAuthNum()
        &&checkPw()&&checkChbox()
}

function checkForgetId(){
    return checkAuthNum()
}

function checkMyPageInfo(){
    return checkId()&&checkTel()&&checkAddr()
}

function checkSocialMyPageInfo(){
    return checkTel()&&checkAddr()
}





//각 페이지마다 입력조건 체크하는 함수 하나씩 할당
