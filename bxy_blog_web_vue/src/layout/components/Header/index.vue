<template>
  <el-header>
    <el-row :gutter="10">
      <el-col :span="6">
        <h1 class="logo">巴学园</h1>
      </el-col>
      <el-col :span="12">
        <el-menu 
          background-color="#ffffff"
          mode="horizontal"
          text-color="#bdbdbd"
          active-text-color="#00a7eb"
          default-active="2"
        >
          <el-menu-item index="2">首页</el-menu-item>
          <el-menu-item index="3">关于我</el-menu-item>
          <el-menu-item index="4">归档</el-menu-item>
          <el-menu-item index="5">分类</el-menu-item>
          <el-menu-item index="6">标签</el-menu-item>
          <el-menu-item index="7">聊天室</el-menu-item>
        </el-menu>
      </el-col>
      <!--  @click="openSearch" -->
      <el-col  :span="6">
        <span class="search">
          <el-input v-model="input" placeholder="请输入内容" suffix-icon="el-icon-search" class="searchInput"></el-input>
          <!-- <svg t="1596278437447" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2838" width="32" height="32"><path d="M998.701176 827.181176L766.960941 595.440941c77.793882-153.735529 47.917176-343.988706-74.420706-466.29647-76.016941-76.016941-177.091765-117.895529-284.611764-117.880471-107.504941 0-208.579765 41.863529-284.611765 117.880471-156.928 156.928-156.928 412.265412 0 569.20847 75.941647 75.941647 176.911059 117.744941 284.310588 117.744941a401.618824 401.618824 0 0 0 207.23953-57.298823l226.093176 226.108235a75.535059 75.535059 0 0 0 52.946824 22.166588c18.507294 0 35.704471-7.002353 48.429176-19.727058l58.789647-58.774589c27.301647-27.271529 26.202353-72.764235-2.424471-101.391059zM611.478588 617.276235c-54.377412 54.377412-126.644706 84.314353-203.535059 84.314353s-149.172706-29.936941-203.535058-84.314353c-112.218353-112.218353-112.218353-294.851765 0-407.070117 54.362353-54.377412 126.644706-84.299294 203.535058-84.299294s149.157647 29.921882 203.535059 84.299294c112.218353 112.218353 112.218353 294.836706 0 407.070117z" p-id="2839" fill="#c0c4cc"></path></svg> -->
        </span>
        <el-dropdown class="userAvater">
          <el-avatar icon="el-icon-user-solid"></el-avatar>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>登录</el-dropdown-item>
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>
    
  </el-header>
</template>

<script>
export default {
  data() {
    return {
      input: '',
      logo: "https://www.logosc.cn//uploads//output//2020//07//07//292fb74b1861179eeda07f3dbddd5e09.png"
    }
  },
  methods: {

  }
}
</script>

<style>
  .el-header{
    margin: 0px;
    padding: 0px !important;
    text-align: center;
    background-color: #ffffff;
    border-bottom: solid 1px #e6e6e6;
    font-size: 16px;
  }
  .el-row{
    height: 60px;
    width: 100%;
  }
  .el-col{
    height: 100%;
    width: 100%;
  }
  .userAvater{
    width: 35px;
    height: 35px;
    position: absolute;
    right: -100px;
    top: 10px;
  }
  .logo{
    color: #00a7eb;
    line-height: 25px;
  }
  .search{
    height: 36px;
    position: absolute;
    top: 10px;
    right: 230px;
  }
  .searchInput{
    display:inline
  }
</style>