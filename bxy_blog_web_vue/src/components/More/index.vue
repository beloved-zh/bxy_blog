<template>
  <div class="more">
    <div class="more-btn" @click="loadMore" >More</div>
  </div>
</template>

<script>
export default {
  props: {
  },
  methods: {
    loadMore(){
      this.$emit('loadMore')
    }
  }
}
</script>

<style scoped lang="less">
  .more{
    margin: 50px 0;
    .more-btn{
      width: 100px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      color: #ADADAD;
      border: 1px solid #ADADAD;
      border-radius: 20px;
      margin: 0 auto;
      cursor: pointer;
      &:hover{
        color: #8fd0cc;
        border: 1px dashed #8fd0cc;
      }
    }
  }
</style>