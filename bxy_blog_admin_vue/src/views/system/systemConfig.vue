<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane name="0" label="七牛云配置">
        <el-form :ref="qiniu" hide-required-asterisk :model="qiniu">
          <el-form-item label="七牛云图片域名" label-width="120px" prop="prefix">
            <el-input v-model="qiniu.prefix" />
          </el-form-item>
          <el-form-item label="七牛云公钥" label-width="120px" prop="accessKey">
            <el-input v-model="qiniu.accessKey" />
          </el-form-item>
          <el-form-item label="七牛云私钥" label-width="120px" prop="secretKey">
            <el-input v-model="qiniu.secretKey" />
          </el-form-item>
          <el-form-item label="上传空间" label-width="120px" prop="bucket">
            <el-input v-model="qiniu.bucket" />
          </el-form-item>
          <el-form-item label="存储区域" label-width="120px" prop="zone">
            <el-select v-model="qiniu.zone" clearable placeholder="请选择存储区域">
              <el-option label="华东" value="z0" />
              <el-option label="华北" value="z1" />
              <el-option label="华南" value="z2" />
              <el-option label="北美" value="na0" />
              <el-option label="东南亚" value="as0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateQiniuConfig()">确 定</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane name="1" label="邮箱配置">邮箱配置</el-tab-pane>
      <el-tab-pane name="2" label="JWT配置">
        <el-form :ref="jwt" hide-required-asterisk :model="jwt">
          <el-form-item label="私钥" label-width="120px" prop="secretKey">
            <el-input v-model="jwt.secretKey" />
          </el-form-item>
          <el-form-item label="有效时间（秒）" label-width="120px" prop="expirationTime">
            <el-input v-model="jwt.expirationTime" />
          </el-form-item>
          <el-form-item label="Token头部" label-width="120px" prop="head">
            <el-input v-model="jwt.head" />
          </el-form-item>
          <el-form-item label="Token前缀" label-width="120px" prop="headPrefix">
            <el-input v-model="jwt.headPrefix" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateJwtConfig()">确 定</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane name="3" label="Redis配置">
        <aside>
          Redis管理主要用于清空一些缓存数据<br>
          用户首次部署时，可以使用清空全部，把Redis中的缓存一键清空<br>
        </aside>
      </el-tab-pane>
      <el-tab-pane name="4" label="第三方登录">
        <el-collapse>
          <el-form :ref="oauth" hide-required-asterisk :model="oauth">
            <el-collapse-item title="GITHUB">
              <el-form-item label="Client ID" label-width="120px" prop="GithubClientId">
                <el-input v-model="oauth.GithubClientId" />
              </el-form-item>
              <el-form-item label="Client Secret" label-width="120px" prop="GithubClientSecret">
                <el-input v-model="oauth.GithubClientSecret" />
              </el-form-item>
              <el-form-item label="应用回调地址" label-width="120px" prop="GithubClientSecret">
                <el-input v-model="oauth.GithubRedirectUri" />
              </el-form-item>
            </el-collapse-item>
            <el-collapse-item title="GITEE">
              <el-form-item label="Client ID" label-width="120px" prop="GiteeClientId">
                <el-input v-model="oauth.GiteeClientId" />
              </el-form-item>
              <el-form-item label="Client Secret" label-width="120px" prop="GiteeClientSecret">
                <el-input v-model="oauth.GiteeClientSecret" />
              </el-form-item>
              <el-form-item label="应用回调地址" label-width="120px" prop="GiteeRedirectUri">
                <el-input v-model="oauth.GiteeRedirectUri" />
              </el-form-item>
            </el-collapse-item>
            <el-collapse-item title="QQ">
              <el-form-item label="Client ID" label-width="120px" prop="QQClientId">
                <el-input v-model="oauth.QQClientId" />
              </el-form-item>
              <el-form-item label="Client Secret" label-width="120px" prop="QQClientSecret">
                <el-input v-model="oauth.QQClientSecret" />
              </el-form-item>
              <el-form-item label="应用回调地址" label-width="120px" prop="QQRedirectUri">
                <el-input v-model="oauth.QQRedirectUri" />
              </el-form-item>
            </el-collapse-item>
            <el-collapse-item title="微信">
              <el-form-item label="Client ID" label-width="120px" prop="WeixinClientId">
                <el-input v-model="oauth.WeixinClientId" />
              </el-form-item>
              <el-form-item label="Client Secret" label-width="120px" prop="WeixinClientSecret">
                <el-input v-model="oauth.WeixinClientSecret" />
              </el-form-item>
              <el-form-item label="应用回调地址" label-width="120px" prop="WeixinRedirectUri">
                <el-input v-model="oauth.WeixinRedirectUri" />
              </el-form-item>
            </el-collapse-item>
            <el-divider />
            <el-form-item>
              <el-button type="primary" @click="updateOauthConfig()">确 定</el-button>
            </el-form-item>
          </el-form>
        </el-collapse>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import {
  getQiniuConfig,
  updateQiniuConfig,
  getJwtConfig,
  updateJwtConfig,
  getOauthConfig,
  updateOauthConfig
} from '@/api/system'
export default {
  data() {
    return {
      activeName: 0,
      qiniu: {
        prefix: '',
        accessKey: '',
        secretKey: '',
        bucket: '',
        zone: ''
      },
      jwt: {
        secretKey: '',
        expirationTime: '',
        head: '',
        headPrefix: ''
      },
      oauth: {
        GithubClientId: '',
        GithubClientSecret: '',
        GithubRedirectUri: '',
        GiteeClientId: '',
        GiteeClientSecret: '',
        GiteeRedirectUri: '',
        QQClientId: '',
        QQClientSecret: '',
        QQRedirectUri: '',
        WeixinClientId: '',
        WeixinClientSecret: '',
        WeixinRedirectUri: ''
      }
    }
  },
  created() {
    this.getQiniuConfig()
  },
  methods: {
    handleClick(tab) {
      switch (tab.name) {
        // eslint-disable-next-line no-lone-blocks
        case '0' : {
          console.log('点击了七牛云配置')
          this.getQiniuConfig()
        }
          break
        // eslint-disable-next-line no-lone-blocks
        case '1' : {
          console.log('点击了邮箱配置')
        }
          break
        // eslint-disable-next-line no-lone-blocks
        case '2' : {
          console.log('点击了JWT配置')
          this.getJwtConfig()
        }
          break
        // eslint-disable-next-line no-lone-blocks
        case '3' : {
          console.log('点击了Redis配置')
        }
          break
        // eslint-disable-next-line no-lone-blocks
        case '4' : {
          console.log('点击了第三方登录配置')
          this.getOauthConfig()
        }
          break
      }
    },
    getOauthConfig() {
      getOauthConfig().then(response => {
        const { data } = response
        this.oauth = data
      })
    },
    getQiniuConfig() {
      getQiniuConfig().then(response => {
        const { data } = response
        this.qiniu = data
      })
    },
    getJwtConfig() {
      getJwtConfig().then(response => {
        const { data } = response
        this.jwt = data
      })
    },
    updateJwtConfig() {
      var params = new URLSearchParams(this.jwt)
      updateJwtConfig(params).then(response => {
        const { message } = response
        this.$message({
          message: message,
          type: 'success'
        })
      })
    },
    updateQiniuConfig() {
      var params = new URLSearchParams(this.qiniu)
      updateQiniuConfig(params).then(response => {
        const { message } = response
        this.$message({
          message: message,
          type: 'success'
        })
      })
    },
    updateOauthConfig() {
      var params = new URLSearchParams(this.oauth)
      updateOauthConfig(params).then(response => {
        const { message } = response
        this.$message({
          message: message,
          type: 'success'
        })
      })
    }
  }
}
</script>

<style>
  .app-container {
    margin: 10px 15px 0;
  }
</style>
