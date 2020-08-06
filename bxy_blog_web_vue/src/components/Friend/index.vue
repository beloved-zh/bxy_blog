<template>
    <div class="top-feature">
    <section-title value="友情链接" />
    <div class="top-social">
      <div v-for="(item,index) in linkData" :key="index">
        <a target="_blank" class="out-link" :href="item.linkUrl">{{item.linkName}}</a>
      </div>
    </div>
  </div>
</template>

<script>
  import SectionTitle from '@/components/SectionTitle'
  import { getLinks } from '@/api/link'
  export default {
  name: 'Friend',
  components: {
    SectionTitle
  },
  data(){
    return{
      linkData: []
    }
  },
  created(){
    this.getLinks()
  },
  methods: {
    getLinks(){
      getLinks().then(response => {
        this.linkData = response.data
      }).catch(response => {
        this.$message.error(response.data)
      })
    }
  }
}
</script>

<style scoped lang="less">
  .top-feature{
    margin: 30px 0px 20px;
    width: 100%;
  }
  a{
    font-size: 15px;
  }
  a.out-link:hover{
    color: #ff6d6d;
  }
  .top-social {
    width: 100%;
    list-style: none;
    display: inline-block;
    font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
    div {
      margin: 0px 10px;
      float: left;
      line-height: 32px;
      text-align: center;
    }
  }
</style>