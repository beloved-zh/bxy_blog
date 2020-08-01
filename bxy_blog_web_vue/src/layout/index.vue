<template>
  <div class="layout">
    <el-container direction="vertical">
      <Header />
      <el-main>
        <router-view/>
      </el-main>
      <Backtop />
      <Footer />
    </el-container>
  </div>
</template>

<script>
import { Header, Footer, Backtop } from './components'
export default {
  name: 'Layout',
  components: {
    Header,
    Footer,
    Backtop
  }
}
</script>

<style>
  .layout{
    background-color: #f0f2f5;
  }
  .el-container{
    margin: 0;
    padding: 0;
  }
</style>