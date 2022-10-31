## 개발 환경
Mac OS Montery 12.6  
안드로이드 스튜디오  

사용 언어 : Kotlin  
컴파일 SDK 버전 : 32 (Android 12)  
사용 AVD : Pixel 2 API 31  



## 구현 내용
회원정보 저장은 프리퍼런스를 이용하여 json형식으로 만든 데이터를 프리퍼런스에 string형식 변환하여 저장하고
다시 프리퍼런스에서 액티비티로 데이터로 불러올 때는 string을 json형식으로 변환하는 방식으로 구현했다.

그리고 액티비티에서 xml 위젯의 아이디를 바탕으로 findViewById() 메소드를 호출하여 동작을 정의하는 대신
뷰바인딩을 활용했다. 뷰바인딩을 활용하기 위해 build.gradle(Module)에서 android.buildTypes에 
buildFeatures { viewBinding true }를 추가했다.

과제에 주어진 3개의 화면을 구성하기위해 3개의 layout xml(activity_main.xml, activity_second.xml, activity_third.xml) 파일과
3개의 액티비티(MainActivity.kt, SecondActivity.kt, ThirdActivity.kt) 파일을 구성했다.
이와 별도로 현재 로그인 정보를 저장하기 위해 Data object를 선언하고,
프리퍼런스를 관리하는 class인 PreferenceUtil class를 정의했다.  



### 1. 첫번째 화면 - MainActivity (로그인)
첫번째 화면은 Relative Layout으로 구성했다.
Relative layout을 통해 아이디와 비밀번호 입력을 받는 EditText 위젯을 포함한 Linear Layout과
로그인 버튼, 회원 가입 버튼, 상품 보기 버튼 위젯을 포함한 Linear Layout을 화면의 중앙 부분에 배치했다.

로그인 화면에서 동작하는 MainActivity에 onCreate() 메소드에 프리퍼런스에서 정보를 불러오는 PreferenceUtil 클래스의 getMember() 메소드를 호출하여
프리퍼런스에서 key값이 users인 string을 불러와 JSONArray로 변환하여 사용자의 로그인 정보를 불러와서 
애플리케이션이 실행되는동안 PreferenceUtil 클래스의 curJsonArray 필드에서 로그인 정보를 지니고 있도록 구현했다.

로그인 버튼을 클릭하면 해당 아이디와 비밀번호를 curJsonArray에 존재하는 각각의 회원의 아이디와 비밀번호와 비교하며 루프를 돌다가
아이디와 비밀번호가 일치하는 사용자를 발견하면 해당 사용자의 정보를 Data object에서 저장하여 로그인 상태를 유지할 수 있도록 구현했다.
만약 아이디와 비밀번호가 일치하는 사용자가 하나도 없다면 toast 메시지를 이용해 "아이디와 비밀번호가 일치하지 않습니다" 문구를 출력하여 로그인에 실패했음을 알린다.
로그인을 성공했을 때는 toast 메시지로 "로그인에 성공했습니다" 문구를 출력하고,
startActivity 메소드와 Intent메소드를 이용해 상품 보기에 해당하는 액티비티인 ThirdActivity를 생성하고, 로그인 액티비티는 종료하도록 구현했다.

만약 ID나 비밀번호가 입력되지 않았는데 사용자가 로그인 버튼을 눌렀을 경우에는
toast 메시지로 "아이디를 입력해주세요"나 "비밀번호를 입력해주세요"를 출력하고 해당 EditText를 focus하도록 구현했다.

두번째 버튼인 회원가입 버튼을 누르면 startActivity 메소드와 Intent메소드를 이용해 회원가입 액티비티인 SecondActivity를 생성하고 로그인 액티비티는 종료하도록 구현했다.
세번째 버튼인 상품 보기 버튼을 누르면 상품 보기에 해당하는 액티비티인 ThirdActivity를 생성하고, 로그인 액티비티는 종료하도록 구현했다.

그리고 어플리케이션이 완벽하게 종료되지 않은 경우 사용자의 로그인 정보를 저장하여 어플리케이션을 재시작할 때
MainActivity가 켜지면 저장된 유저의 정보를 유지한채로 바로 ThirdActivity를 실행하고 MainActivity를 종료하도록 구현했다.  



### 2. 두번째 화면 - SecondActivity (회원 가입)
두번째 화면은 Linear Layout으로 orientation 속성을 기본값인 vertical로 두어 세로로 위젯들을 배치하여 구성했다. 
두번째 화면의 경우 위젯이 많아 기본 화면에 위젯을 다 담을 수 없어
Linear Layout의 부모로 ScrollView를 두어 레이아웃을 밑으로 스크롤할 수 있도록 구현했다.

사용자의 회원가입을 위해 id, 비밀번호, 이름, 휴대폰번호, 주소를 입력받기 위해 
material의 textfield.TextInputLayout과 textfield.TextInputEditText를 활용하여 위젯을 구성했다.
TextInputLayout의 hint 속성을 이용하여 각 입력값에 해당되는 제한 사항을 사용자가 알 수 있도록 구성했다.
counterMaxLength 속성을 이용하여 현재 EditText에 입력된 글자수를 알 수 있도록 구성했다.
그리고 TextInputEditText 위젯의 속성에 maxLength를 두어 입력될 수 있는 최대 글자수를 제한했다.

비밀번호 입력의 경우 TextInputLayout 위젯의 passwordToggleEnabled를 true로 설정하여 
비밀번호를 기본적으로는 보이지 않지만 사용자가 원하면 토글하여 볼 수 있도록 설정했다.

개인정보 사용동의 문구는 TextView 위젯을 이용하여 표기하고, RadioGroup과 2개의 RadioButton을 두어 개인정보제공 동의나 거부 표시를 할 수 있도록 구현했다.
그리고 개인정보제공 동의를 한 경우에만 회원 가입이 가능하도록 지정했다.

id, 비밀번호, 이름, 휴대폰번호, 주소입력의 경우 액티비티에서 프로그래밍을 통해 입력받은 값을 정규식으로 검사한다.
사용자의 정보를 json 형식으로 프리퍼런스에 string으로 저장하기 때문에 사용자의 입력에서 중괄호나 따옴표 같은 특수문자가 요소가 존재할 때 입력을 그대로 받아버리면
저장에 문제가 생기기 때문에 비밀번호의 경우 특수문자의 입력을 json형식에 영향을 미치지 않는 ! @ # & ^ & * 만 가능하도록 제한했다.

id의 경우 소문자나 숫자로 6\~13글자 입력이 가능하다. 비밀번호의 경우 특수문자, 문자, 숫자를 모두 포함하여 6\~13글자의 입력이 가능하다.
전화번호의 경우 010-1234-5678 의 형태로 3글자-4글자-4글자, -부호를 포함하여 입력을 받도록 지정했다.
이름과 주소의 경우 영어, 한글, 띄어쓰기만을 허용하여 입력을 받는다. 띄어쓰기만 존재하는 경우를 막기 위해 정규식으로 문자열을 확인하기전에
문자열에 trim() 메소드를 사용하여 공백을 제거했다.

이러한 규칙을 바탕으로 사용자의 정보 입력을 바탕으로 정규식을 통과하면 회원 정보를 프리퍼런스에 저장하고,
프리퍼런스에 저장을 성공하면 MainActivity를 실행하고 현재 액티비티를 종료하여 로그인 화면으로 가도록 구현했다.  



### 3. 세번째 화면 - ThirdActivity (상품 보기)
세번째 화면은 Constraint Layout을 활용하여 레이아웃을 구성했다. Constraint Layout의 자식으로
ScrollView 안에 TableLayout을 활용하여 이미지뷰를 포함한 5개의 Linear Layout을 5행 1열로 배치했다.
레이아웃이 세로로 길어져 사용자가 스크롤할 수 있도록 ScrollView를 이용했다.
각각 이미지마다 이미지의 이름과 이미지를 배치하기 위해 Linear Layout을 이용하여 이름과 이미지를 묶어서 
shape으로 만든 drawable 리소스를 background src로 두어 모서리가 둥근 사각형 틀안에 배치했다.

Frame Layout을 이용해 항상 화면의 오른쪽 아래에 이미지 버튼이 위치하도록 지정했다.
안드로이드 다이얼로그를 이용해 사용자가 로그인한 상태에서 이미지 버튼을 누를 경우 회원의 정보를 보여준다.
회원의 정보는 Data object에서 지니고 있는 정보를 AlertDialog.Builder의 setMessage() 메소드를 활용하여 시각화한다.
만약 사용자가 로그인 하지 않은 경우 AlertDialog.Builder의 setMessage(), setPositiveButton(), setNegativeButton()을 활용하여
회원가입을 했는지 여부를 물어본다. 만약 사용자가 회원가입을 했다고 대답하면 다시 동일한 메소드를 활용하여 로그인을 하고 싶은지 여부를 물어보고
로그인을 하고 싶다고 응답하면 MainActivity(로그인 액티비티)를 실행하고 현재 액티비티를 종료한다. 만약 로그인 하고 싶지 않다 응답하면 해당 다이얼로그를 종료한다.

사용자가 회원가입을 하지 않았다고 대답하면 AlertDialog.Builder의 setMessage(), setPositiveButton(), setNegativeButton()을 활용하여
다시 회원가입을 하고 싶은지 여부를 물어보고 회원가입을 하고 싶다고 응답하면 SecondActivity(회원 가입)를 실행하고 현재 액티비티를 종료한다.
회원가입을 하고 싶지 않다면 다이얼로그를 종료하고 상태를 유지한다.  
