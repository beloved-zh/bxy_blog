<template>
  <div class="app-container">
    <el-alert
      :title="title"
      type="warning"
      show-icon
    />
    <VMdEditor
      height="500px"
      :value="contentMd"
      @save="save"
    />
  </div>
</template>

<script>
import { addAboutMe, getAboutMe } from '@/api/aboutMe'
import VMdEditor from '@/components/MyEditor/VMdEditor'
export default {
  components: {
    VMdEditor
  },
  data() {
    return {
      title: '此配置的为关于我页面信息。存储在Redis中',
      contentMd: '',
      contentHtml: ''
    }
  },
  created() {
    this.getAboutMe()
  },
  methods: {
    getAboutMe() {
      getAboutMe().then(response => {
        const { contentMd, contentHtml } = response.data
        this.contentMd = contentMd
        this.contentHtml = contentHtml
      })
    },
    save(markdown, html) {
      var params = new URLSearchParams()
      params.append('contentMd', markdown)
      params.append('contentHtml', html)
      addAboutMe(params).then(response => {
        const { message } = response
        this.$message({
          message: message,
          type: 'success'
        })
        this.getAboutMe()
      })
    }
  }
}
</script>

<style>
  .app-container {
    /* margin: 10px 15px 0; */
  }
</style>
