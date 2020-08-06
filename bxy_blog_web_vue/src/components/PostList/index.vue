<template>
  <!--文章列表-->
    <main class="site-main">
        <section-title value="文章列表" />
        <template v-for="item in topList">
            <post :post="item" :key="item.id"></post>
        </template>
        <template v-for="item in postList">
            <post :post="item" :key="item.id"></post>
        </template>
        <!-- 下一页 -->
        <More v-if="hasNext" @loadMore="loadMore" />
    </main>
</template>

<script>
import SectionTitle from '@/components/SectionTitle'
import Post from './components/Post'
import More from '@/components/More'
import { getBlogByLevelAndPage, getBlogByTop } from '@/api/blog'
import { formatDate } from '@/utils/webUtils'
export default {
  components: {
    SectionTitle,
    Post,
    More
  },
  data() {
      return{
        currentPage: 1, // 当前页
        pageSize: 2, // 每页大小
        hasNext: false, // 是否存在下一页
        postList: [],
        topList: []
      }
  },
  created(){
    this.getBlogByTop()
    this.getBlogByLevelAndPage()
  },
  methods: {
    getBlogByTop(){
      getBlogByTop().then(response => {
        const { data } = response
        this.topList = data.map(x => {
          x.createTime = this.dateFormat(x.createTime)
          return x
        })
      }).catch(response => {
        this.$message.error(response.data)
      })
    },
    loadMore(){
      this.currentPage += 1
      this.getBlogByLevelAndPage()
    },
    getBlogByLevelAndPage(){
      const params = {}
      params.level = 0
      params.currentPage = this.currentPage
      params.pageSize = this.pageSize
      getBlogByLevelAndPage(params).then(response => {
        const { data } = response
        this.currentPage = data.current
        this.pageSize = data.size
        this.postList = [...this.postList,...data.records.map(x => {
          x.createTime = this.dateFormat(x.createTime)
          return x
        })]
         
        if(data.total > (data.size * data.current)){
          return this.hasNext = true
        }else{
          return this.hasNext = false
        }
      }).catch(response => {
        this.$message.error(response.data)
      })
    },
    // 格式化日期
    dateFormat: function(value) {
      var date = new Date(value)
      if (date === undefined) {
          return ''
      }
      return formatDate(date, 'YYYY-MM-DD hh:mm:ss')
    } 
  }
}
</script>

<style>

</style>