<template>
  <div class="top-feature">
    <section-title value="联系方式" />
    <div class="top-social">
      <div>
        <a :href="'tencent://message/?uin='+qq+'&Site=400301.com&Menu=yes'" target="_blank" style="color:#1AB6FF">
          <i class="iconfont iconqq"></i>
        </a>
      </div>
      <div>
        <a :href="gitee" target="_blank" style="color:#d81e06">
          <i class="iconfont icongitee"></i>
        </a>
      </div>
      <div>
        <a :href="github" target="_blank" style="color:#1AB6FF">
          <i class="iconfont icongithub"></i>
        </a>
      </div>
      <div>
        <a :href="csdn" target="_blank" style="color:red">
          <i class="iconfont iconcsdn"></i>
        </a>
      </div>
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
    }
  },
  computed: {
    qq() {
      return this.$store.state.app.webConfig.qq
    },
    github() {
      return this.$store.state.app.webConfig.github
    },
    gitee() {
      return this.$store.state.app.webConfig.gitee
    },
    csdn() {
      return this.$store.state.app.webConfig.csdn
    }
  },
  methods: {
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