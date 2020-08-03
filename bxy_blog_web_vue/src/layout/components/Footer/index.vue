<template>
    <div id="layout-footer">
        <div class="copyright">Copyright © 2020 by <a target="_blank" class="out-link" href="https://www.beloved.ink">beloved.ink</a> . All rights reserved. | <a target="_blank" class="out-link" href="http://www.beian.miit.gov.cn">陕ICP备19025605号-1</a></div>
    </div>
</template>

<script>
    export default {
        name: "layout-footer",
        data(){
            return{
            }
        },
        components:{
        },
        computed:{
        },
        methods:{
        },
        created(){
        }
    }
</script>

<style scoped lang="less">
#layout-footer{
    padding: 2%;
    border-top: 1px solid #F7F7F7;
    font-size: 13px;
    color: #9c9c9c;
    a.out-link:hover{
        color: #ff6d6d;
    }
    .copyright{
        text-align: center;
    }
}
    /*****/
    @media (max-width: 800px){
        #layout-footer{
            .footer-main .footer-item:nth-child(3){
                flex: 2;
            }
        }
    }
    @media (max-width: 600px){
        #layout-footer{
            .footer-main {
                display: block;
                .footer-item{
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    flex-wrap: wrap;
                    & > div{
                        margin-bottom: 10px;
                    }
                }
            }
        }
    }
    /*****/
</style>