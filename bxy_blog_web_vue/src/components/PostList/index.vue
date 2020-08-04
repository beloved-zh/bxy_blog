<template>
  <!--文章列表-->
    <main class="site-main">
        <section-title value="文章列表" />
        <template v-for="item in postList">
            <post :post="item" :key="item.id"></post>
        </template>
    </main>
</template>

<script>
import SectionTitle from '@/components/SectionTitle'
import Post from './components/Post'
export default {
    components: {
      SectionTitle,
      Post
    },
    data() {
        return{
            postList: [
          {
            banner:"https://s1.ax1x.com/2020/05/14/YDhagx.jpg",
            commentsCount:99,
            content:"",
            id:0,
            isHot:false,
            isTop:false,
            pubTime:505057707561,
            summary:"Lua 是一种轻量小巧的脚本语言，能为应用程序提供灵活的扩展和定制功能。",
            title:"测试文章",
            viewsCount:4045
          },
          {
            banner:"https://s1.ax1x.com/2020/05/14/YDhagx.jpg",
            commentsCount:99,
            content:"",
            id:2,
            isHot:true,
            isTop:true,
            pubTime:505057707561,
            summary:"Lua 是一种轻量小巧的脚本语言，能为应用程序提供灵活的扩展和定制功能。",
            title:"测试文章",
            viewsCount:4045
          },
          {
            banner:"https://s1.ax1x.com/2020/05/14/YDhagx.jpg",
            commentsCount:99,
            content:"",
            id:3,
            isHot:true,
            isTop:true,
            pubTime:505057707561,
            summary:"Lua 是一种轻量小巧的脚本语言，能为应用程序提供灵活的扩展和定制功能。",
            title:"测试文章",
            viewsCount:4045
          }
        ]
        }
    }
}
</script>

<style>

</style>