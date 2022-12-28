# BoardProject_JPA

> ### 프로젝트 의도
>> JPA를 배웠으니 기존 프로젝트를 JPA로 다시 구현해보는 것을 목표로 진행.
>> #
>> 기존 기능 및 데이터는 최대한 그대로 유지하고 JPA로 재구현.
>> #
>> 테스트 먼저 한 뒤에 구현하도록 TDD로 최대한 해보기.
>>  #
>> 프로젝트를 만들자! 라기 보다는 공부하는 목적으로 진행.
#
> ### 사용기술
>> Spring Data JPA   
>> Sprint Boot   
>> Gradle   
>> Spring Dev tools   
>> MySQL

#
> ### 기타 사항
>> 맥북과 데스크탑 두군데서 깃에 push 할 예정이기 때문에   
>> commit시에 어디서 push 하는건지 명시.

#
> ### 히스토리
> 2022/06/10
>> hierarchicalBoard, imageBoard, Comment, Member의
>> 기본 리스트 테스트 완료.   
>> 계층형 쿼리 및 페이징 테스트 완료.   
>> controller, html등 기본적으로 필요한 것들 생성 완료.   
>> insert, update, delete 처리는 다 restController로 분리   
>> 기존 boardProject에서 등록해놨던 이미지 파일 그대로 사용하기 위해 webapp/img 로 이동   
>> mysql DB에도 더미데이터 및 기존 이미지 데이터 추가.
>
> #
> 22/10/25
>> 시큐리티 CustomUserDetailService에서 권한 리스트를 제대로 받아오지 못하는 문제점 해결.   
>> findById는 Optional로 받아오고 CustomUser에서는 Member 타입으로 파라미터를 받도록 구현되어있었다.   
>> CustomUser에서 Member 타입이 아닌 Optional로 변경해주는것도 방법이지만 Optional에서 각 값을 분리해내는 것 보다 Member 타입 객체서 분리하는게 낫다고 판단해 Member 타입으로 넘기는 방법을 찾았다.   
>> repository에 userInfo라는 이름으로 만들어 해당 아이디의 모든 데이터를 가져오도록 했고 auth는 List화 되어 들어온다.   
>> 수정 이전 코드의 경우는 쿼리문에서 select m.userid, m.userPw, m.auths from member m where ~~~   
>> 이 형태였으나 실행되는 쿼리 확인했을 때 select 부분에 아무 이름이 없는 컬럼이 하나 추가되어 syntaxError 가 발생했다.   
>> 이걸 해결하고자 했으나 해결하지 못했고 그래서 전체 데이터를 가져오는 형태로 변경했다.   
>> 그리고 member 엔티티의 auths는 현재 List로 수정했으나 처음에는 Set으로 작성했었다.   
>> 한 포스팅에서 List보다는 Set을 사용하는것이 더 낫다라고 했기 때문에 경우에따라 다르지만 최대한 Set을 사용하고자 한다.   
>> 그럼 이제 테스트에서 다시 Set으로 변경했을 때 정상적으로 동작하는지 확인해야하고,      
>> 그리고 테스트 후 auths에 @ToString.Exclude를 달아놨는데도 로그인하는데에 문제가 없었다.   
>> 이거에 대한 테스트도 필요할듯 하다.   
>> 마지막으로 정상적으로 로그인이 되어 Success Handler에 접근을 하고 있긴 하지만!   
>> 서비스를 이용할때 정상적으로 권한 처리까지 되는지를 확인해야 한다.
> #
> 
> 22/10/26
>> securityConfig 에서 WebSecurityConfigurerAdapter를 extends 해서 사용했는데 현재 Deprecated 되었다.   
>> 대체제로 코드를 변경해놨으니 확인할것.   
>> https://devlog-wjdrbs96.tistory.com/434 여기를 참고.   
>> @Preauthorize가 제대로 동작하지 않는 문제가 있었으나 해결.   
>> securityConfig에 @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) 이 어노테이션을 추가하니 정상적으로 처리된다.
>> 로그인과 로그아웃이 정상적으로 처리되는것을 테스트로 확인함.   
>> 현재 어디서 꼬이는건지 프로젝트 실행시 IllegalArgumentException이 발생하면서 간간히 메소드에 접근하지 못하는 문제가 발생. 확인해볼것.    
> #
> 
> 22/10/28
>> ImageBoard에 대한 테스트 진행.
>> imageBoardDetail의 경우 ImageBoard Entity와 ImageData Entity의 데이터 모두가 필요한데 findById나 imageDetail(조인해서 데이터를 가져오는 쿼리)로   
>> 처리하니 Many에 해당하는 ImageData의 데이터 개수만큼 중복되는 문제가 발생.   
>> Set으로 처리하면 중복을 제거해주지 않을까 했지만 중복 제거가 전혀 되지 않음.   
>> Optional과 ImageBoard 타입으로 받으려 하니 incorrectResultSizeDataAccessException과 NoUniqueResultException이 발생.   
>> 단일 데이터를 받아야 하는데 다중 데이터를 리턴하기 때문에 발생하는 문제라 다시 List와 Set으로 테스트를 진행.   
>> 계속 타입을 바꾸고 쿼리문을 바꿔가면서 테스트했지만 동일한 문제가 발생.   
>> 기존 JPA를 사용하지 않은 경우에서 어떻게 했었는지 다시 고민.   
>> 생각해보니 계속 시도했던 것 처럼 One에 해당하는 객체 하나에 Many를 포개 넣으려고 하는것이 문제라는 것을 깨닫고 방법을 변경.   
>> 기존에 진행한 프로젝트들은 상속을 받아 해당 VO로 받았으니 객체 자체가 여러개가 쌓이는 형태이니 이걸 처리하기 위해서는 여기서도 상속을 받을까? 라는 생각을 해서   
>> 엔티티 상속에 대해 알아봤으나 쉽게 적용할만한 내용이 아닌것 같은데다가 내가 원하는 답이 아니라는 느낌. 추후 엔티티 상속에 대해서는 공부해서 이 문제를 해결할 수 있을지 테스트해봐야 한다.   
>> 결론은 DTO에 매핑에서 처리하면 손쉽게 해결.   
>>    
>> 이번 문제를 해결하면서 알게된점은 OneToMany 관계에서 Many의 데이터까지 한번에 가져오는 것은 문제가 되지 않고 컨트롤러나 서비스단에서   
>> 이 데이터를 처리하는 경우에도 문제가 발생하지 않을것으로 보이나 view단에서 Many의 데이터를 제대로 다룰 수 없다는 문제가 있다.   
>> Many에 해당하는 ImageData에서 imageName와 imageStep, oldName 데이터를 사용해야 하는데 each문을 통해서 처리하려고 했더니   
>> image.get(0).getImageData()........... 이런형태로 처리해야 한다. 그리고 이게 제대로 출력해주는지도 사실 잘 모르겠다.   
>> each문을 사용하면서 이런 구조는 의미가 없다고 판단해 출력 테스트도 해보지 않았다.   
>> 정해져있는 개수의 데이터를 다룬다면 이렇게라도 사용할 수 있겠지만 굳이 이렇게 써야하는 일이 생길까 싶기도 하다.   
>> 이번 문제 해결에 있어서 가장 큰 문제점은 이전에 어떻게 했었는지 어떠한 구조로 데이터를 받아오면 되는지에 대해 너무 경솔하게 판단했다는 점이다.   
>> 기존에 어떻게 처리했었는지에 대해서만 다시 잘 생각해봤다면 금방 해결했을 문젠데 너무 꼬아서 해결하고자 하다보니 시간이 너무 오래걸린 느낌이다.   
>> 
>> 그리고 findById와 imageDetail 메소드로 테스트를 진행했을때는 쿼리문에 imageStep asc로 정렬을 하도록 해도 제대로 정렬되지 않았는데   
>> DTO로 처리하니까 정렬도 잘된다...
>> #
>> 
> 22/12/08   
>>현재까지 구현 내역   
>> 1. 계층형 게시판 리스트(boardList)
>> 2. 계층형 게시판 상세페이지(boardDetail) 및 댓글
>> 3. 회원가입
>> 4. 로그인
>> 
>> 구현 내역 중 추가 수정 해야 할 곳
>> 1. 계층형 게시판 리스트   
>>    페이징 기능 부재 상태. 그리고 select 처리 중 count() 쿼리에서 너무 많은 시간 소요. 해결방안 찾아볼것.
>> 2. 계층형 게시판 상세 페이지 및 댓글   
>>    대댓글의 계층형이 원하는 순서대로 나오지 않으니 쿼리 수정 필요
>> 3. 로그인   
>>    로그인 시 페이지 헤더에 로그인 버튼 로그아웃으로 변경되도록 수정 필요
>> 
>> 그 외 구현해야하는 부분들
>> 1. 계층형 게시판
>>    1. 게시글 작성
>>    2. 게시글 수정
>>    3. 답글 작성
>> 2. 계층형 게시판 상세 페이지 및 댓글
>>    1. 사용자에 따른 게시글 수정, 삭제, 답글, 댓글 삭제 기능
>> 3. 이미지 게시판
>>    1. 이미지 게시판 리스트
>>    2. 게시글 등록
>>    3. 게시글 수정
>>    4. 게시글 상세 페이지
> #
> 
> 22/12/09   
>> 댓글 페이징 해결.   
>> commentUpperNo 값을 RECURSIVE 로 처리했을때 처럼 넣어놔서 발생한 문제.   
>> commentUpperNo를 다시 설정함으로 해결.   
>> 
>> 게시글 상세 페이지 사용자별 버튼 출력 구현.   
>>   로그인한 사용자 아이디를 세션에 저장해서 프론트에서 비교하는 방식으로 구현.
>> 
>> 게시글 수정 구현중.   
>>   페이지 구현까지 완료. 수정 등록 구현 해야함.
> #
> 
> 22/12/12   
>> 계층형 게시판 게시글 등록, 수정, 답글 구현중.
>> 
>> thymeleaf에서 csrf토큰을 form에 담을 때 form action을 th:action으로 쓰면 thymeleaf가 form안에 csrf 토큰을 넣어준다.
>> 
>> PutMapping과 DeleteMapping으로 요청할 때 form 데이터를 넘겨주면 설정을 다르게 해줘야 한다.   
>> form은 get과 post방식만 기본적으로 지원하기 때문에 put와 delete로 설정할 수 없다.   
>> 그래서 form 안에 input을 통해 method 처리를 하게 되면 put과 delete를 사용할 수 있다.   
>> \<input type="hidden" name="_method" value="put"/>\
>>  thymeleaf에서는 form 태그에서 method="" 설정 대신 th:method="put" 으로 설정하게 되면   
>> thymeleaf가 알아서 input을 추가해주게 된다.   
>> 그리고 이렇게 사용하기 위해서는 한가지를 더 추가해야 한다.   
>> hiddenmethod 옵션을 추가해줘야 하는데 yml, properties에 추가하는 방법이 있고
>> Bean으로 등록하는 방법이 있다.   
>> spring.mvc.hiddenmethod.filter.enabled: true   
>> springBoot에서는 이렇게 설정해주면 되고 bean으로 등록할때는    
>> @Bean   
>> public HiddenHttpMethodFilter hiddenHttpMethodFilter(){   
>> return new HiddenHttpMethodFilter();   
>> }   
>> 이렇게 HiddenHttpMethodFilter를 bean으로 등록해주면 된다.   
>> yml에서 설정을 하고자 했지만 이상하게 계속 오류가 발생해서 bean으로 등록해서 처리.
>> 
>> insert, delete, replyInsert save 코드 추가.
> #
> 
> 22/12/13   
>> interceptor 구현 중 문제 발생.
>> 각 게시판 및 댓글 삭제와 수정 시 작성자와 로그인한 사용자가 동일한지 검증하기 위해서 interceptor 내에서 처리하도록 하고자 했으나
>> request를 interceptor에서 읽고난 뒤에 컨트롤러에서 읽을 수 없는 문제 발생.   
>> 찾아보니 HttpServletRequestWrapper 를 이용해 처리해야 한다.   
>> JPA가 주 목적인 프로젝트이니 기본 기능들을 먼저 구현한 다음 문제해결 할 것.   
>>
>> imageBoard 리스트와 상세 페이지 완료.
>> commentRepository commentCount 각 게시판 별로 분리.
> # 
> 
> 22/12/14   
>> ImageBoardModify, ImageBoardInsert html 파일 작성 및 imageBoard.js 파일 작성.
>> 계층형때와는 다르게 좀 중복되는 코드가 길어서 imageBoard는 js파일 우선 분리.
>> 
>> 문제는 ResponseEntity로 imageData에서 이미지 파일 데이터만 조회해 오도록 구현되어 있는데
>> imageData 엔티티를 전체조회하면 imageData, imageBoard, member, hierarchicalBoard까지 연관관계에 있는 모든 엔티티 전부가 다 조회한다.   
>> 이렇게라도 처리가 되긴 하면 그나마 다행이지만 ImageData 엔티티 타입이다보니 받아주지도 못해서 오류가 발생.   
>> 쿼리문을 좀 수정하니 연관관계에 있는 엔티티들은 따로 조회하지 않게 되었지만 ConvertNotFoundException이 계속 발생.   
>> 이전에도 비슷한 오류가 발생했을때는 Entity 쿼리에서 엔티티 컬럼을 하나 빼먹었다거나 뭐 그런 가벼운 문제였어서 확인해봤지만
>> 전혀 문제가 없고 테스트클래스에서는 멀쩡하게 잘 조회됨.   
>> 이 오류에 대한 해결 방법으로 DTO로 받아주면 된다고 하는데 imageData만 딱 받아오면 되는데 굳이 DTO를 써야하나...   
>> 라는 고집에 의해 하루종일 붙들고 있었지만 결국 해결하지 못하고 ImageDataDTO를 만들어서 받아주는 방법을 사용하자마자 
>> 바로 해결.
>> 
>> 남은 기능은 파일 처리 기능.
> #
> 
> 22/12/15   
>> 파일 처리 구현중   
>> 컨트롤러에서 정상적으로 데이터 받는것 확인.   
>> 파일 저장 및 DB insert, update, delete 처리 필요.
> #
> 
> 22/12/16
>> 파일 처리 구현중.
>> insert, modify, delete 메소드 모두 생성했고 각 기능별 메소드 분리까지 작성.   
>> 처리과정은 insert 기준 imageBoard에 먼저 데이터를 담고 imageData를 처리하는 형태.   
>> 여기서 문제점은 'Entity에 setter를 사용하지 않는것이 좋다. builder를 사용해라'라는 것을 봤기 때문에 builder로 처리하다보니
>> builder().imageBoard()에서 문제가 발생.   
>> @NonNull 어노테이션을 imageTitle에 붙여놨더니 ImageData를 저장할때 필요한건 imageNo뿐인데도 NonNull인데 null이기 때문에 안된다는 오류가 발생.   
>> NonNull을 그냥 지우면 해결되기야 할것 같았지만 그럼 나중에 @NonNull 어노테이션을 활용할 때 다시 알아봐야 할것 같아서 이상태로 해결방법을 찾음.
>> 
>> 해결 방안
>> 1. ImageBoard Entity에서 연관관계가 설정되어있는 ImageData에 대해 CascadeType.ALL을 설정.
>> 2. ImageBoard에 ImageData를 추가해주는 메소드를 생성.(addImageData(ImageData imageData))
>> 3. 서비스단에서 imageBoard를 먼저 빌드해주고 imageData를 빌드할때마다 addImageData를 통해 ImageBoard에 저장.
>> 4. ImageData의 처리가 끝나고 나면 imageBoard Entity를 save해주면 연관관계에 있는 데이터가 다 전송된다.
>> 5. 안그래도 ImageBoard는 제대로 들어가고 imageData에서 오류가 발생했을 때 롤백을 어떻게 설정해줘야 될까 고민이었는데 이 문제까지 같이 한번에 해결!
>> 6. 참고 포스팅 = https://data-make.tistory.com/730
>>
>> 이번 문제 해결 중 알게된점 한가지.
>> mysql에서 auto_increment를 사용하다보니 위 같은 경우에 Imageboard 테이블에 저장된 id값이 뭔지 알수가 없었고
>> 그래서 다시한번 DB 조회를 통해 max 값이 무엇인지 찾아서 그걸 imageData에서 활용하는 형태로 구현해왔다.   
>> 근데 save(.....build().getId())를 사용하게 되면 저장된 아이디값을 알 수 있다.   
>> save를 처리한 repository가 방금 저장한 값들을 리턴하기 때문이다.   
>> 근데 이것 역시 위 문제 해결 방법에서는 굳이 필요없다. 알아서 해줘서.   
>> 
>> 현재까지 imageBoardInsert까지 확인했고, modify, delete 테스트 필요.
> 
> #
> 
> 22/12/19   
>> 사진 게시판 insert, modify, delete 테스트 완료. 정상 작동.   
>> 파일 사이즈 체크 컨트롤러에서 접근하지 않고 각 insert, modify 메소드 내에서 호출하는 형태로 변경.
>> return Data : -1(error), 1(success), 2(oversize) 
> #
> 
> 22/12/23   
>> commentService 수정.   
>> setter를 사용하지 않고 builder를 통해서만 처리하기 위해 그에 맞는 방법으로 수정.
>> 
>> 기존 로직   
>> 1. maxNo라는 변수에 현재 테이블에서 가장 큰 commentNo를 조회해서 담음. 이 maxNo는 GroupNo와 UpperNo에 사용.
>> 2. comment.set~~ 을 통해 필요값을 담음.
>> 3. 각 게시판별 게시글 번호를 담아야 하므로 메소드를 호출해 조건문으로 어느 게시판 번호를 갖고 있는지 체크 후 comment에 set해서 comment 객체를 리턴
>> 4. repository.save(comment)로 저장.
>> 
>> 기존 로직의 문제점.
>> 1. maxNo로 조회해서 처리하는 방법의 문제점으로는 운영 서비스에서 조회한 maxNo와 commit 당시의 maxNo가 차이가 생길 가능성이 있음.
>> 2. 차이가 생기게 되면 본인의 commentNo와 GroupNo, UpperNo가 동일해야 하는데 달라진다는 문제점이 발생.
>> 3. 또한 문제점까지는 아니지만 Entity에는 setter로 처리하기보다는 builder로 처리하는 방식으로 하는것이 좋기에 이 부분의 수정이 필요.
>> 
>> 코드 수정
>> 1. maxNo 대신 1차적으로 notNull로 지정되어있는 필드인 member, content, date를 save를 하면서 getCommentNo()를 통해 save된 데이터의 commentNo를 변수에 저장.
>> 2. 그 변수를 통해 GroupNo와 UpperNo 값을 수정하는데 Map 형태로 받은 commentData에 put.
>> 3. GroupNo, UpperNo 를 제외한 나머지 데이터들도 모두 commentData에 put으로 수정 혹은 추가.
>> 4. checkBoard 메소드를 호출해 어느 게시판의 몇번 게시글인지 확인하고 그에 따라 각 게시판 댓글 수정 메소드를 호출
>> 5. 각 게시판 댓글 수정 메소드는 commentData를 인자로 받고 GroupNo, UpperNo, Indent, 게시판 No, CommentNo를 매개변수로 쿼리문 실행.
>>
>> 수정 코드에서 좀 더 생각해봐야 하는 부분   
>> 
>> JPA는 save() 하나로 insert와 update가 가능하다. 하지만 update를 위한 save()를 하기 위해서는 모든 필드의 값이 필요하다.   
>> 그 말은 이미 저장된 데이터 역시 같이 빌드해서 처리해야 한다. 이렇게 처리하게 되면 굳이 쿼리를 통해 수정을 할 필요가 없이 save()로 처리할 수 있다는 장점이 있다.   
>> 하지만 이미 저장된 데이터와 동일한 값들을 굳이 넣어서 또 보내줘야 하는가? 라는 의문점이 생긴다.   
>> save로 처리하지 않는다면 필요한 필드값만 넘겨서 update 쿼리로 처리해줄 수 있지만 save로 처리하게 되면 select -> update 순서로 처리되는 부분도 있고 
>> 같은 데이터를 한번 더 덮어 씌운다는 점에서 오히려 문제 발생의 여지가 더 생기지 않나 싶다.   
> 
> 22/12/28
>> entity 전부 @Setter 제외.   
>> 필요한 부분은 따로 set 메소드를 필드 단위로 만들어서 사용하도록 수정.      
>> 계층형 게시판도 maxNo를 조회하는 형태 말고 save로 boardNo 받아오고 수정으로 나머지 데이터 넣어주는 형태로 수정.   
> 
> 
> #
> 남은 처리내역   
>> 모든 Entity 처리에서 setter를 사용하지 않고 builder 패턴을 사용하도록 수정 후 테스트.   
>> interceptor
>