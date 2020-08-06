<template>
  <div class="top-feature" v-if="blogList.length !== 0">
    <section-title value="特别推荐" />
    <div v-for="item in blogList" :key="item.id">
        <Feature :data="item"></Feature>
    </div>
  </div>
</template>

<script>
import SectionTitle from '@/components/SectionTitle'
import Feature from './components/Feature'
import { getBlogByLevel } from '@/api/blog'
export default {
    components: {
      Feature,
      SectionTitle
    },
    data() {
        return{
            blogList: [
                {
                    id:1,
                    img:"https://s1.ax1x.com/2020/05/14/YDfRnU.jpg",
                    title:"Akina"
                }
            ]
        }
    },
    created() {
        this.getBlogByLevel()
    },
    methods: {
        getBlogByLevel(){
        const params = {}
        params.level = 2
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

</style>