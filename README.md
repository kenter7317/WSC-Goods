# WSC Goods - Gmail API 서비스 계정 설정 가이드

## 🚀 빠른 설정 (서비스 계정 방식)

Gmail API를 서비스 계정으로 사용하면 OAuth 인증 과정 없이 바로 이메일을 발송할 수 있습니다.

### 1단계: Google Cloud Console에서 서비스 계정 생성

1. [Google Cloud Console](https://console.cloud.google.com/) 접속
2. 프로젝트 생성 또는 기존 프로젝트 선택
3. **API 및 서비스 > 라이브러리**에서 "Gmail API" 활성화
4. **IAM 및 관리자 > 서비스 계정**으로 이동
5. **+ 서비스 계정 만들기** 클릭
6. 서비스 계정 정보 입력:
   - 서비스 계정 이름: `wsc-goods-gmail`
   - 설명: `WSC Goods Gmail API Service Account`
7. **만들기 및 계속** 클릭
8. 역할 부여 (선택사항, 건너뛰기 가능)
9. **완료** 클릭

### 2단계: 서비스 계정 키 다운로드

1. 생성된 서비스 계정 클릭
2. **키** 탭으로 이동
3. **키 추가 > 새 키 만들기** 클릭
4. **JSON** 선택 후 **만들기** 클릭
5. 다운로드된 JSON 파일을 `src/main/resources/service-account-key.json`으로 저장

### 3단계: Gmail 설정

1. Google Workspace 관리 콘솔 또는 Gmail 설정에서 서비스 계정이 이메일을 보낼 수 있도록 권한 설정
2. `application.properties`에서 `gmail.from.email`을 실제 비즈니스 이메일로 변경

### 4단계: 애플리케이션 실행

```bash
./gradlew bootRun
```

브라우저에서 `http://localhost:8080/wsc-goods` 접속하여 테스트

## 📁 파일 구조

```
src/main/resources/
├── service-account-key.json    # Google Cloud에서 다운로드한 서비스 계정 키
└── application.properties      # Gmail 설정
```

## ⚙️ 설정 파일

### application.properties
```properties
gmail.service.account.key.path=service-account-key.json
gmail.application.name=WSC Goods Email Service
gmail.from.email=your-business@yourdomain.com
```

## 🔧 주요 특징

- **서비스 계정 방식**: OAuth 인증 없이 직접 API 사용
- **HTML 이메일**: 전문적인 HTML 형식의 인증 이메일
- **이메일 필터링**: 허용된 이메일 주소만 인증번호 수신
- **재고 관리**: 굿즈별 재고 자동 차감
- **중복 신청 방지**: 동일 사용자의 중복 신청 차단

## 🛠️ 트러블슈팅

### "서비스 계정 키 파일을 찾을 수 없습니다" 오류
- `service-account-key.json` 파일이 `src/main/resources/` 폴더에 있는지 확인
- 파일명이 정확한지 확인

### "Gmail API 이메일 발송 실패" 오류
- Gmail API가 활성화되어 있는지 확인
- 서비스 계정에 Gmail 발송 권한이 있는지 확인
- `gmail.from.email` 설정이 올바른지 확인

## 📧 이메일 필터링 관리

허용된 이메일 목록은 API를 통해 관리할 수 있습니다:

```bash
curl -X POST http://localhost:8080/api/admin/email-filter \
  -H "Content-Type: application/json" \
  -d '{"emails": ["user1@example.com", "user2@example.com"]}'
```
