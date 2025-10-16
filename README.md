# AWS EC2 관리 콘솔 (AWS EC2 Control Panel)

이 프로젝트는 Java AWS SDK를 사용하여 Amazon Web Services (AWS)의 EC2 인스턴스를 관리하는 간단한 콘솔 애플리케이션입니다. 클라우드 컴퓨팅 과목의 term 프로젝트로 제작되었습니다.

## 주요 기능

이 애플리케이션을 통해 다음 AWS EC2 관련 작업을 수행할 수 있습니다.

* **인스턴스 관리**
    * 인스턴스 목록 조회
    * 인스턴스 시작/중지/재부팅
    * 새로운 인스턴스 생성
* **리전 및 가용 영역 조회**
    * 사용 가능한 전체 리전 목록 조회
    * 사용 가능한 가용 영역 목록 조회
* **이미지 (AMI) 관리**
    * 소유하고 있는 AMI 목록 조회
* **모니터링**
    * 인스턴스 모니터링 활성화/비활성화
* **키페어 관리**
    * 키페어 목록 조회
    * 새로운 키페어 생성
    * 기존 키페어 삭제

## ✅ 요구 사항 (Prerequisites)

이 프로젝트를 실행하기 위해 다음 환경이 필요합니다.

1.  **Java Development Kit (JDK)**: `1.8` 또는 그 이상
2.  **Maven**: 프로젝트 의존성 관리 및 빌드를 위해 필요합니다.
3.  **AWS Credentials**:
    * 프로그램을 실행하기 전에 AWS IAM 사용자를 생성하고 Access Key와 Secret Key를 발급받아야 합니다.
    * 발급받은 키는 `~/.aws/credentials` 파일에 아래와 같은 형식으로 저장되어 있어야 합니다.
        ```ini
        [default]
        aws_access_key_id = YOUR_ACCESS_KEY
        aws_secret_access_key = YOUR_SECRET_KEY
        ```

## 🚀 실행 방법 (How to Run)

1.  **프로젝트 클론 또는 다운로드**
    ```bash
    git clone [저장소 URL]
    cd [프로젝트 디렉토리]
    ```

2.  **Maven을 이용한 빌드**
    프로젝트의 루트 디렉토리에서 다음 명령어를 실행하여 컴파일하고 필요한 라이브러리를 다운로드합니다.
    ```bash
    mvn clean install
    ```

3.  **프로그램 실행**
    빌드가 완료되면 다음 명령어를 통해 프로그램을 실행할 수 있습니다.
    ```bash
    mvn exec:java -Dexec.mainClass="awsTest.awsTest"
    ```
4.  실행 후, 콘솔에 나타나는 메뉴의 번호를 입력하여 원하는 기능을 사용합니다.

## 📦 의존성 (Dependencies)

이 프로젝트는 다음 라이브러리를 사용합니다.

* **AWS SDK for Java**: `1.12.89`
