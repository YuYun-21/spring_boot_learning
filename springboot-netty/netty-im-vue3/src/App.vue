<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <RouterLink to="/">Home</RouterLink>
  <RouterLink to="/about">About</RouterLink>
  <RouterLink to="/nettyIm" >NettyIm</RouterLink>
  <RouterLink to="/websocket">Websocket</RouterLink>

  <RouterView />
</template>
