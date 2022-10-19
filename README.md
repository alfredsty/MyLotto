# MyLotto


# 구현 목록

## 메인 화면(activity_main.xml)

<img width="357" alt="image" src="https://user-images.githubusercontent.com/102028778/196095512-2835f4c6-deaf-4810-87d2-5aa8c25b92a5.png">

## 로또 번호 생성 리스트(activity_result.xml)

<img width="356" alt="image" src="https://user-images.githubusercontent.com/102028778/196095613-61f2243a-f2fb-440b-ae6e-6cce0a518f2f.png">

# CODE REVIEW

## 로또 번호 생성 버튼을 눌렀을 때

```kotlin
binding.btnNew.setOnClickListener {
			if (binding.cbIsSame.isChecked) {
				getLottoNumber1()
			} else {
				getLottoNumber2()
			}
			binding.btnSave.isEnabled = true
		}
```
* 중복제거를 한 함수와 중복이 가능하게 한 함수를 만들어 cbIsSame 라디오 버튼이 클릭되어 있으면 중복이 가능한 함수를 실행한다. 그리고 Save버튼을 누를 수 있게 binding.btnSave.isEnabled = true로 바꿔준다.

## getLottoNumber1함수(중복 가능)


## getLottoNumber2함수(중복 불가능)



## ResultActivity


## Skill

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF.svg?&style=for-the-badge&logo=Kotlin&logocolor=white)

## Tools

![Android Studio](https://img.shields.io/badge/Android%20Studio-008000.svg?&style=for-the-badge&logo=Android%20Studio&logocolor=white)
