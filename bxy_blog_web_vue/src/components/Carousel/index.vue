<template>
  <div>
    <el-carousel indicator-position="outside" height="450px">
      <el-carousel-item v-for="(item,index) in blogList" :key="index">
        <el-image :src="item.url" fit="fill"></el-image>
        <div class="carousel-title">
          <span>{{item.title}}</span>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: 'Carousel',
  data() {
    return {
      blogList: [
        {
          url: 'http://qd4zqd4qv.bkt.clouddn.com/0d68c626f8e94a4287d15273b034fde4.jpg',
          title: 'Docker'
        },
        {
          url: 'http://qd4zqd4qv.bkt.clouddn.com/1b5adba89e8746c5b51aebd4d0504ea8.jpg',
          title: 'Github'
        }
      ]
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