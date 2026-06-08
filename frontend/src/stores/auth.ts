import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'))
  const userInfo = ref<{ id?: number; username?: string; nickname?: string; avatar_url?: string } | null>(
    JSON.parse(localStorage.getItem('userInfo') || 'null')
  )

  function setAuth(newToken: string, info: typeof userInfo.value) {
    token.value = newToken
    userInfo.value = info
    localStorage.setItem('token', newToken)
    if (info) localStorage.setItem('userInfo', JSON.stringify(info))
  }

  function logout() {
    token.value = null
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return { token, userInfo, setAuth, logout }
})
