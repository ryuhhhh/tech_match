<h1> 概要 </h1>

* 下記URLのWEBアプリのソースです
* https://www.techmatch.tokyo/about にWEBアプリの概要を載せています

<h1> URL</h1>

* https://www.techmatch.tokyo/

<h1> qiita記事</h1>

* https://qiita.com/zS1Solx7fEMDQSb/items/f9e46b8e669b708137cf

<h1>  アーキテクチャ</h1>

<h2> バックエンド</h2>

* クリーンアーキテクチャを参考に実装。
  * APIエンドポイント層 ⇔ サービス層 ⇔ レポジトリ層 ⇔ DB の流れで処理が進む
    * APIエンドポイント層では主に認証を実施
    * サービス層では主にバリデーションを実施
    * レポジトリ層では主にDBに対する処理を実施

<details><summary>バックエンドのフォルダ構成</summary><div>

* src/main/java/com/techmatch/base 配下

<h2> /controller 配下</h2>

<h3> /rest_api <h3>

* 下記を実行するための各種APIエンドポイント層を作成。ここからサービス処理を呼び出す。
  * 認証の
  * 募集する
  * 応募する
  * ユーザ情報を操作する
  * マスタ情報を呼び出す
----
<h2> /representative 配下</h2>

* 処理のひな形(インタフェース)を用意する層。実際の処理は/logic配下で行う。

<h3> /service </h3>

* サービス層のインタフェースを宣言

<h3> /repository </h3>

* レポジトリ層のインタフェースを宣言
  * ここからSQL処理を呼び出すことができる
----
<h2> /logic 配下</h2>

* バリデーションやデータ処理などの実際の処理を行う

<h3> /service <h3>

* /representative/service で宣言されたインタフェースを実装
* バリデーションを主に行い，ＯＫならレポジトリ層(/repository)の処理を呼びに行く

<h3> /repository </h3>

* 主にデータベースに対する処理を行い、その他メール送信・画像ファイルに対する処理も行う
* ＳＱＬを叩く際は/representative/repository のインタフェースを呼び出す
----
<h2> /common 配下</h2>

* 共通処理、エンティティ、モデル、列挙型を用意する

<h3> /utility </h3>

* 以下の処理
  * エンティティ⇔モデル 間の変換処理
  * 画像チェック/削除/変換 処理
  * 列挙型宣言

<h3> /model <h3>

* 以下に関する、リクエストやレスポンス用のモデルを用意。バリデーションの内容もここで決める。
  * 募集情報
  * 募集に対する応募情報
  * 認証情報
  * 登録されたユーザ情報

<h3> /entity </h3>

* 以下のentityクラスを用意
  * 募集情報
  * 募集に対する応募情報
  * 募集のスキル情報
  * ユーザ情報
  * 募集時に使用する情報のマスタ(スキル情報やレベル情報の一覧)

<h3> /error </h3>

* エラーを捕捉しハンドリングするクラスやエラーレスポンスのエンティティクラスを用意

----
<h2> /exception </h2>

* 各種例外クラスを作成

</div></details>


<h1> フロントエンドのフォルダ構成 </h1>


<details><summary>フロントエンドのフォルダ構成</summary><div>

* FrontEnd/techmatch/ 配下

</div></details>
