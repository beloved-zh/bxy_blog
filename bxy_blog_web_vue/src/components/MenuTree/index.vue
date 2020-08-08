<template>
    <div class="app">
        <div class="title">
            {{ title }}
        </div>
        <aside>
        <ul class="menus">
            <li v-for="(item,index) in menus" :key="index" @click="handleAnchorClick(item)"  :style="{ padding: `0px 0 0px ${item.indent * 10}px` }">
                <a style="cursor: pointer">{{ item.title }}</a>
            </li>
        </ul>
    </aside>
    </div>
</template>

<script>
    export default {
        name: "menuTree",
        props: {
            menus: {
                type: [Object,Array],
                required: true
            }
        },
        data() {
            return{
                title: '目录导航'
            }
        },
        methods: {
            handleAnchorClick(item){
                this.$emit('handleAnchorClick', item)
            }
        }
    }
</script>

<style scoped lang="less">
    .app{
        box-shadow: 0 2px 6px rgba(0, 0, 0, .1);
        border-radius: 3px;
        font-size: 16px;
        background-color: rgb(255, 255, 255);
        width: 250px;
        position: fixed;
        z-index: 10;
        top: 100px;
        left: 50px;
        bottom: 100px;
        box-sizing: border-box;
        margin: 0px;
        border-right: 1px solid rgb(234, 236, 239);
        .title{
            width: 100%;
            height: 40px;
            background: #eee;
            line-height: 40px;
        }
        aside{
            position: fixed;
            top: 140px;
            left: 50px;
            bottom: 100px;
            width: 100%;
            overflow-y: auto;
            ul{
                padding: 0px 15px 15px;
            }
        }
    }
    

    .menus{
        line-height: 1.7em;
    }
    .child{
        padding-left: 15px;
    }
</style>
