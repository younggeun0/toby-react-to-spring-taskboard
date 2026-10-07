# TaskBoard — 『React에서 Spring으로』 단계별 실습

책 『React에서 Spring으로』의 예제 TaskBoard를 **커밋 하나 = 실습 한 단계**로 쌓은 레포다.
단계마다 태그(`ch05-6` 등)가 있고, 그 단계의 실습 안내는 `steps/<태그>.md`에 있다.

- 책 본문: https://library.younggeun0.dev/react-to-spring
- 기준 버전: Spring Boot 3.5.16, Java 17
- 「앱 보충」 표시: 원서에 없거나 원서대로는 동작하지 않아 이 레포에서 채운 부분

## 따라 하는 법

```bash
git clone https://github.com/younggeun0/toby-react-to-spring-taskboard.git
cd toby-react-to-spring-taskboard

git checkout ch03-1                          # 단계로 이동
cat steps/ch03-1.md                          # 그 단계의 실습 안내
git diff ch03-1 ch03-2 -- . ':!steps'        # 다음 단계에서 바뀌는 코드만 보기
git checkout main                            # 최신 단계로 돌아오기
```

태그로 이동하면 `You are in 'detached HEAD' state` 안내가 나온다. 특정 커밋을 구경하는 상태라는 뜻이고 정상이다.
실습하며 코드를 고쳤다면, 다른 단계로 이동하기 전에 `git checkout -- .`으로 되돌린다.

GitHub에서는 커밋 화면 하나로 충분하다. 바뀐 코드가 먼저 나오고, 맨 아래에 그 단계의 `steps/<태그>.md`가 새 파일로 붙어 있다.

## 구성

- `steps/`: 단계별 실습 안내. 한 번 쓴 파일은 다음 단계에서 고치지 않는다
- `ch11-*` 태그는 별도 브랜치에 있다(10장 JWT와 11장 세션 인증은 함께 켤 수 없다)

## 라이선스

[LICENSE.md](LICENSE.md) 참고. 원서와 같은 CC BY-NC-SA 4.0이며, 원서는 Toby-AI의 book-writer 하네스로 만들어졌다.
