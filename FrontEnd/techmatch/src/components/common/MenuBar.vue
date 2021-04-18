<template>
  <div class="wrapper">
    <div
      v-show="!isAuthenticated"
      class="menu"
      style="background: #f2f0f0;"
    >
      <ul class="menu-list authenticated">
        <router-link
          :to="{ name: 'home'}"
          @click.native="Close"
        >
          募集一覧
        </router-link>
        <router-link
          to="login"
          @click.native="Close"
        >
          ログイン
        </router-link>
        <router-link
          to="registration"
          @click.native="Close"
        >
          登録
        </router-link>
        <router-link
          to="about"
          @click.native="Close"
        >
          About
        </router-link>
      </ul>
    </div>
    <div
      v-show="isAuthenticated"
      class="menu"
      style="background: #f2f0f0;"
    >
      <ul class="menu-list authenticated">
        <router-link
          :to="{ name: 'home'}"
          @click.native="Close"
        >
          募集一覧
        </router-link>
        <router-link
          to="requirement-parent"
          @click.native="Close"
        >
          募集をかける
        </router-link>
        <router-link
          to="mypage"
          @click.native="Close"
        >
          マイページ
        </router-link>
        <!-- <router-link
          to="inquiry"
          @click.native="Close"
        >
          問い合わせ
        </router-link> -->
        <router-link
          to="about"
          @click.native="Close"
        >
          About
        </router-link>
      </ul>
    </div>
  </div>
</template>

<style scoped>
</style>

<script>
export default {
    name: 'MenuBar',
    computed:{
        isAuthenticated:function(){
            return this.$store.state.isLoggedIn
        }
    },
    methods:{
        Close:function(){
            this.$emit('Close')
        }
    }
};
</script>
<style scoped>
.menu{
    z-index: 50;
    position: fixed;
    top: 0px;
    left: 0px;
    background: #f2f0f0;
    height: 100%;
    width: max-content;
}
.menu-list a{
    padding: 0.5em 1.75em;
    font-size: 1.3em;
}
</style>