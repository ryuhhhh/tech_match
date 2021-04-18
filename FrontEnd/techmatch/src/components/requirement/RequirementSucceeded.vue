<template>
  <div class="wrapper">
    <div class="declaration">
      募集を開始しました
    </div>
    <div class="requirement-info">
      <div class="info requirement-title">
        {{ requirementModel.title }}
      </div>
    </div>
    <div class="buttons-wrapper">
      <router-link to="/">
        <button class="button a is-large">
          募集一覧へ
        </button>
      </router-link>
      <router-link to="mypage">
        <button class="button cancel is-large">
          マイページへ
        </button>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
</style>

<script>
export default {
    name: 'RequirementSucceeded',
    data:function(){
        return {
            requirementModel:{},
        }
    },
    mounted:function(){
        this.requirementModel = this.$route.params.requirementModel
    }
};
</script>
<style scoped>
.wrapper{
    min-height: 100vh;
}
.declaration{
    text-align: center;
    font-size: 2.3em;
    margin-top: 100px;
}
.requirement-info{
    text-align: center;
    font-size:1.6em;
    overflow: hidden;
    margin-top:100px
}
.buttons-wrapper{
    margin-top: 100px;
    text-align: center;
}
.button{
    color:#807e68;
}
.button.a{
    margin-right:50px;
}
.requirement-title{
    font-size: 2em;
    overflow: hidden;
}
</style>