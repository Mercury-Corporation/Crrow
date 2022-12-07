# APIドキュメント
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
**email**: メールアドレス（20文字）
**school**: 学校名（13文字）
#### Responses
| Status | Description | Schema |
|:------:|:------------|:-------|
|  201   | none        | none   |
|  400   | none        | none   |
|  500   | none        | none   |