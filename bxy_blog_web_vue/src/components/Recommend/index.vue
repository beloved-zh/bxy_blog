<template>
  <div class="top-feature">
    <section-title value="联系方式" />
    <div class="top-social">
      <div v-for="item in socials" :key="item.id" :title="item.title"><a :href="item.href" target="_blank" :style="{'color':item.color}"><i class="iconfont" :class="item.icon"></i></a></div>
    </div>
  </div>
</template>

<script>
  import SectionTitle from '@/components/SectionTitle'
  export default {
  name: 'Recommend',
  components: {
    SectionTitle
  },
  data() {
    return{
      socials: [
          {
            color:"#1AB6FF ",
            href:"http://wpa.qq.com/msgrd?v=3&uin=1224971566&site=qq&menu=yes",
            icon:"iconqq",
            id:1,
            title:"QQ"
          },
          {
            color:"#d81e06",
            href:"https://gitee.com/fengziy",
            icon:"icongitee",
            id:2,
            title:"Gitee"
          },
          {
            color:"",
            href:"https://github.com/fengziye",
            icon:"icongithub",
            id:3,
            title:"GitHub"
          },
          {
            color:"red",
            href:"https://blog.csdn.net/feng_zi_ye",
            icon:"iconcsdn",
            id:4,
            title:"CSDN"
          }
        ]
    }
  }
}
</script>

<style scoped lang="less">
  .top-feature{
    margin-top: 30px;
    width: 100%;
  }
  .top-social {
    height: 32px;
    width: 100%;
    list-style: none;
    display: inline-block;
    font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
    div {
      float: left;
      margin-left: 25px;
      height: 32px;
      line-height: 32px;
      text-align: center;
      width: 32px;
      background: white;
      border-radius: 100%;
    }
  }
  .footer-item{
    flex: 1;
    & > div:not(:last-child){
      margin-bottom: 10px;
    }
    i{
      margin-right: 10px;
    }
  }
</style>