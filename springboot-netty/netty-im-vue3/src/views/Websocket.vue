<template>
  <div class="flex">
    <el-card :gutter="12" class="w-1/2" shadow="always">
      <template #header>
        <div class="card-header">
          <span>连接</span>
        </div>
      </template>
      <div class="flex items-center">
        <span class="text-lg font-medium mr-4"> 连接状态: </span>
        <el-tag :color="getTagColor">{{ status }}</el-tag>
      </div>
      <hr class="my-4" />

      <div class="flex">
        <el-input v-model="server" disabled>
          <template #prepend> 服务地址</template>
        </el-input>
        <el-button :type="getIsOpen ? 'danger' : 'primary'" @click="toggle">
          {{ getIsOpen ? '关闭连接' : '开启连接' }}
        </el-button>
      </div>
      <p class="text-lg font-medium mt-4">设置</p>
      <hr class="my-4" />
      <el-input
        v-model="sendValue"
        :autosize="{ minRows: 2, maxRows: 4 }"
        :disabled="!getIsOpen"
        clearable
        type="textarea"
      />
      <el-button :disabled="!getIsOpen" block class="mt-4" type="primary" @click="handlerSend">
        发送
      </el-button>
    </el-card>
    <el-card :gutter="12" class="w-1/2" shadow="always">
      <template #header>
        <div class="card-header">
          <span>消息记录</span>
        </div>
      </template>
      <div class="max-h-80 overflow-auto">
        <ul>
          <li v-for="item in getList" :key="item.time" class="mt-2">
            <div class="flex items-center">
              <span class="mr-2 text-primary font-medium">收到消息:</span>
              {{item}}
            </div>
            <div>
            </div>
          </li>
        </ul>
      </div>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import { useWebSocket } from '@vueuse/core'
import { computed, watchEffect, reactive, ref } from 'vue';

defineOptions({ name: 'InfraWebSocket' })

const sendValue = ref('')

const server = ref('ws://localhost:8088')

const state = reactive({
  recordList: [] as { id: number; time: number; res: string }[]
})

const { status, data, send, close, open } = useWebSocket(server.value, {
  autoReconnect: false,
  heartbeat: true
})

watchEffect(() => {
  if (data.value) {
    try {
      const res = JSON.parse(data.value)
      state.recordList.push(res)
    } catch (error) {
      state.recordList.push({
        res: data.value,
        id: Math.ceil(Math.random() * 1000),
        time: new Date().getTime()
      })
    }
  }
})

const getIsOpen = computed(() => status.value === 'OPEN')
const getTagColor = computed(() => (getIsOpen.value ? 'success' : 'red'))

const getList = computed(() => {
  return [...state.recordList].reverse()
})

function handlerSend() {
  send(sendValue.value)
  sendValue.value = ''
}

function toggle() {
  if (getIsOpen.value) {
    close()
  } else {
    open()
  }
}
</script>
