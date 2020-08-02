<template>
  <div class="home">
    <el-image src="http://qd4zqd4qv.bkt.clouddn.com/e96c19ca30f24b9e91ced977a7e7e15e.jpg" />
    <!-- <el-row :gutter="10">
      <el-col :span="15">
        <el-carousel indicator-position="outside">
          <el-carousel-item v-for="(item,index) in imgUrl" :key="index">
            <el-image :src="item.url" />
          </el-carousel-item>
        </el-carousel>
      </el-col>
    </el-row> -->
  </div>
</template>

<script>

export default {
  name: 'Home',
  components: {
  },
  data() {
    return{
      imgUrl: [
        {
          url: 'http://qd4zqd4qv.bkt.clouddn.com/e96c19ca30f24b9e91ced977a7e7e15e.jpg',
          title: '测试文章1'
        },
        {
          url: 'http://qd4zqd4qv.bkt.clouddn.com/750b8be4fdf4450cb9cf00f049904011.png',
          title: '测试文章1'
        },
        {
          url: 'http://qd4zqd4qv.bkt.clouddn.com/779d7ceed3e7493290a079e9742b9591.jpg',
          title: '测试文章1'
        }
      ]
    }
  }
}
</script>
