<template>
  <div class="app-container">
    <el-form :ref="form" hide-required-asterisk :model="form" :rules="rules">
      <el-form-item label="Logo" label-width="120px" prop="logo">
        <el-upload
          class="avatar-uploader"
          action=""
          :show-file-list="false"
          :http-request="uploadImage"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="form.logo" :src="form.logo" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>
      <el-form-item label="网站名称" label-width="120px" prop="webName">
        <el-input v-model="form.webName" />
      </el-form-item>
      <el-form-item label="网站地址" label-width="120px" prop="webUrl">
        <el-input v-model="form.webUrl" />
      </el-form-item>
      <el-form-item label="简介" label-width="120px" prop="summary">
        <el-input v-model="form.summary" />
      </el-form-item>
      <el-form-item label="作者" label-width="120px" prop="author">
        <el-input v-model="form.author" />
      </el-form-item>
      <el-form-item label="备案号" label-width="120px" prop="recordNum">
        <el-input v-model="form.recordNum" />
      </el-form-item>
      <el-form-item label="GitHub" label-width="120px" prop="github">
        <el-input v-model="form.github" />
      </el-form-item>
      <el-form-item label="GitEE" label-width="120px" prop="gitee">
        <el-input v-model="form.gitee" />
      </el-form-item>
      <el-form-item label="CSDN" label-width="120px" prop="csdn">
        <el-input v-model="form.csdn" />
      </el-form-item>
      <el-form-item label="微信" label-width="120px" prop="weixin">
        <el-input v-model="form.weixin" />
      </el-form-item>
      <el-form-item label="QQ" label-width="120px" prop="qq">
        <el-input v-model="form.qq" />
      </el-form-item>
      <el-form-item label="邮箱" label-width="120px" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-row :gutter="24">
        <el-col :span="5">
          <el-form-item label="阿里支付" label-width="120px" prop="aliPay">
            <el-upload
              class="avatar-uploader"
              action=""
              :show-file-list="false"
              :http-request="uploadAliPay"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="form.aliPay" :src="form.aliPay" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="微信支付" label-width="120px" prop="weixinPay">
            <el-upload
              class="avatar-uploader"
              action=""
              :show-file-list="false"
              :http-request="uploadWeixinPay"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="form.weixinPay" :src="form.weixinPay" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item style="text-align: right">
        <el-button type="primary" @click="submitForm(form)">确 定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { uploadImage } from '@/api/qiniu'
import { getConfig, updateConfig } from '@/api/web'
export default {
  data() {
    return {
      form: {
        id: '',
        logo: '',
        webName: '',
        webUrl: '',
        summary: '',
        author: '',
        recordNum: '',
        github: '',
        gitee: '',
        csdn: '',
        weixin: '',
        qq: '',
        email: '',
        aliPay: '',
        weixinPay: ''
      },
      rules: {
        webName: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        summary: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        recordNum: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        github: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/, message: '请输入正确的Github地址' }
        ],
        gitee: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/, message: '请输入正确的Gitee地址' }
        ],
        csdn: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/, message: '请输入正确的CSDN地址' }
        ],
        qq: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /[1-9]([0-9]{5,11})/, message: '请输入正确的QQ号码' }
        ],
        email: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/, message: '请输入正确的邮箱' }
        ]
      }
    }
  },
  created() {
    this.getConfig()
  },
  methods: {
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        // 校验规则
        if (valid) {
          var params = new URLSearchParams(this.form)
          updateConfig(params).then(response => {
            const { data } = response
            this.initForm(data)
            this.$message({
              message: data,
              type: 'success'
            })
            this.getConfig()
          })
        } else {
          return false
        }
      })
    },
    getConfig() {
      getConfig().then(response => {
        const { data } = response
        this.initForm(data)
      })
    },
    initForm(data) {
      this.form = {
        id: data.id,
        logo: data.logo,
        webName: data.webName,
        webUrl: data.webUrl,
        summary: data.summary,
        author: data.author,
        recordNum: data.recordNum,
        github: data.github,
        gitee: data.gitee,
        csdn: data.csdn,
        weixin: data.weixin,
        qq: data.qq,
        email: data.email,
        aliPay: data.aliPay,
        weixinPay: data.weixinPay
      }
    },
    // 自定义文件上传
    uploadWeixinPay(param) {
      const formData = new FormData()
      formData.append('file', param.file)
      uploadImage(formData).then(response => {
        this.form.weixinPay = response.data.fileUrl
        this.$message({
          message: '图片上传成功',
          type: 'success'
        })
      }).catch(response => {
        this.$message.error(response.data)
      })
    },
    // 自定义文件上传
    uploadAliPay(param) {
      const formData = new FormData()
      formData.append('file', param.file)
      uploadImage(formData).then(response => {
        this.form.aliPay = response.data.fileUrl
        this.$message({
          message: '图片上传成功',
          type: 'success'
        })
      }).catch(response => {
        this.$message.error(response.data)
      })
    },
    // 自定义文件上传
    uploadImage(param) {
      const formData = new FormData()
      formData.append('file', param.file)
      uploadImage(formData).then(response => {
        this.form.logo = response.data.fileUrl
        this.$message({
          message: '图片上传成功',
          type: 'success'
        })
      }).catch(response => {
        this.$message.error(response.data)
      })
    },
    beforeAvatarUpload(file) {
      const types = ['image/jpeg', 'image/gif', 'image/bmp', 'image/png']
      const isImage = types.includes(file.type)
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isImage) {
        this.$message.error('上传图片只能是 JPG、GIF、BMP、PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!')
      }
      return isImage && isLt2M
      // return false
    }
  }
}
</script>

<style>
  .app-container {
    margin: 10px 15px 0;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: rgb(140, 147, 157);
    width: 105px;
    height: 105px;
    line-height: 105px;
    text-align: center;
  }
  .avatar {
    width: 105;
    height: 105px;
    display: block;
  }
</style>
