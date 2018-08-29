conference-room-reservation-demo
===================

실행 환경 
--------
* jdk.version : `1.8.0_162`
* maven.vsersion : `3.3.9`
* port : `8080`
* index : [localhost:8080](http://localhost:8080)
* h2-console : [localhost:8080/h2](http://localhost:8080/h2)
* h2-account : sa
* h2-password : empty string

실행 방법 
--------
* 프로젝트 폴더에서 실행 cmd : `mvn spring-boot:run`
* 빌드 및 실행
    * 빌드 : `mvn install` (`web-0.0.1-SNAPSHOT.jar` 생성)
    * 실행 : `java -jar web-0.0.1-SNAPSHOT.jar`

문제 해결 전략
--------
* 시간 범위 데이터 표현
    * 정시, 30분을 기준으로 30분 단위로 예약이 가능 하므로 `00:00 부터 30분 단위`로 sequence 의미를 부여함 ( `0 ~ 47` )
    * 사용자가 입력한 예약 정보는 하나 혹은 다수의  record 로 데이터 베이스 내부에 저장
    * 하나의 회의실을 하루종일 대여할 경우 48개의 record 생성

* 회의실 중복 등록 시 동시성 문제
    * 등록 시점에 중복 레코드가 있는지 1차 유효성 검사 
    * `multi-process` 일 경우 application 레벨에서 제어가 힘드므로 database 내 `unique-constraint` 에 위임
    * `unique-key` : 등록일, 시간 시퀀스, 회의실 ID
    * 중복 데이터 발생 발견 혹은 unique-constraint-violation 발생시 (`status: 409/CONFLICT`)
    
* 데이터 유효성 문제
    * `validation-api` 를 이용하여 필수값 체크 (유효하지 않을시 `stats: 400/BAD_REQUEST`)
    * time-range 범위 유효성 검사 (유효하지 않을 시 `status: 422/UNPROCESSABLE_ENTITY`)

