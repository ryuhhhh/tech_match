<template>
  <div class="about-wrapper">
    <diV class="title">
      About
    </div>
    <div class="section-wrapper">
      <p class="question">
        Q.サービスを開始した背景はなにか
      </p>
      <p class="answer">
        A.ITエンジニア達の個人開発をもっと協力的にしてみたいと考えため
      </p>
      <p class="message-body">
        現在、ITエンジニアに必要なスキルが身に付きやすい社会になっていると考えます。<br>
        従来からある専門書に加えウェブサービスやスクールの台頭によってハードルが下がっているからです。<br>
        また現在、人と人や人と組織をつなげるサービスなどが存在しています。<br>
        例えば、何かが特定の技術や知識を有する人とそれを欲する人とのマッチングサービスやITエンジニアとIT企業とのマッチングサービスです。<br>
        ですが、IT技術を軸にITエンジニア同士を個人間でつなげる目立ったサービスはまだありません。<br>
        以上の様にITエンジニアが個人の力を身に付きやすい背景の中、技術を中心にITエンジニア同士をつなげる目立ったサービスはないと考え、<br>
        運営者はITエンジニア同士をつなげるTechMatchをリリースしようということに至りました。<br>
      </p>
    </div>
    <div class="section-wrapper">
      <p class="question">
        Q.誰のためにあるのか
      </p>
      <p class="answer">
        A.以下の様な方たちのため
      </p>
      <div class="message-body">
        <ul>
          <li class="list-item">
            ・ITエンジニア駆け出しだが勉強のために，IT技術を駆使して一緒に何かを作りたい方
          </li>
          <li class="list-item">
            ・ITエンジニアとしてチーム開発の経験を積みたい方
          </li>
          <li class="list-item">
            ・ITエンジニアの仲間がほしい人
          </li>
        </ul>
        <br>
        などITエンジニアのためにあります
      </div>
    </div>
    <div class="section-wrapper">
      <p class="question">
        Q.何ができるのか
      </p>
      <p class="answer">
        A.技術を軸にエンジニア同士をつなぐことができる
      </p>
      <div class="message-body">
        以下の流れでつなぎます<br>
        <ol
          class="ordered-list"
          type="1"
        >
          <li class="list-item">
            募集者は"募集をかける"から募集情報を登録し応募を待つ。<br>この時、IT技術を指定して募集をかけることができます。
          </li>
          <li class="list-item">
            応募者は"募集一覧"から気になる募集情報を探し応募します。<br>この時、IT技術から探すことができます。
          </li>
          <li class="list-item">
            募集者は応募者の中から適切な方を選び，以降マイページに設定されたSNSのアカウント情報経由でコンタクトを取ります。
          </li>
        </ol>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>

<script>
export default {
    name: 'Footer',
};
</script>
<style scoped>
.about-wrapper{
    padding: 40px;
}
.section-wrapper{
    padding: 50px;
    background-color: #f4f5ef;
    border-radius: 20px;
    border: 2px #b9b8b5 solid;
    margin-top:50px;
}
.question{
    color: #4b4b4b;
    font-weight: bold;
    font-size: 1.8em;
}
.answer{
    color:#5a8404;
    font-weight: bold;
    font-size: 1.8em;
}
.message-body{
    padding: 10px;
    line-height: 1.5em;
    font-size: 1.2em;
    font-weight: bold;
}
.ordered-list{
    padding-left:30px;
}
.list-item{
    margin-top:10px;
}
</style>