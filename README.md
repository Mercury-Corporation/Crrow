# 開発者用
## 環境構築
- IntelliJ IDEA Community Edition
- MySQL Workbench 8.0 CE
- Docker Desktop
- JDK17
## デバッグ環境構築
### apiのビルド
gradleタブからapi→Tasks→build→build
### 各サーバー起動
- データベースとphpMyAdminの起動  
ServicesタブからDocker-compose: crrowをDeploy  
  
- その他サーバー  
gradleタブから 
1. backend→Tasks→application→run  
2. web→kotlin browser→browserRun
## 各モジュール説明

|  モジュール名   | 説明                       |
|:---------:|:-------------------------|
|   `api`   | 各モジュールから呼ばれる共通のapiが入る場所。 |
| `backend` | APサーバー。                  |
|   `web`   | Webサーバー。                 |