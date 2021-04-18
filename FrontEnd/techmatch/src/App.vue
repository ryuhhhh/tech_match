<template>
  <div id="app-top">
    <popup />
    <top-bar />
    <router-view />
    <Footer />
    <div
      v-if="isLoading"
      class="loading  is-loading"
    >
      <div class="ball-pulse">
        <div /><div /><div />
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from '@/components/common/TopBar'
import Footer from '@/components/common/Footer'
import popup from '@/components/common/popup'
export default {
    name: 'App',
    components: {
        TopBar,
        Footer,
        popup
    },
    computed:{
        isLoading:function(){
            return this.$store.state.isLoading
        },
    }
};
</script>
<style scoped>
.loading{
  display:block;
  position:fixed;
  top:0px;
  left:0px;
  height:100vh;
  width:100vw;
  background-color:#b0a9a96e;
  z-index: 100;
}
</style>

<style scoped>
@-webkit-keyframes scale {
  0% {
    -webkit-transform: scale(1);
            transform: scale(1);
    opacity: 1; }
  45% {
    -webkit-transform: scale(0.1);
            transform: scale(0.1);
    opacity: 0.7; }
  80% {
    -webkit-transform: scale(1);
            transform: scale(1);
    opacity: 1; } }
@keyframes scale {
  0% {
    -webkit-transform: scale(1);
            transform: scale(1);
    opacity: 1; }
  45% {
    -webkit-transform: scale(0.1);
            transform: scale(0.1);
    opacity: 0.7; }
  80% {
    -webkit-transform: scale(1);
            transform: scale(1);
    opacity: 1; } }

.ball-pulse > div:nth-child(1) {
  -webkit-animation: scale 0.9s -0.24s infinite cubic-bezier(0.2, 0.68, 0.18, 1.08);
          animation: scale 0.9s -0.24s infinite cubic-bezier(0.2, 0.68, 0.18, 1.08); }

.ball-pulse > div:nth-child(2) {
  -webkit-animation: scale 0.9s -0.12s infinite cubic-bezier(0.2, 0.68, 0.18, 1.08);
          animation: scale 0.9s -0.12s infinite cubic-bezier(0.2, 0.68, 0.18, 1.08); }

.ball-pulse > div:nth-child(3) {
  -webkit-animation: scale 0.9s 0s infinite cubic-bezier(0.2, 0.68, 0.18, 1.08);
          animation: scale 0.9s 0s infinite cubic-bezier(0.2, 0.68, 0.18, 1.08); }

.ball-pulse{
    text-align: center;
    position: relative;
    top: 40%;
}
.ball-pulse > div {
  position:relative;
  top:40%;
  background-color: #7d8c5c;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  margin: 20px;
  -webkit-animation-fill-mode: both;
          animation-fill-mode: both;
  display: inline-block; }
</style>