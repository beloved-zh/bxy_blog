<template>
  <el-dialog
    title="登录"
    :visible.sync="isShow"
    width="30%"
    :center="true"
  >
    <el-form :model="form" :ref="form" :rules="rules" >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center;">
        <el-row>
          <el-col :span="12">
            <el-button class="loginBut" type="primary" @click="handleLogin(form)">登录</el-button>
          </el-col>
          <el-col :span="12">
            <el-button class="logonBut">注册</el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item class="loginCircleBut">
        <el-row>
          <el-col :span="6">
            <el-button class="github" @click="goAuth('github')" type="warning" circle><i class="iconfont icon-github"></i></el-button>
          </el-col>
          <el-col :span="6">
            <el-button class="gitee" @click="goAuth('gitee')" type="warning" circle><i class="iconfont icon-gitee"></i></el-button>
          </el-col>
          <el-col :span="6">
            <el-button class="qq" type="warning" circle><i class="iconfont icon-qq"></i></el-button>
          </el-col>
          <el-col :span="6">
            <el-button class="weixin" type="warning" circle><i class="iconfont icon-weixin"></i></el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <div class="loginTip">
          推荐使用第三方登录
        </div>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { oauthLogin } from "@/api/user"
import { getToken } from '@/utils/auth'
import { getUrlVars } from '@/utils/webUtils'
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return{
      isShow : this.dialogVisible,
      form: {
        username: 'admin',
        password: '123456'
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    dialogVisible: function() {
      this.isShow = this.dialogVisible
    },
    isShow: function() {
      this.$emit('close', this.isShow)
    }
  },
  created(){
    this.getToken()
    this.judgeIsLogin()
  },
  methods: {
    getToken(){
      let token = getUrlVars('token')
      if(token != false){
        this.$store.dispatch('setToken',token)
        this.$store.dispatch('getInfo')
      }
    },
    judgeIsLogin(){
      // 确定用户是否已登录
      const hasToken = getToken()
      
      if(hasToken) {
        this.$store.dispatch('getInfo')
      }
    },
    handleLogin(form) {
      this.$refs[form].validate((valid) => {
        // 校验规则
        if (valid) {
          this.$store.dispatch('login', this.form).then(() =>{
            this.$store.dispatch('getInfo').then(() =>{
              this.$emit('close', false)
            })
          })
        } else {
          return false
        }
      })
    },
    goAuth(source){
      var params = new URLSearchParams();
      params.append("source", source);
      oauthLogin(params).then(response => {
        console.log(response.data);
        window.location.href = response.data.url
      })
    }
  }
}
</script>

<style scoped lang="less">
  .loginTip {
      text-align: center;
      font-size: 14px;
      color: #bababa;
  }
  .loginBut{
    width: 80% !important;
  }
  .logonBut{
    width: 80% !important;
  }
  .loginCircleBut{
    text-align: center !important;
    button{
      border: none !important;
    }
    .github{
      background: #000 !important;
    }
    .gitee{
      background: #c71d23 !important;
    }
    .qq{
      background: #1ab6ff !important;
    }
    .weixin{
      background: #91dc59 !important;
    }
  }
</style>