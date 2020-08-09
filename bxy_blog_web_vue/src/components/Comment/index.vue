<template>
  <div>
    <comment
      :avatar="userPhoto"
      :placeholder="placeholder"
      :commentWidth="commentWidth"
      :commentList="commentList"
      :authorId="888888"
      label="博主"
      :commentNum="commentNum"
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
    commentNum: {
      type: Number,
      default: 0
    },
    commentList: {
      type: Array,
      default: () => {
        return [
          // {
          //   id: 1,
          //   commentUser: {
          //     id: 1,
          //     nickName: 'Beloved',
          //     avatar: 'http://bxyimage.beloved.ink/6029b17ebc0b410e902ced8d560339a8'
          //   },
          //   targetUser: {
          //   },
          //   content: '测试评论内容',
          //   createDate: '2020-02-02',
          //   childrenList: [
          //     {
          //       id: 2,
          //       commentUser: {
          //         id: 2,
          //         nickName: '张三',
          //         avatar: 'http://bxyimage.beloved.ink/6029b17ebc0b410e902ced8d560339a8'
          //       },
          //       targetUser: {
          //         id: 1,
          //         nickName: 'Beloved',
          //         avatar: 'http://bxyimage.beloved.ink/6029b17ebc0b410e902ced8d560339a8'
          //       },
          //       content: '张三测试评论内容',
          //       createDate: '2020-02-02',
          //       childrenList: [
          //       ]
          //     },
          //     {
          //       id: 3,
          //       commentUser: {
          //         id: 3,
          //         nickName: '李四',
          //         avatar: 'http://bxyimage.beloved.ink/6029b17ebc0b410e902ced8d560339a8'
          //       },
          //       targetUser: {
          //         id: 1,
          //         nickName: 'Beloved',
          //         avatar: 'http://bxyimage.beloved.ink/6029b17ebc0b410e902ced8d560339a8'
          //       },
          //       content: '李四测试评论内容',
          //       createDate: '2020-02-02',
          //       childrenList: [
          //       ]
          //     }
          //   ]
          // }
        ]
      }
    }
    
  },
  computed: {
    userPhoto() {
        return this.$store.getters.getUserPhoto
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