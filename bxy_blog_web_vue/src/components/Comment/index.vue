<template>
  <div>
    <comment
      :avatar="userPhoto"
      :placeholder="placeholder"
      :commentWidth="commentWidth"
      :commentList="commentList"
      :authorId="authorId"
      :label="label"
      :commentNum="commentNum"
      @doSend="doSend"
      @doChidSend="doChidSend"
    >
    </comment>
    <div class="tips" v-if="commentNum === 0">
      还没有评论，快来抢沙发~~~
    </div>
  </div>
</template>

<script>
// https://github.com/wanglinyong/hbl-comment
import comment from 'hbl-comment'
import { addDiscussFather, getDiscuss, addDiscussSon } from '@/api/discuss'
import { formatDate } from '@/utils/webUtils'
export default {
  name: 'MyComment',
  components:{
    comment
  },
  props: {
    placeholder: {
      type: String,
      default: '既然来了，那就留下什么吧~'
    },
    commentWidth: {
      type: String,
      default: '90%'
    },
    label: {
      type: String,
      default: '作者'
    },
    blogId: {
      type: String,
      default: ''
    },
    authorId: {
      type: String,
      default: ''
    },
    commentNum: {
      type: Number,
      default: 0
    }
  },
  data() {
    return{
      commentList: [
        {
          id: 'sasasa1',
          commentUser: {
            id: '1samsmmds',
            nickName: '张三',
            avatar: 'http://image.beloved.ink/Typora/23bdeddbf64660372b1664d6fda0c93.jpg'
          },
          content: '测试数据',
          createDate: '2020-02-02',
          childrenList: []
        }
      ]
    }
  },
  computed: {
    userPhoto() {
      return this.$store.getters.getUserAvatar
    },
    userInfo() {
      return this.$store.getters.userInfo
    },
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  created() {
    this.getDiscuss()
  },
  methods: {
    initCommentList(data) {
      return data.map(x => {
        const comment = {
          id: x.id,
          commentUser: {
            id: x.user.id,
            nickName: x.user.username,
            avatar: x.user.avatar
          },
          targetUser: {},
          content: x.content,
          createDate: this.dateFormat(x.createTime),
          childrenList: []
        }
        if (x.toUser) {
          comment.targetUser = {
            id: x.toUser.id,
            nickName: x.toUser.username,
            avatar: x.toUser.avatar
          }
        }
        if (x.children) {
          comment.childrenList = this.initCommentList(x.children)
        } else {
          delete comment.children
        }
        return comment
      })
    },
    getDiscuss(){
      var params = new URLSearchParams()
      params.append('blogId', this.blogId)
      getDiscuss(params).then(response => {
        const { data } = response
        this.commentList = this.initCommentList(data)
      })
    },
    doSend(val){
      if(this.isLogin) {
        var params = new URLSearchParams()
        params.append('userId', this.userInfo.id)
        params.append('content', val)
        params.append('blogId', this.blogId)
        params.append('isFirst', true)
        addDiscussFather(params).then(response => {
          this.$message({
            message: response.data,
            type: 'success'
          })
          this.getDiscuss()
        })
      }else{
        this.$notify.error({
          title: '警告',
          message: '登录后才可以评论哦~'
        });
      }
    },
    doChidSend(val,toUserUid,discussId){
      if(this.isLogin) {
        var params = new URLSearchParams()
        params.append('userId', this.userInfo.id)
        params.append('content', val)
        params.append('blogId', this.blogId)
        params.append('isFirst', false)
        params.append('toUserUid', toUserUid)
        params.append('discussId', discussId)
        addDiscussSon(params).then(response => {
          this.$message({
            message: response.data,
            type: 'success'
          })
          this.getDiscuss()
        })
      }else{
        this.$notify.error({
          title: '警告',
          message: '登录后才可以评论哦~'
        });
      }
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
  .hbl-comm{
    padding: 40px 0 !important;
  }
  .comm{
    padding: 20px 0 !important;
  }
  .comm{
    padding: 20px 0 !important;
  }
  .tips{
    width: 100%;
    text-align: center;
    margin: 50px auto;
  }

</style>