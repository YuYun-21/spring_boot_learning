<template>
  <div class="common-layout">
  <el-container>
    <el-header>
      WebSocket聊天
      <el-form :model="form">
        <el-form-item label="websocket地址：">
          <el-input v-model="form.name" />
        </el-form-item>
      </el-form>
    </el-header>
    <el-container>
      <el-aside width="500px">
        <div class="flex items-center">
      <span class="text-lg font-medium mr-4"> 连接状态: </span>
      <el-tag :color="getTagColor()">{{ status }}</el-tag>
    </div>
      </el-aside>
      <el-container >
        <el-main>
          <dev>

          </dev>
        </el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </el-container>
</div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';

// const wsClient = ref(new WebSocket("ws://localhost:8088"))

const status = ref('OPEN')
const getTagColor = () => (status.value === 'OPEN' ? '#67C23A' : '#F56C6C')

const content = reactive({
  code: 10001,
  nickname: "张三"
})

const form = reactive({
  name: ""
})

function connect(){
  console.log("连接成功",wsClient)
  wsClient.value.onopen = function (){
    status.value = "OPEN"
    console.log("已连接")
  }
  wsClient.value.onmessage = function (msg){
    console.log("收到服务端消息",msg)
  }
  wsClient.value.onclose = function (msg){
    status.value = "CLOSE"
    console.log("断开连接",msg)
  }
  wsClient.value.onerror = function (msg){
    status.value = "FAIL"
    console.log("连接失败",msg)
  }
  wsClient.value.send(JSON.stringify(content))

}
onMounted(()=>{
//  connect()
})

</script>
  
<style scoped lang="scss">
.el-container{
  height: auto;
}
  .el-header{
    background-color: #c6e2ff;
  }
  
  .el-aside{
    background-color: #d9ecff;
  }
  .el-main{
    height: 300px;
    background-color: #ecf5ff;
  }
  .el-footer{
    background-color: #c6e2ff;
  }
</style>