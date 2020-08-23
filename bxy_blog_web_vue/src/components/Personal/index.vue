<template>
  <el-drawer
    :visible.sync="drawer"
    :with-header="false"
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
        <el-timeline :reverse="false">
          <el-timeline-item
            v-for="(item,index) in discussList"
            :key="index"
            placement="top"
            :timestamp="item.createTime"
          >
            <el-card>
              <span style="font-size: 16px">
                <i class="el-icon-tickets" style="margin-right: 10px;"></i>
                <a target="_blank" class="out-link" :href="`/article/${item.blog.id}`">{{item.blog.title}}</a>
              </span>
              <el-divider></el-divider>
              <div>
                <i class="el-icon-chat-dot-square" style="margin-right: 10px;"></i>
                {{item.content}}
              </div>
            </el-card>
          </el-timeline-item>
          <el-timeline-item
            v-if="discussList.length == 0" 
            placement="top"
          >
            <el-card>
              <span style="font-size: 16px">空空如也~</span>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-tab-pane>
      <el-tab-pane name="2">
        <span slot="label"><i class="el-icon-message-solid"></i> 我的消息</span>
        <el-collapse>
          <el-collapse-item title="以读消息">
            <el-timeline>
              <el-timeline-item
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">空空如也~</span>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </el-collapse-item>
          <el-collapse-item title="未读消息">
            <el-timeline>
              <el-timeline-item
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">空空如也~</span>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
      <el-tab-pane name="3">
        <span slot="label"><i class="el-icon-star-on"></i> 我的点赞</span>
        <el-timeline :reverse="false">
          <el-timeline-item
            v-for="(item,index) in fabulousList"
            :key="index"
            placement="top"
            :timestamp="item.createTime"
          >
            <el-card v-if="item.type">
              <i class="el-icon-chat-dot-square" style="margin-right: 10px;"></i>
              <span style="font-size: 16px">
                <a target="_blank" class="out-link" :href="`/article/${item.blog.id}`">{{item.blog.title}}</a>
              </span>
            </el-card>
            <el-card v-else>
              <i class="el-icon-tickets" style="margin-right: 10px;"></i>
              <span style="font-size: 16px">
                <a target="_blank" class="out-link" :href="`/article/${item.blog.id}`">{{item.blog.title}}</a>
              </span>
            </el-card>
          </el-timeline-item>
          <el-timeline-item
            v-if="fabulousList.length == 0" 
            placement="top"
          >
            <el-card>
              <span style="font-size: 16px">空空如也~</span>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-tab-pane>
      <el-tab-pane name="4">
        <span slot="label"><i class="el-icon-phone"></i> 我的反馈</span>
        <el-collapse accordion>
          <el-collapse-item title="反馈须知">
            <div>如果您对本站有什么想法，可以在这里进行反馈</div>
            <div>或者联系站长进行交流</div>
          </el-collapse-item>
          <el-collapse-item title="历史记录">
            <el-timeline>
              <el-timeline-item
                v-for="(item,index) in feedbackList"
                :key="index"
                placement="top"
                :timestamp="item.createTime"
              >
                <el-card>
                  <div  slot="header">
                    {{item.title}}
                      <el-tag v-if="item.state === 0" type="warning" style="float: right;">未解决</el-tag>
                      <el-tag v-if="item.state === 1" type="success" style="float: right;">已解决</el-tag>
                      <el-tag v-if="item.state === 2" type="danger" style="float: right;">已拒绝</el-tag>
                  </div >
                  <div>
                    <el-collapse accordion>
                      <el-collapse-item title="反馈内容">
                        {{item.content}}
                      </el-collapse-item>
                       <el-collapse-item title="回复">
                        {{item.reply}}
                      </el-collapse-item>
                    </el-collapse>
                  </div>
                </el-card>
              </el-timeline-item>
              <el-timeline-item
                v-if="feedbackList.length == 0" 
                placement="top"
              >
                <el-card>
                  <span style="font-size: 16px">空空如也~</span>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </el-collapse-item>
          <el-divider></el-divider>
          <el-form 
            label-width="80px" 
            :model="feedback" 
            ref="feedback" 
            hide-required-asterisk
            status-icon
            :rules="{
              title: [
                {required: true, message: '标题不能为空', trigger: 'blur'},
                {min: 1, max: 20, message: '长度在1到20个字符'}
              ],
              content: [
                {required: true, message: '不能为空', trigger: 'blur'}
              ]
            }" 
          >
            <el-form-item label="标题" prop="title">
              <el-input v-model="feedback.title" required/>
            </el-form-item>
            <el-form-item label="内容" prop="content">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                v-model="feedback.content"
                required
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addFeedback('feedback')">提交</el-button>
            </el-form-item>
          </el-form>
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
          ref="blogLink" 
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
            <el-button type="primary" @click="ApplyingLink('blogLink')">申请</el-button>
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
        <el-divider></el-divider>
        <el-form label-width="80px" :disabled="userInfo.source === 'BXY' ? false : true">
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
import { addFeedback, getFeedbackByUser } from "@/api/feedback"
import { getFabulousByUser } from "@/api/fabulous"
import { getDiscussByUser } from "@/api/discuss"
import { updateInfo } from "@/api/user"
// 图片裁剪组件
// https://github.com/dai-siki/vue-image-crop-upload
import myUpload from 'vue-image-crop-upload'
import { formatDate } from '@/utils/webUtils'
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
      show: false,
      discussList: [],
      fabulousList:[],
      feedback: {
        title: '',
        content: ''
      },
      feedbackList: []
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
    getFeedbackByUser(){
      var params = new URLSearchParams()
      params.append('userId', this.userInfo.id)
      getFeedbackByUser(params).then(response => {
        this.feedbackList =  response.data.map(x => {
          x.createTime = this.dateFormat(x.createTime)
          x.updateTime = this.dateFormat(x.updateTime)
          return x
        })
      })
    },
    addFeedback(feedback){
      this.$refs[feedback].validate((valid) => {
        if (valid) {
          var params = new URLSearchParams(this.feedback)
          params.append('userId', this.userInfo.id)
          addFeedback(params).then(response => {
            this.$message({
              message: response.message,
              type: 'success'
            })
            this.getFeedbackByUser()
          })
        } else {
          return false
        }
      })
    },
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
          this.getDiscussByUser()
        }
        break
        case "2": {
          console.log("点击我的回复")
        }
        break
        case "3": {
          console.log("点击我的点赞")
          this.getFabulousByUser()
        }
        break
        case "4": {
          console.log("点击我的反馈")
          this.getFeedbackByUser()
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
    getFabulousByUser(){
      var params = new URLSearchParams(this.blogLink)
      params.append('userId', this.userInfo.id)
      getFabulousByUser(params).then(response => {
        this.fabulousList = response.data.map(x => {
          x.createTime = this.dateFormat(x.createTime)
          x.updateTime = this.dateFormat(x.updateTime)
          return x
        })
      })
    },
    getDiscussByUser(){
      var params = new URLSearchParams(this.blogLink)
      params.append('userId', this.userInfo.id)
      getDiscussByUser(params).then(response => {
        console.log(response.data);
        this.discussList = response.data.map(x => {
          x.createTime = this.dateFormat(x.createTime)
          x.updateTime = this.dateFormat(x.updateTime)
          return x
        })
      })
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