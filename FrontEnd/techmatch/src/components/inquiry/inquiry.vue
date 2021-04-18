<template>
  <div class="inquiry-wrapper">
    <diV class="title">
      お問い合わせ
    </div>
    <p class="inquiry-message">
      運営者へのお問い合わせ・ご意見等は下記の連絡先へお願いします。
    </p>
    <div class="mail-wrapper">
      <a href="mailto:support@techmatch.tokyo">support@techmatch.tokyo</a>
    </div>
    <div class="caution">
      ※ 内容によりますが１～２週間ほどお時間を頂く場合がございます。<br>
      ※ お問い合わせ後は自動返信などはございません。あらかじめご了承ください。
    </div>
  </div>
</template>
<script>
export default {
    name: 'Inquiry',
};
</script>
<style scoped>
.inquiry-wrapper{
  min-height:100vh;
  padding:50px;
}
.inquiry-message{
  font-size:1.6em;
}
.mail-wrapper{
  margin-top:20px;
}
a{
  font-size:1.8em;
  text-decoration: underline;
}
.caution{
    margin-top: 30px;
    font-size: 1.3em;
}
</style>