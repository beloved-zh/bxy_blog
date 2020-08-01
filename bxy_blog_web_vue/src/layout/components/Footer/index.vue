<template>
  <el-footer>
    <a href="#">Copyright 2019-2020&nbsp;{{info.name}}&nbsp;</a>
    <a href="http://www.beian.miit.gov.cn">{{info.recordNum}}</a>
  </el-footer>
</template>

<script>
export default {
  data() {
    return {
      info: {
        name: '巴学园',
        recordNum: '陕ICP备19025605号-1'
      }
    }
  }
}
</script>

<style>
  .el-footer{
    background-color: #ffffff;
    color: #a5a4a4;
    text-align: center;
    line-height: 60px;
  }
  a{
    text-decoration:none;
    color: #a5a4a4;
  }
  a:hover{
    color: #00a7eb
  }
</style>