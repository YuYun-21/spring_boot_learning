<template>
  <el-space :size="size" :spacer="spacer">
      <el-button type="primary" plain>
        <RouterLink to="/about">About</RouterLink>
      </el-button>
      <el-button type="primary" plain>
        <RouterLink to="/nettyIm">NettyIm</RouterLink>
      </el-button>
      <el-button type="primary" plain>
        <RouterLink to="/websocket">Websocket</RouterLink>
      </el-button>
  </el-space>
</template>

<script lang="ts" setup>
import { RouterLink } from "vue-router";
import { h, ref } from 'vue'
import { ElDivider } from 'element-plus'

const size = ref(10)
const spacer = h(ElDivider, { direction: 'vertical' })

</script>

