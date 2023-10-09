<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <h3>WebSocket聊天</h3>
        <div class="flex items-center">
          <span class="text-lg font-medium mr-4"> 连接状态: </span>
          <el-tag :color="getTagColor">{{ status }}</el-tag>
        </div>
        <el-form :model="form">
          <el-form-item style="align-items: center" required label="websocket地址：">
            <el-select v-model="server" style="width: 350px" clearable placeholder="Select">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
            <el-button type="primary" @click="toggle">{{ getIsOpen ? '关闭连接' : '连接' }}</el-button>
          </el-form-item>
        </el-form>
      </el-header>
      <el-container>
        <el-aside width="500px">
          <div style="margin-top: 10px">
            <span>昵称：</span>
            <el-input style="width: 150px;margin-right: 10px"></el-input>
            <el-button type="primary" @click="toggle">上线</el-button>
          </div>

        </el-aside>
        <el-container>
          <el-main>
            <div>

            </div>
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import {computed, reactive, ref} from 'vue';
import {useWebSocket} from '@vueuse/core'

// const wsClient = ref(new WebSocket("ws://localhost:8088"))

const server = ref<string>()
const address = ref<string>()
const options: [] = ref([
  {
    label: 'ws://localhost:8088',
    value: 'ws://localhost:8088'
  }
])

interface Command {
  code: number,
  nickname: string,
  target: string,
  content: string,
  type: number
}

const command: Command = {
  code: '',
  nickname: ''
}

const {data, status, close, open, send, ws} = useWebSocket(address, {
  heartbeat: false,
  autoReconnect: false
});


const getIsOpen = computed(() => status.value === 'OPEN')
const getTagColor = computed(() => (getIsOpen.value ? '#67C23A' : '#F56C6C'))

function toggle() {
  console.log(status, 1)
  console.log(status.value, 2)
  console.log(server, 3)
  // 'CONNECTING' | 'CLOSED'
  if (status.value === 'OPEN') {
    close()
  } else {
    close()
    address.value = 'ws://localhost:8088'
    open()
  }
}

const content = reactive({
  code: 10001,
  nickname: "张三"
})

const form = reactive({
  name: ""
})

function connect() {
  console.log("连接成功", wsClient)
  wsClient.value.onopen = function () {
    status.value = "OPEN"
    console.log("已连接")
  }
  wsClient.value.onmessage = function (msg) {
    console.log("收到服务端消息", msg)
  }
  wsClient.value.onclose = function (msg) {
    status.value = "CLOSE"
    console.log("断开连接", msg)
  }
  wsClient.value.onerror = function (msg) {
    status.value = "FAIL"
    console.log("连接失败", msg)
  }
  wsClient.value.send(JSON.stringify(content))

}


</script>

<style scoped lang="scss">
.el-container {
  height: auto;
}

.el-header {
  background-color: #c6e2ff;
  height: auto;
}

.el-aside {
  background-color: #d9ecff;
}

.el-main {
  height: 300px;
  background-color: #ecf5ff;
}

.el-footer {
  background-color: #c6e2ff;
}
</style>