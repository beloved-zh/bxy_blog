<template>
  <el-drawer
    :visible.sync="drawer"
    :show-close="false"
    direction="rtl"
  >
    <el-tabs v-model="activeName" type="border-card" tab-position="left" stretch style="height: 100%;" @tab-click="handleClick">
      <el-tab-pane name="0">
        <span slot="label"><i class="el-icon-user-solid"></i> 个人中心</span>
        <el-form 
          label-width="80px" 
          :model="userInfo" 
          hide-required-asterisk
          status-icon
        >
          <el-form-item
            label="头像"
            prop="avatar"
          >
            <template>
              <img v-if="userInfo.avatar" :src="userInfo.avatar" class="avatar" @click="show = !show">
              <div v-else class="avatar-uploader">
                <i class="el-icon-plus avatar-uploader-icon" @click="show = !show" />
              </div>
              <my-upload
                v-model="show"
                :width="300"
                :height="300"
                img-format="png"
                @crop-success="cropSuccess"
              />
            </template>
          </el-form-item>
          <el-form-item
            label="昵称"
            prop="username"
          >
            <el-input v-model="userInfo.username" />
          </el-form-item>
          <el-form-item
            label="邮箱"
            prop="email"
          >
            <el-input v-model="userInfo.email" />
          </el-form-item>
          <el-form-item
            label="电话"
            prop="mobile"
            :rules="telRules"
          >
            <el-input v-model="userInfo.mobile" />
          </el-form-item>
          <el-form-item
            label="邮件通知"
          >
            <el-radio :value="true" :label="true" border>是</el-radio>
            <el-radio :value="false" :label="false" border>否</el-radio>
          </el-form-item>
          <el-form-item
            label="来源"
            prop="source"
          >
            <el-input v-model="userInfo.source" disabled />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateInfo()">提交</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane name="1">
        <span slot="label"><i class="el-icon-s-comment"></i> 我的评论</span>
      </el-tab-pane>
      <el-tab-pane name="2">
        <span slot="label"><i class="el-icon-message-solid"></i> 我的消息</span>
      </el-tab-pane>
      <el-tab-pane name="3">
        <span slot="label"><i class="el-icon-star-on"></i> 我的点赞</span>
      </el-tab-pane>
      <el-tab-pane name="4">
        <span slot="label"><i class="el-icon-phone"></i> 我的反馈</span>
        <el-collapse accordion>
          <el-collapse-item title="反馈须知">
            <div>如果您对本站有什么想法，可以在这里进行反馈</div>
            <div>或者联系站长进行交流</div>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
      <el-tab-pane name="5">
        <span slot="label"><i class="el-icon-share"></i> 申请友链</span>
        <el-collapse>
          <el-collapse-item title="申请须知">
            <div>请确定贵站可以稳定运营</div>
            <div>申请前请先添加下方巴学园友链</div>
            <div>欢迎各位小伙伴一起互换友链~</div>
          </el-collapse-item>
          <el-collapse-item title="巴学园">
            <div>网站名称：巴学园</div>
            <div>网站LOGO：http://bxyimage.beloved.ink/logo</div>
            <div>网站简介：巴学园 - 个人学习博客</div>
            <div>网站地址：https://www.beloved.ink</div>
          </el-collapse-item>
          <el-collapse-item title="审核中">
            <el-timeline>
              <el-timeline-item
                v-for="(item,index) in linkApplying"
                :key="index"
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">
                    <a target="_blank" class="out-link" :href="item.linkUrl">{{item.linkName}}</a>
                  </span>
                </el-card>
              </el-timeline-item>
              <el-timeline-item
                v-if="linkApplying.length == 0" 
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">空空如也~</span>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </el-collapse-item>
          <el-collapse-item title="已上架">
            <el-timeline>
              <el-timeline-item
                v-for="(item,index) in linkSuccess"
                :key="index"
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">
                    <a target="_blank" class="out-link" :href="item.linkUrl">{{item.linkName}}</a>
                  </span>
                </el-card>
              </el-timeline-item>
              <el-timeline-item
                v-if="linkSuccess.length == 0" 
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">空空如也~</span>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </el-collapse-item>
        </el-collapse>
        <el-divider></el-divider>
        <el-form 
          label-width="80px" 
          :model="blogLink" 
          hide-required-asterisk
          status-icon
          :ref="blogLink" 
          :rules="linkRules"
        >
          <el-form-item label="网站名称" prop="linkName">
            <el-input v-model="blogLink.linkName"/>
          </el-form-item>
          <el-form-item label="网站简介" prop="summary">
            <el-input v-model="blogLink.summary"/>
          </el-form-item>
          <el-form-item label="网站地址" prop="linkUrl">
            <el-input v-model="blogLink.linkUrl"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="ApplyingLink(blogLink)">申请</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane name="6">
        <span slot="label"><i class="el-icon-s-tools"></i> 修改密码</span>
        <el-collapse>
          <el-collapse-item title="修改密码须知">
            <div>此修改密码功能仅适用于账号和密码登录</div>
            <div>对于第三方登录的账号，无法进行密码修改</div>
          </el-collapse-item>
          <el-collapse-item title="账号来源">
            <div>{{userInfo.source}}</div>
          </el-collapse-item>
        </el-collapse>
        <el-form label-width="80px">
          <el-form-item label="旧密码">
            <el-input />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input />
          </el-form-item>
          <el-form-item label="确认密码">
            <el-input />
          </el-form-item>
          <el-form-item label="验证码">
            <el-input>
              <el-button slot="append">发送验证码</el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary">提交</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </el-drawer>
</template>

<script>
import { getLinkByUserAndStatus, addLink } from "@/api/link"
import { updateInfo } from "@/api/user"
// 图片裁剪组件
// https://github.com/dai-siki/vue-image-crop-upload
import myUpload from 'vue-image-crop-upload'
export default {
  components: {
    'my-upload': myUpload
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    }
  },
  data(){
    return{
      drawer: this.isShow,
      activeName: '0',
      blogLink: {
        linkName: '',
        linkUrl: '',
        summary: ''
      },
      telRules: [
        { max: 11, message: '最大长度11位'},
      ],
      linkRules: {
        linkName: [
          {required: true, message: '网站名称不能为空', trigger: 'blur'},
          {min: 1, max: 10, message: '长度在1到10个字符'},
        ],
        summary: [
          {required: true, message: '简介不能为空', trigger: 'blur'},
          {min: 1, max: 50, message: '长度在1到50个字符'},
        ],
        linkUrl: [
          {required: true, message: '网站地址不能为空', trigger: 'blur'},
          {pattern:  /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/, message: '请输入有效的URL'},
        ]
      },
      linkApplying: [],
      linkSuccess: [],
      show: false
    }
  },
  watch: {
    isShow: function() {
      this.drawer = this.isShow
    },
    drawer: function() {
      this.$emit('close', this.drawer)
    }
  },
  computed: {
    userInfo() {
      return this.$store.getters.userInfo
    }
  },
  methods: {
    // 裁剪图片回调
    cropSuccess(imgDataUrl) {
      this.userInfo.avatar = imgDataUrl
    },
    handleClick(tab) {
      switch(tab.name) {
        case "0": {
          console.log("点击个人中心")
        }
        break
        case "1": {
          console.log("点击我的评论")
        }
        break
        case "2": {
          console.log("点击我的回复")
        }
        break
        case "3": {
          console.log("点击我的点赞")
        }
        break
        case "4": {
          console.log("点击我的反馈")
        }
        break
        case "5": {
          console.log("点击申请友链")
          this.getLinkByApplying()
          this.getLinkBySuccess()
        }
        break
        case "6": {
          console.log("点击修改密码")
        }
        break
      }
    },
    updateInfo(){
      var params = new URLSearchParams(this.userInfo)
      updateInfo(params).then(response => {
        this.$message({
          message: response.data,
          type: 'success'
        })
      })
      this.$store.dispatch('getInfo')
    },
    ApplyingLink(blogLink){
      this.$refs[blogLink].validate((valid) => {
        // 校验规则
        if (valid) {
          var params = new URLSearchParams(this.blogLink)
          params.append('userId', this.userInfo.id)
          addLink(params).then(response => {
            this.$message({
              message: response.data,
              type: 'success'
            })
            this.initBlogLink()
            this.getLinkByApplying()
          })
        } else {
          return false
        }
      })
    },
    getLinkByApplying(){
      var params = new URLSearchParams()
      params.append('userId', this.userInfo.id)
      params.append('linkStatus', 0)
      getLinkByUserAndStatus(params).then(response => {
        this.linkApplying = response.data
      })
    },
    getLinkBySuccess(){
      var params = new URLSearchParams()
      params.append('userId', this.userInfo.id)
      params.append('linkStatus', 1)
      getLinkByUserAndStatus(params).then(response => {
        this.linkSuccess = response.data
      })
    },
    initBlogLink(){
      this.blogLink = {
        linkName: '',
        linkUrl: '',
        summary: ''
      }
    }
  }
}
</script>

<style>
  a.out-link:hover{
    color: #ff6d6d;
  }
  .authority-user{
      margin: 10px 15px 0;
  }
  .avatar-uploader{
    background-color: #fbfdff;
    border: 1px dashed #c0ccda;
    border-radius: 6px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    width: 100px;
    height: 100px;
    cursor: pointer;
    line-height: 100px;
    vertical-align: top;
  }
  .avatar-uploader:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
</style>