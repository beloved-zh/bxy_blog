<template>
  <div>
    <el-carousel indicator-position="outside" height="450px">
      <el-carousel-item v-for="(item,index) in blogList" :key="index">
        <router-link :to="`/article/${item.id}`">
          <el-image :src="item.image" fit="fill"></el-image>
          <div class="carousel-title">
            <span>{{item.title}}</span>
          </div>
        </router-link>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import { getBlogByLevel } from '@/api/blog'
export default {
  name: 'Carousel',
  data() {
    return {
      blogList: []
    }
  },
  created() {
    this.getBlogByLevel()
  },
  methods: {
    getBlogByLevel(){
      const params = {}
      params.level = 1
      getBlogByLevel(params).then(response => {
        this.blogList = response.data
      }).catch(response => {
        this.$message.error(response.data)
      })
    }
  }
}
</script>

<style>
.carousel-title {
  cursor: pointer;
  position: absolute;
  z-index: 10;
  bottom: 40px;
  height: 40px;
  width: 100%;
  line-height: 40px;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
}
.carousel-title span {
  color: white;
  font-size: 22px;
  display: inline-block;
}
</style>