# BoardProject_JPA

> ### 프로젝트 의도
>> JPA를 배웠으니 기존 프로젝트를 JPA로 다시 구현해보는 것을 목표로 진행.
>> #
>> 기존 기능 및 데이터는 최대한 그대로 유지하고 JPA로 재구현.
>> #
>> 테스트 먼저 한 뒤에 구현하도록 TDD로 최대한 해보기. 
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
>
> 2022/06/10
>> hierarchicalBoard, imageBoard, Comment, Member의
>> 기본 리스트 테스트 완료.   
>> 계층형 쿼리 및 페이징 테스트 완료.   
>> controller, html등 기본적으로 필요한 것들 생성 완료.   
>> insert, update, delete 처리는 다 restController로 분리   
>> 기존 boardProject에서 등록해놨던 이미지 파일 그대로 사용하기 위해 webapp/img 로 이동   
>> mysql DB에도 더미데이터 및 기존 이미지 데이터 추가.