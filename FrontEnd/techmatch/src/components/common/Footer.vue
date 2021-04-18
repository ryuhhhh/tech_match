<template>
  <div class="footer-wrapper">
    <div class="footer-bar">
      <router-link
        to="inquiry"
      >
        お問い合わせ
      </router-link>
      <router-link
        :to="{ name: 'Notices'}"
      >
        利用規約
      </router-link>
      <router-link
        :to="{ name: 'PrivacyPolicy'}"
      >
        プライバシーポリシー
      </router-link>
      <!-- <router-link
        :to="{ name: 'PrivacyPolicy'}"
      >
        パスワード変更
      </router-link> -->
      <router-link
        :to="{ name: 'DeleteUrAccount'}"
      >
        退会
      </router-link>
    <div class="copy-right">
      © 2020 techmatch.jp
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
.footer-bar{
    width:100%;
    height:50px;
    background:#6b686869;
    padding:10px;
}
a{
  text-decoration: underline;
  margin-right: 10px;
}
.copy-right{
  float:right;
}
</style>