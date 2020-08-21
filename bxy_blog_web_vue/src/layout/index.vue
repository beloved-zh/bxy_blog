<template>
  <div id="app" v-cloak>
    <Loading />
    <Header />
    <Main />
    <Footer />
  </div>
</template>
<script>
  import Header from './components/Header'
  import Main from './components/Main'
  import Footer from './components/Footer'
  import Loading from '@/components/Loading'
  export default {
    name: "Layout",
    components: {
        Header,
        Main,
        Footer,
        Loading
    }
  }
</script>
<style lang="less">
  // #app {
  //   font-size: 17px;
  //   color: #6f6f6f;
  // }
</style>
