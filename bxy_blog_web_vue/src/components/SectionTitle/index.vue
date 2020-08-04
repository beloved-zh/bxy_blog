<template>
    <h1 class="section-title">
       <div style="display: flex;align-items: flex-end;">
            {{ value }}
            <SmallIco />
        </div>
    </h1>
</template>

<script>
    import SmallIco from '@/components/SmallIco'
    export default {
        name: "section-title",
        props: {
          value: {
              type: String,
              default: ""
          }
        },
        components: {
          SmallIco
        },
    }
</script>

<style scoped>
    h1.section-title {
        color: #757575;
        font-size: 16px;
        font-weight: 400;
        padding-bottom: 15px;
        border-bottom: 1px dashed #ECECEC;
        display: flex;
        align-items: flex-end;
    }
</style>
