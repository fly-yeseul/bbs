const LinkFunc = {  // 상수인 LinkFunc 는 object 선언
    recover: () => {        // LinkFunc의 멤버인 recover는 함수이다.
        alert('복구!');       // 함수
    },
    register: () => {       // LinkFunc 의 멤버인 Register는 함수이다.
        LoginForm.getElement().querySelectorAll('input').forEach(input => input.setAttribute('disabled', 'disabled'));
        // LoginForm 에 input으로 받은 속성을 input에 대입한다.
        LoginForm.cover.show();
        // LoginForm을 커퍼에 보여준다.
        LoginForm.sendBack();
        // LoginForm을
        RegisterForm.getElement().querySelectorAll('input').forEach(input => {
            if (['email', 'password', 'text'].some(x => x === input.getAttribute('type'))) {
                input.value = ''
            }
        });
        RegisterForm.cover.hide();
        RegisterForm.bringFront();
        RegisterForm.getElement()['email'].focus();
    },
    registerCancel: () => {
        RegisterForm.cover.show();
        RegisterForm.sendBack();
        LoginForm.getElement().querySelectorAll('input').forEach(input => input.removeAttribute('disabled'));
        LoginForm.cover.hide();
        LoginForm.bringFront();
        LoginForm.getElement()['email'].focus();
        LoginForm.getElement()['email'].select();
    },
};

const LoginForm = {
    // 상수 LoginForm 은 Object이다.
    getElement: () => window.document.getElementById('login-form'),
    // getElement 함수는 id가 'login-form'인 요소를 가져온다.
    cover: {
        // cover는 Ojbect의 키값이다.
        getElement: () => LoginForm.getElement().querySelector(':scope > [rel=cover]'),
        // getElement는 'scope/rel-cover'인 쿼리를 모아서 LoginForm 의 요소에 추가한다.
        show: () => LoginForm.cover.getElement().classList.add('visible'),
        // show 는 loginForm의 cover의 요소들을 visible classList에 추가한다.
        hide: () => LoginForm.cover.getElement().classList.remove('visible'),
        isShown: () => LoginForm.cover.getElement().classList.contains('visible')
    },
    sendBack: () => LoginForm.getElement().classList.add('aside'),
    bringFront: () => LoginForm.getElement().classList.remove('aside')
};

const RegisterForm = {
    getElement: () => window.document.getElementById('register-form'),
    cover: {
        getElement: () => RegisterForm.getElement().querySelector(':scope > [rel=cover]'),
        show: () => RegisterForm.cover.getElement().classList.add('visible'),
        hide: () => RegisterForm.cover.getElement().classList.remove('visible'),
        isShown: () => RegisterForm.cover.getElement().classList.contains('visible')
    },
    globalWarning: {
        getElement: () => RegisterForm.getElement().querySelector('[rel=global-warning]'),
        show: () => RegisterForm.globalWarning.getElement().classList.add('visible'),
        hide: () => RegisterForm.globalWarning.getElement().classList.remove('visible'),
        isShown: () => RegisterForm.globalWarning.getElement().classList.contains('visible')
    },
    sendBack: () => RegisterForm.getElement().classList.add('aside'),
    bringFront: () => RegisterForm.getElement().classList.remove('aside')
};

window.document.body.querySelectorAll('[data-link-mode=spa]').forEach(link => {
    link.addEventListener('click', e => {
        e.preventDefault(); // e를 막는다.
        const linkFunc = link.dataset.linkFunc;         // 'recover' | 'register' | 'registerCancel'
        if (typeof (linkFunc) === 'string' && typeof (LinkFunc[linkFunc]) === 'function') {       // 함수일 경우에만 실행하겠다.
            LinkFunc[linkFunc]();
        }
    });
});

LoginForm.getElement().onsubmit = e => {
    // TODO: 로그인 구현
    e.preventDefault();
    LoginForm.cover.show();
    // const formData = new FormData(window.document.getElementById
    const formData = new FormData(LoginForm.getElement());
    // 실제로 폼 요소는 아님.
    const xhr = new XMLHttpRequest();

    // http://localhost:8080/user/login
    // http://localhost:8080/user/register 이 주소로 요청이 나간다.
    xhr.open('POST', 'login');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            LoginForm.cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const response = JSON.parse(xhr.responseText);
                switch (response['result']) {
                    case 'FAILURE':
                        alert("실패하였습니다.");
                        break;
                    case 'SUSPENDED':
                        alert("해당 계정은 정지되었습니다.");;
                        break;
                    case 'DELETED':
                        alert("해당 계정은 삭제되었습니다.");
                        break;
                    case 'SUCCESS':
                        alert('성공')
                        window.location.reload();
                        break;
                    default:
                        alert("알 수 없는 오류로 회원가입에 실패하였습니다. 잠시 후 다시 시도해주세요.");
                }
                /*
                "index": 0,
                "email": "test3@test.com",
                "password": "test1234",
                "nickname": "test3",
                "contact": "01000000003",
                "level": 0,
                "registeredAt": null,
                "modifiedAt": null,
                "isdeleted": false,
                "issuspended": false,
                "result": "DUPLICATE_EMAIL"
                * */
            }
        }
    };
    xhr.send(formData);
};

RegisterForm.getElement().onsubmit = e => {
    e.preventDefault();
    RegisterForm.cover.show();
    // const formData = new FormData(window.document.getElementById
    const formData = new FormData(RegisterForm.getElement());
    // 실제로 폼 요소는 아님.
    const xhr = new XMLHttpRequest();

    // http://localhost:8080/user/login
    // http://localhost:8080/user/register 이 주소로 요청이 나간다.
    xhr.open('POST', 'register');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            RegisterForm.cover.hide();
            if (xhr.status >= 200 && xhr.status < 300) {
                const response = JSON.parse(xhr.responseText);
                switch (response['result']) {
                    case 'DUPLICATE_EMAIL':
                        RegisterForm.globalWarning.getElement().innerText="해당 이메일은 이미 사용 중입니다.";
                        RegisterForm.globalWarning.show();
                        break;
                    case 'DUPLICATE_NICKNAME':
                        RegisterForm.globalWarning.getElement().innerText= "해당 닉네임은 이미 사용 중입니다.";
                        RegisterForm.globalWarning.show();
                        break;
                    case 'DUPLICATE_CONTACT':
                        RegisterForm.globalWarning.getElement().innerText= "해당 연락처는 이미 사용 중입니다.";
                        RegisterForm.globalWarning.show();
                        break;
                    case 'SUCCESS':
                        alert('회원가입이 완료되었습니다. 확인 버튼을 클릭하면 로그인 화면으로 돌아갑니다.')
                        LinkFunc.registerCancel();
                        break;
                    default:
                        RegisterForm.globalWarning.getElement().innerText="알 수 없는 오류로 회원가입에 실패하였습니다. 잠시 후 다시 시도해주세요.";
                        RegisterForm.globalWarning.show();
                }
                /*
                "index": 0,
                "email": "test3@test.com",
                "password": "test1234",
                "nickname": "test3",
                "contact": "01000000003",
                "level": 0,
                "registeredAt": null,
                "modifiedAt": null,
                "isdeleted": false,
                "issuspended": false,
                "result": "DUPLICATE_EMAIL"
                * */
            } else {
                RegisterForm.globalWarning.getElement().innerText = '회원가입 도중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.';
                RegisterForm.globalWarning.show();
            }
        }
    };
    xhr.send(formData);
};