<template>
    <div id="layout-footer">
        <div class="copyright">
                Copyright © 2020 by 
            <a target="_blank" class="out-link" :href="webUrl">
                {{ webName }}
            </a>
                . All rights reserved. | 
             <a target="_blank" class="out-link" href="http://www.beian.miit.gov.cn">
                {{ recordNum }}
             </a>
        </div>
    </div>
</template>

<script>
    export default {
        name: "layout-footer",
        data(){
            return{
                webName: '',
                webUrl: '',
                recordNum: ''
            }
        },
        components:{
        },
        computed:{
        },
        methods:{
            getConfig(){
                this.$store.dispatch('getWebConfig').then(data =>{
                    this.webName = data.webName
                    this.webUrl = data.webUrl
                    this.recordNum = data.recordNum
                })
            }
        },
        created(){
            this.getConfig()
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