<template>
  <div class="top-feature">
    <section-title value="联系方式" />
    <div class="top-social">
      <div v-if="qq != ''">
        <a :href="'tencent://message/?uin='+qq+'&Site=400301.com&Menu=yes'" target="_blank" style="color:#1AB6FF">
          <i class="iconfont icon-qq"></i>
        </a>
      </div>
      <div v-if="gitee != ''">
        <a :href="gitee" target="_blank" style="color:#d81e06">
          <i class="iconfont icon-gitee"></i>
        </a>
      </div>
      <div v-if="github != ''">
        <a :href="github" target="_blank" style="color:#000000">
          <i class="iconfont icon-github"></i>
        </a>
      </div>
      <div v-if="csdn != ''">
        <a :href="csdn" target="_blank" style="color:red">
          <i class="iconfont icon-csdn"></i>
        </a>
      </div>
      <div v-if="weixin != ''">
        <a :href="weixin" target="_blank" style="color:#50b674">
          <i class="iconfont icon-weixin"></i>
        </a>
      </div>
      <div v-if="email != ''">
        <a :href="'mailto:' + email" target="_blank" style="color:#0078d7">
          <i class="iconfont icon-youxiang"></i>
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
    },
    weixin() {
      return this.$store.state.app.webConfig.weixin
    },
    email() {
      return this.$store.state.app.webConfig.email
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
    // height: 32px;
    width: 100%;
    list-style: none;
    display: inline-block;
    font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
    div {
      float: left;
      margin: 10px 15px;
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