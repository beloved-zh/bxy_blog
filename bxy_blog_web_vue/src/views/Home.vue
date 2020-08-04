<template>
  <div class="home">
    <div class="content">
      <!-- 轮播图 -->
      <Carousel />
      <el-row :gutter="200">
        <el-col :span="16">
          <!--文章列表-->
          <PostList />
          <!-- 下一页 -->
          <More />
        </el-col>
        <el-col :span="8">
          <el-row>
            <el-col :span="24">
              <FeatureList />
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <!-- 联系方式 -->
              <Recommend />
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <!-- 友情链接 -->
              <Friend />
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      
    </div>
  </div>
</template>

<script>
  import Carousel from '@/components/Carousel'
  import PostList from '@/components/PostList'
  import FeatureList from '@/components/FeatureList'
  import Recommend from '@/components/Recommend'
  import More from '@/components/More'
  import Friend from '@/components/Friend'
  export default {
    name: 'Home',
    data() {
      return {
        
      }
    },
    components: {
      Carousel,
      PostList,
      More,
      Recommend,
      FeatureList,
      Friend
    },
    computed: {
    },
    methods: {
    },
    mounted() {
    }
  }
</script>
<style scoped lang="less">
  .home{
    background-color: #e2e2e2;
    .content{
      position: relative;
      width: 1200px;
      margin: 80px auto 0px;
    }
  }
</style>
