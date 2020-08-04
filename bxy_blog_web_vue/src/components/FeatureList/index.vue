<template>
  <div class="top-feature">
    <section-title value="特别推荐" />
    <div v-for="item in features" :key="item.title">
        <Feature :data="item"></Feature>
    </div>
  </div>
</template>

<script>
import SectionTitle from '@/components/SectionTitle'
import Feature from './components/Feature'
export default {
    components: {
      Feature,
      SectionTitle
    },
    data() {
        return{
            features: [
            {
                id:1,
                img:"https://s1.ax1x.com/2020/05/14/YDfRnU.jpg",
                title:"Akina"
            },
            {
                id:2,
                img:"https://s1.ax1x.com/2020/05/14/YDf4AJ.jpg",
                title:"使用说明"
            },
            {
                id:3,
                img:"https://s1.ax1x.com/2020/05/14/YDfT91.jpg",
                title:"文章归档",
            }
            ]
        }
    }
}
</script>

<style>

</style>