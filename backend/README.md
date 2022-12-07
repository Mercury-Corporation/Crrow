# APIドキュメント
## Example
### PowerShell
```shell
# 新しくユーザーを作成する
$uri = 'http://localhost:8081/user'
$body = [System.Text.Encoding]::UTF8.GetBytes('{"name":"高専太郎","birthday":"2004-05-27","sex":1,"introduction":"こんにちは世界","nickname":"kosentr","icon":"dummy","email":"example@example.com","school":"富山高専"}')
curl -Method Post -Uri $uri -Body $body -ContentType 'application/json' 
```

## Base URL
http://localhost:8081
## Users
### Userを作成
POST /user  
新しくユーザーを作成する。
#### Body
```json
{
  "name": "string",
  "birthday": "yyyy-mm-dd",
  "sex": 0,
  "introduction": "hello",
  "nickname": "string",
  "icon": "string",
  "email": "example@example.com",
  "school": "string"
}
```
#### Detail
**name**: 本名（15文字）  
**birthday**: 誕生日  
**sex**: 性別（0.無回答 1.男 2.女 9.その他）  
**introduction**: 自己紹介（200文字）  
**nickname**: ニックネーム（20文字）  
**icon**: アイコンのファイル名（32文字）  
**email**: メールアドレス（20文字、任意）。  
**school**: 学校名（13文字）
#### Responses
| Status | Description | Schema |
|:------:|:------------|:-------|
|  201   | none        | none   |
|  400   | none        | none   |
|  500   | none        | none   |