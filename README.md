[![Build Check](https://github.com/Mercury-Corporation/Crrow/actions/workflows/build_check.yaml/badge.svg?branch=master)](https://github.com/Mercury-Corporation/Crrow/actions/workflows/build_check.yaml)
# 開発者用
## 環境構築
- IntelliJ IDEA Community Edition
- MySQL Workbench 8.0 CE
- Docker Desktop
- JDK17
## デバッグ環境構築
IntelliJ IDEAを開いているものとして進めます
### apiのビルド
gradleタブからapi→Tasks→build→build
### 各サーバー起動
- データベースとphpMyAdmin  
ServicesタブからDocker-compose: crrowをDeploy  
  
- その他  
gradleタブから 
1. backend→Tasks→application→run  
2. web→kotlin browser→browserRun
## デバッグ環境へ接続
- webサーバー  
  http://localhost:8080
- backend  
  http://localhost:8081
- phpMyAdmin  
  http://localhost:8082
- database
### 各サーバー停止
停止ボタン🟥を押す  
ServicesタブからDocker-compose: crrowをDown
## 各モジュール説明

|  モジュール名   | 説明                       |
|:---------:|:-------------------------|
|   `api`   | 各モジュールから呼ばれる共通のapiが入る場所。 |
| `backend` | APサーバー。                  |
|   `web`   | Webサーバー。                 |
## コーディングスタイル
publicメソッドには必ず日本語または英語で[ドキュメント](https://kotlinlang.org/docs/kotlin-doc.html)を記載してください。  
## gitについて
開発をする際は[Crrow](https://github.com/Mercury-Corporation/Crrow)のフォークを作成し、ブランチを切って作業をしてください。（masterブランチで作業しないでください）  
開発が終了したら[Crrow](https://github.com/Mercury-Corporation/Crrow)のmasterブランチにPullRequestを発行し、[build-check](https://github.com/Mercury-Corporation/Crrow/actions/workflows/build_check.yaml)と一人以上のreviewでApproveされたらマージできます。  
コミットメッセージは[Conventional Commits](https://www.conventionalcommits.org/ja/v1.0.0-beta.4/)を参考にしてください。  