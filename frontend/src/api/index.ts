import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 15000,
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export const get = <T = any>(url: string, params?: any) =>
  api.get<{ code: number; message: string; data: T }>(url, { params }).then(res => res.data)

export const post = <T = any>(url: string, data?: any) =>
  api.post<{ code: number; message: string; data: T }>(url, data).then(res => res.data)

export const put = <T = any>(url: string, data?: any) =>
  api.put<{ code: number; message: string; data: T }>(url, data).then(res => res.data)

export const del = <T = any>(url: string, params?: any) =>
  api.delete<{ code: number; message: string; data: T }>(url, { params }).then(res => res.data)

export default api
