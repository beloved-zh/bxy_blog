<template>
  <el-dialog
    title="登录"
    :visible.sync="isShow"
    width="30%"
    :center="true"
  >
    <el-form :model="form">
      <el-form-item label="用户名">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center;">
        <el-row>
          <el-col :span="12">
            <el-button class="loginBut" type="primary">登录</el-button>
          </el-col>
          <el-col :span="12">
            <el-button class="logonBut">注册</el-button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item class="loginCircleBut">
        <el-row>
          <el-col :span="6">
            <el-button class="github" type="warning" style="background: #fff;" circle><i class="iconfont icon-github"></i></el-button>
          </el-col>
          <el-col :span="6">
            <el-button class="gitee" type="warning" circle><i class="iconfont icon-gitee"></i></el-button>
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
        username: '',

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
  methods: {
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