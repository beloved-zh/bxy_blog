<template>
  <div class="js-search search-form search-form--modal is-visible">
    <div class="search-form__inner">
      <div>
        <p class="micro mb-">你想搜索什么...</p>
        <i class="iconfont icon-sousuo"></i>
        <input class="submit" type="search" name="s" placeholder="Search...">
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
  .search-form input {
      font-size: 24px;
      font-size: 1.5rem;
      background: none;
      padding: 12px 24px 12px 64px;
      width: 100%;
      outline: none;
      border-radius: 50px;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
  }
  .iconfont {
    font-family: "iconfont" !important;
    font-size: 16px;
    font-style: normal;
    -webkit-font-smoothing: antialiased;
    -webkit-text-stroke-width: 0.2px;
    -moz-osx-font-smoothing: grayscale;
  }
  .search-form i {
      font-size: 32px;
      font-size: 1.5rem;
      line-height: 1;
      color: #ddd;
      position: absolute;
      bottom: 18px;
      margin-top: -16px;
      left: 16px;
  }
  p {
      margin-bottom: 1.5em;
      font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
  }
  .search-form--modal .search-form__inner {
    max-width: 640px;
    padding: 0 20px;
    margin: auto;
    text-align: left;
    position: absolute;
    width: 100%;
    left: 0;
    right: 0;
    height: 85px;
    top: 0;
    bottom: 0;
    font-family: miranafont,"Hiragino Sans GB",STXihei,"Microsoft YaHei",SimSun,sans-serif;
  }
  .search-form div {
      position: relative;
      top: -150px;
  }
  .search-form .is-visible{
    visibility: visible;
    opacity: 1;
  }
  .search-form--modal{
    -moz-transition: visibility 0.25s ease, opacity 0.25s ease;
    -ms-transition: visibility 0.25s ease, opacity 0.25s ease;
    -o-transition: visibility 0.25s ease, opacity 0.25s ease;
    transition: visibility 0.25s ease, opacity 0.25s ease;
    overflow: hidden;
    z-index: 999;
    position: fixed;
    top: 0;
    right: 0;
    left: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.96);
    visibility: hidden;
    opacity: 0;
  }
</style>